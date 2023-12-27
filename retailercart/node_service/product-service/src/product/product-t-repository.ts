import { Logger } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductHierCrossMapping } from 'src/product/entities/product-hier-cross-mapping.entity';
import { ProductHierarchyValue } from 'src/product/entities/product-hierarchy-value.entity';
import { ProductEntity } from 'src/product/entities/product.entity';
import { Brackets, Repository } from 'typeorm';
import { CartDTO } from '../home-t/dto/cart-request-dto';
import { ProductDTO, ProductListDTO } from '../home-t/dto/product-list.dto';

export class ProductTRepository {
  @InjectRepository(ProductGroupEntity)
  private productGroup: Repository<ProductGroupEntity>;

  @InjectRepository(ProductHierCrossMapping)
  private productCrossMapping: Repository<ProductHierCrossMapping>;

  @InjectRepository(ProductHierarchyValue)
  private productHierarchyValue: Repository<ProductHierarchyValue>;

  @InjectRepository(ProductEntity)
  private product: Repository<ProductEntity>;
  //product

  async findProductList(filters: ProductListDTO): Promise<any> {
    const queryGroup = this.productGroup
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .select(['t1.prod_group_code prodGroupCode'])
      .distinct(true)
      .where('t1.cmp_code = :cmpCode', { cmpCode: filters.cmpCode })
      .andWhere('t4.expire_dt >= NOW()')
      .andWhere(this.checkFilters(filters));
    Logger.log(' Query queryGroup', queryGroup.getQueryAndParameters());
    const productGroup = await queryGroup.getRawMany();
    const total = productGroup.length;
    const groupList: string[] = [];
    filters.offset = filters.offset == 0 ? 1 : filters.offset;
    const startIndex =
      filters.offset == 1 ? 0 : (filters.offset - 1) * filters.limit;

    Logger.log('startIndex ' + startIndex);

    productGroup.forEach((data: any, index) => {
      if (startIndex <= index && index < filters.offset * filters.limit) {
        groupList.push(data?.prodGroupCode);
      }
    });
    if (groupList.length === 0) {
      return { productList: [], total };
    }

    Logger.log('productGroup length ' + productGroup.length);
    const query = this.productGroup
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .where('t1.cmp_code = :cmpCode', { cmpCode: filters.cmpCode })
      .andWhere('t4.expire_dt >= NOW()')
      .andWhere(`t1.prod_group_code IN ( :productGroup ) `, {
        productGroup: groupList,
      })
      .andWhere(this.checkFilters(filters));
    this.applySorting(query, filters);
    Logger.log('getQueryAndParameters ', query.getQueryAndParameters());
    const productList: any = await query.getMany();
    return { productList: productList, total };
  }

  async getProductSubCategory(levelCode: string, cmpCode: string) {
    await this.productHierarchyValue
      .createQueryBuilder('t1')
      .where('t1.level_code = :levelCode AND t1.cmp_code =:cmpCode ', {
        levelCode,
        cmpCode,
      })
      .getMany();
  }

  async getSubCategory(categoryCode?: string) {
    return await this.productHierarchyValue
      .createQueryBuilder('t1')
      .select([
        't1.cmp_code as cmpCode',
        't1.value_code as Code',
        't1.value_name as name',
        't1.image_url as image',
      ])
      .innerJoin('t1.productHierLevel', 't2')
      .where('t2.common_name = "SUB-CATEGORY NAME" ')
      .andWhere(
        new Brackets((qb) => {
          if (categoryCode) {
            qb.where('t1.level_code = :categoryCode ', { categoryCode });
          }
        }),
      )
      .getRawMany();
  }

  async getProduct(product: ProductDTO): Promise<ProductGroupEntity[]> {
    return await this.productGroup
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .where('t1.cmp_code = :cmpCode', { cmpCode: product.cmpCode })
      .andWhere('t4.expire_dt >= NOW()')
      .andWhere(`t1.prod_group_code = :groupCode`, {
        groupCode: product.groupCode,
      })
      .getMany();
  }

  async findProductCart(cartDTO: CartDTO): Promise<any> {
    const query = this.product
      .createQueryBuilder('t2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .innerJoinAndSelect('t4.stock', 't6')
      .where('t2.cmp_code = :cmpCode', { cmpCode: cartDTO.cmpCode })
      .andWhere('t4.expire_dt >= NOW()')
      .andWhere('t4.prod_code = :prodCode ', {
        prodCode: cartDTO.prodCode,
      })
      .andWhere(
        new Brackets((qb) => {
          if (cartDTO.batchCode) {
            qb.where('t4.prod_batch_code = :batchCode ', {
              batchCode: cartDTO.batchCode,
            });
          }
        }),
      );
    Logger.log('getQueryAndParameters ', query.getQueryAndParameters());
    return await query.getOne();
  }

  checkFilters(
    filters: ProductListDTO,
  ):
    | string
    | import('typeorm').ObjectLiteral
    | Brackets
    | import('typeorm').ObjectLiteral[]
    | ((
        qb: import('typeorm').SelectQueryBuilder<ProductGroupEntity>,
      ) => string) {
    return new Brackets((qb) => {
      if (filters?.name && filters?.category) {
        qb.where(
          ' t1.prod_group_name LIKE :name OR t2.prod_name LIKE :name  ' +
            ' AND t2.prod_hier_level = :category',
          {
            name: `%${filters?.name}%`,
            category: filters.category,
          },
        );
      } else if (filters?.name) {
        qb.where(' t1.prod_group_name LIKE :name OR t2.prod_name LIKE :name ', {
          name: `%${filters?.name}%`,
        });
      } else if (filters?.category) {
        qb.where(' t2.prod_hier_level = :category ', {
          category: filters.category,
        });
      }

      if (filters?.subCategory) {
        qb.andWhere(' t2.prod_hier_path = :subCategory ', {
          subCategory: `%${filters?.subCategory}%`,
        });
      }
      if (filters?.brand) {
        qb.andWhere(' t2.prod_hier_path = :brand ', {
          brand: `%${filters?.brand}%`,
        });
      }

      if (filters?.price?.max) {
        qb.andWhere(
          ' t5.price_to_retailer BETWEEN :min AND :max',
          filters.price,
        );
      }
    });
  }

  applySorting(query: any, filters: ProductListDTO) {
    if (filters?.sortBy == 'price') {
      query.orderBy('t5.price_to_retailer', filters.sortOrder);
    } else if (filters?.sortBy == 'name') {
      query.orderBy('t1.prod_group_name', filters.sortOrder);
    }
  }
}
