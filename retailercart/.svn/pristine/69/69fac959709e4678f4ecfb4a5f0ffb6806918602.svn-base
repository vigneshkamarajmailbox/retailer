import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { constantMedia } from 'src/common/helper/constant-data';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductUom } from 'src/product/entities/product-uom.entity';
import { ProductEntity } from 'src/product/entities/product.entity';
import { PRODUCT_UOM } from 'src/shared/enum/product-uom.enum';
import { CartDTO } from './../home-t/dto/cart-request-dto';
import {
  Product,
  ProductDTO,
  ProductListDTO,
  Uom,
} from './../home-t/dto/product-list.dto';
import { ProductTRepository } from './product-t-repository';
import { SchemeServiceCommunicator } from 'src/communicators/scheme/scheme.service';
import { UserServiceCommunicator } from 'src/communicators/user-profile/user.service';
import { CartServiceCommunicator } from 'src/communicators/cart/cart.service';

@Injectable()
export class ProductTService {
  @Inject()
  private productRepo: ProductTRepository;

  @Inject()
  private schemeService: SchemeServiceCommunicator;

  @Inject()
  private userService: UserServiceCommunicator;

  @Inject()
  private cartCommunicator: CartServiceCommunicator;

  async getProduct(product: ProductDTO, username: string) {
    const productList = await this.productRepo.getProduct(product);
    const productGrouping: any[] = [];
    const wishlist = await this.getAllWishList(product.cmpCode, username);
    if (productList) {
      this.mapProduct(productList, productGrouping, wishlist);
      return { statusCode: 200, product: productGrouping[0] ?? {} };
    } else {
      throw new HttpException('Product Group Not found', HttpStatus.NOT_FOUND);
    }
  }

  async getProductList(
    productListDTO: ProductListDTO,
    username: string,
    authorization: string,
  ) {
    console.log(' user name ', username, authorization);
    const subCategory = await this.productRepo.getSubCategory(
      productListDTO.category,
    );
    const productGroup: any = await this.productRepo.findProductList(
      productListDTO,
    );
    const productList = productGroup.productList;
    const wishlist = await this.getAllWishList(
      productListDTO.cmpCode,
      username,
    );
    const productGrouping: any[] = [];
    this.mapProduct(productList, productGrouping, wishlist);
    return {
      statusCode: 200,
      subCategory,
      productList: productGrouping,
      limit: productListDTO.limit,
      offset: productListDTO.offset,
      totalRecordCount: productGroup.total,
      totalPage: Math.ceil(productGroup.total / productListDTO.limit),
    };
  }

  mapProduct(
    productList: ProductGroupEntity[],
    productGrouping: any,
    wishlist: any[],
  ) {
    productList.forEach(async (data: ProductGroupEntity) => {
      const uom: Uom[] = data.product.productUom?.map(
        (weighage: ProductUom) => {
          return {
            code: PRODUCT_UOM[weighage.prodUomCode],
            name: weighage.prodUomCode,
            conversionFactor: weighage.prodConversionFactor,
          };
        },
      ) as any[];
      const productBatch = data.product.productBatch[0];
      const pricing = productBatch?.productPricing;
      const singleWishlist: any = wishlist?.find(
        (wl: any) =>
          wl.prodCode === data.product.prodCode &&
          wl.cmpCode === data.product.cmpCode,
      );
      const product: Product = {
        cmpCode: data.product.cmpCode,
        prodCode: data.product.prodCode,
        batchCode: productBatch?.prodBatchCode,
        name: data.product.prodName,
        desc: data.product.prodDesc,
        shortName: data.product.prodShort,
        shortDesc: data.product.prodShortDesc,
        currencySymbol: '₹',
        weightType: data.product.weightType,
        weightValue: data.product.weightValue,
        mrp: pricing?.mrp,
        priceToRetailer: pricing?.priceToRetailer,
        suggestedQty: pricing?.suggestedQty,
        minimumOrderQty: pricing?.minimumOrderQty,
        uom: uom,
        productMedia: constantMedia,
        isWishlist: !!singleWishlist,
        wishlistId: singleWishlist ? singleWishlist?.wishlistId : null,
        scheme: null,
        appliedScheme: null,
        priceAfterDiscount: null,
      } as Product;

      if (productGrouping?.length > 0) {
        if (
          productGrouping.some((pg) => pg?.prodGroupCode === data.prodGroupCode)
        ) {
          productGrouping.forEach((element: any) => {
            if (element?.prodGroupCode === data.prodGroupCode) {
              element?.product.push(product);
            }
          });
        } else {
          productGrouping.push({
            prodName: data.prodGroupName,
            prodGroupCode: data.prodGroupCode,
            product: [product],
          });
        }
      } else {
        productGrouping.push({
          prodName: data.prodGroupName,
          prodGroupCode: data.prodGroupCode,
          product: [product],
          productMedia: product.productMedia,
        });
      }
    });
  }

  async getCartRequest(cartDTO: CartDTO) {
    const cart = await this.productRepo.findProductCart(cartDTO);
    return this.mapCartProduct(cart);
  }

  mapCartProduct(productEntity: ProductEntity) {
    if (productEntity) {
      const uom: Uom[] = productEntity?.productUom?.map(
        (weighage: ProductUom) => {
          return {
            code: PRODUCT_UOM[weighage.prodUomCode],
            name: weighage.prodUomCode,
            conversionFactor: weighage.prodConversionFactor,
          };
        },
      ) as any[];
      const productBatch = productEntity.productBatch[0];
      const product: any = {
        cmpCode: productEntity.cmpCode,
        prodCode: productEntity.prodCode,
        batchCode: productBatch?.prodBatchCode,
        distrCode: productBatch?.stock[0].distrCode,
        name: productEntity.prodName,
        desc: productEntity.prodDesc,
        shortName: productEntity.prodShort,
        shortDesc: productEntity.prodShortDesc,
        currencySymbol: '₹',
        weightType: productEntity.weightType,
        weightValue: productEntity.weightValue,
        mrp: productBatch?.productPricing?.mrp,
        priceToRetailer: productBatch?.productPricing?.priceToRetailer,
        suggestedQty: productBatch?.productPricing?.suggestedQty,
        minimumOrderQty: productBatch?.productPricing?.minimumOrderQty,
        uom: uom,
        productMedia: constantMedia,
        isWishlist: false,
        scheme: null,
        appliedScheme: null,
        priceAfterDiscount: null,
      } as Product;

      return { product };
    } else {
      throw new HttpException('Product Not Found', HttpStatus.NOT_ACCEPTABLE);
    }
  }

  async applyScheme(cmpCode: string, token: string) {
    const user = await this.userService.userInfo(token, cmpCode);
    console.log('user ', user);
    // const scheme = this.schemeService.schemeList();
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
