import { Inject, Injectable } from '@nestjs/common';
import { constantMedia } from 'src/common/helper/constant-data';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductUom } from 'src/product/entities/product-uom.entity';
import { PRODUCT_UOM } from 'src/shared/enum/product-uom.enum';
import { Product, Uom } from './dto/home-dto';
import { ProductExplore } from './dto/product-explore-dto';
import { HomeTRepository } from './home-t.repository';
import { CartServiceCommunicator } from 'src/communicators/cart/cart.service';

@Injectable()
export class HomeTService {
  @Inject()
  private homeRepo: HomeTRepository;

  @Inject()
  private cartCommunicator: CartServiceCommunicator;

  async getHomeData(cmpCode: string, username: string) {
    const highlights = await this.homeRepo.getHighlights(cmpCode);
    const banner = await this.homeRepo.getBanner(cmpCode);
    const category = await this.homeRepo.getCategory(cmpCode);
    const brand = await this.homeRepo.getBrand(cmpCode);
    const tags = await this.homeRepo.getTags(cmpCode);

    const productGrouping: any[] = [];
    const wishlist = await this.getAllWishList(cmpCode, username);
    await this.mapProduct(tags, productGrouping, wishlist);

    const home: any = [
      { key: 'highlights', highlights },
      { key: 'banner', banner: banner },
      { key: 'category', title: 'Shop by category', category: category },
      { key: 'brand', title: 'Shop by brand', brand: brand },
      ...productGrouping,
    ];
    return { statusCode: 200, homeData: home };
  }

  async getProductTag(productExplore: ProductExplore, username: string) {
    const tags = await this.homeRepo.getTagsExplore(productExplore);
    const productGrouping: any[] = [];
    const wishlist = await this.getAllWishList(
      productExplore.cmpCode,
      username,
    );
    await this.mapProduct(tags, productGrouping, wishlist);
    return { statusCode: 200, tag: productGrouping[0] ?? {} };
  }

  async mapProduct(
    productList: ProductGroupEntity[],
    tagList: any,
    wishlist: any,
  ) {
    productList?.forEach(async (data: ProductGroupEntity) => {
      const uom: Uom[] = data.product.productUom?.map(
        (weighage: ProductUom) => {
          return {
            code: PRODUCT_UOM[weighage.prodUomCode],
            name: weighage.prodUomCode,
            conversionFactor: weighage.prodConversionFactor,
          };
        },
      ) as any[];

      const singleWishlist: any = wishlist?.find(
        (wl: any) =>
          wl.prodCode === data.product.prodCode &&
          wl.cmpCode === data.product.cmpCode,
      );
      console.log('singleWishlist ', singleWishlist);
      const product: any = {
        cmpCode: data.product.cmpCode,
        prodCode: data.product.prodCode,
        batchCode: data.product.productBatch[0].prodBatchCode,
        name: data.product.prodName,
        desc: data.product.prodDesc,
        shortName: data.product.prodShort,
        shortDesc: data.product.prodShortDesc,
        currencySymbol: '₹',
        weightType: data.product.weightType,
        weightValue: data.product.weightValue,
        mrp: data.product.productBatch[0].productPricing?.mrp,
        priceToRetailer:
          data.product.productBatch[0].productPricing?.priceToRetailer,
        suggestedQty: data.product.productBatch[0].productPricing?.suggestedQty,
        minimumOrderQty:
          data.product.productBatch[0].productPricing?.minimumOrderQty,
        uom: uom,
        productMedia: constantMedia,
        isWishlist: !!singleWishlist,
        wishlistId: singleWishlist ? singleWishlist?.wishlistId : null,
        scheme: null,
        appliedScheme: null,
        priceAfterDiscount: null,
      } as Product;

      // check tag available
      if (tagList.length > 0 && this.checkTagExist(tagList, data)) {
        // check tag and group code exist
        tagList?.some((tl: any) => {
          if (tl?.tagCode === data.product.productTags[0].tagCode) {
            tl?.productGroup?.some((pg) => {
              if (pg?.prodGroupCode === data.prodGroupCode) {
                pg?.product.push(product);
              } else {
                const group = {
                  prodGroupName: data.prodGroupName,
                  prodGroupCode: data.prodGroupCode,
                  product: [product],
                  productMedia: product.productMedia,
                };
                tl?.productGroup.push(group);
              }
            });
          }
        });
      } else {
        const tag = {
          key: 'product',
          tagCode: data.product.productTags[0].tagCode,
          tagName: data.product.productTags[0].tagName,
          productGroup: [
            {
              prodGroupName: data.prodGroupName,
              prodGroupCode: data.prodGroupCode,
              product: [product],
              productMedia: product.productMedia,
            },
          ],
        };
        tagList.push(tag);
      }
    });
  }

  checkTagExist(tags: any[], data: ProductGroupEntity): boolean {
    return tags?.some(
      (tl) => tl?.tagCode === data.product.productTags[0].tagCode,
    );
  }

  async getAllWishList(cmpCode: string, username: string) {
    try {
      return await this.cartCommunicator.getWishlist(username, cmpCode);
    } catch (error) {
      console.error(error);
      return [];
    }
  }
}
