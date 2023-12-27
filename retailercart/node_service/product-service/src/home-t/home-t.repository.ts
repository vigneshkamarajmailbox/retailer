import { InjectRepository } from '@nestjs/typeorm';
import { BannerEntity } from 'src/home/entities/home-banner.entity';
import { HighlightsEntity } from 'src/home/entities/home-highlights.entity';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductHierCrossMapping } from 'src/product/entities/product-hier-cross-mapping.entity';
import { ProductHierarchyValue } from 'src/product/entities/product-hierarchy-value.entity';
import { ProductTags } from 'src/product/entities/product-tags.entity';
import { Repository } from 'typeorm';
import { ProductExplore } from './dto/product-explore-dto';

export class HomeTRepository {
  @InjectRepository(HighlightsEntity)
  private highlights: Repository<HighlightsEntity>;

  @InjectRepository(BannerEntity)
  private banner: Repository<BannerEntity>;

  @InjectRepository(ProductHierCrossMapping)
  private productCrossMapping: Repository<ProductHierCrossMapping>;

  @InjectRepository(ProductHierarchyValue)
  private productHierarchyValue: Repository<ProductHierarchyValue>;

  @InjectRepository(ProductTags)
  private productTags: Repository<ProductTags>;

  @InjectRepository(ProductGroupEntity)
  private productGroup: Repository<ProductGroupEntity>;

  async getHighlights(cmpCode: string) {
    return await this.highlights.find({ where: { cmpCode } });
  }

  async getCategory(cmpCode: string): Promise<any> {
    return await this.productCrossMapping
      .createQueryBuilder('t1')
      .select([
        't2.cmp_code as cmpCode',
        't2.value_code as code',
        't2.value_name as name',
        't2.image_url as image',
      ])
      .innerJoin('t1.productHierValue', 't2')
      .where('t1.cmp_code =:cmpCode', {
        cmpCode,
      })
      .andWhere('t1.common_name =:commonName', {
        commonName: 'CATEGORY NAME',
      })
      .getRawMany();
  }

  async getBrand(cmpCode: string): Promise<any> {
    return await this.productCrossMapping
      .createQueryBuilder('t1')
      .select([
        't2.cmp_code as cmpCode',
        't2.value_code as code',
        't2.value_name as name',
        't2.image_url as image',
      ])
      .innerJoin('t1.productHierValue', 't2')
      .where('t1.cmp_code =:cmpCode', {
        cmpCode,
      })
      .andWhere('t1.common_name =:commonName', {
        commonName: 'BRAND NAME',
      })
      .getRawMany();
  }
  async getBanner(cmpCode: string): Promise<any> {
    return await this.banner.find({ where: { cmpCode } });
  }

  async getProductTags(cmpCode: string): Promise<any> {
    return await this.productTags
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .innerJoinAndSelect('t2.productGroupMapping', 't6')
      .where('t1.cmp_code = :cmpCode ', { cmpCode: cmpCode })
      .andWhere(' t4.expire_dt >= NOW() ')
      .getMany();
  }

  async getTags(cmpCode: string): Promise<ProductGroupEntity[]> {
    return await this.productGroup
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .innerJoinAndSelect('t2.productTags', 't6')
      .where('t1.cmp_code = :cmpCode ', { cmpCode: cmpCode })
      .andWhere(' t4.expire_dt >= NOW() ')
      .getMany();
  }

  async getTagsExplore(exploreProduct: ProductExplore): Promise<any> {
    return await this.productGroup
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.product', 't2')
      .innerJoinAndSelect('t2.productUom', 't3')
      .innerJoinAndSelect('t2.productBatch', 't4')
      .innerJoinAndSelect('t4.productPricing', 't5')
      .innerJoinAndSelect('t2.productTags', 't6')
      .where('t1.cmp_code = :cmpCode ', { cmpCode: exploreProduct.cmpCode })
      .andWhere('t6.tag_code = :tagCode ', {
        tagCode: exploreProduct.tagCode,
      })
      .andWhere(' t4.expire_dt >= NOW() ')
      .getMany();
  }
}
