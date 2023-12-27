import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { WishlistRepository } from 'src/wishlist/wishlist.repository';
import {
  AddWishlistStatusDto,
  CreateWishlistDto,
  WishlistStatusDto,
  WishlistDto,
} from './dto/create-wishlist.dto';
import { ProductCommunicatorService } from 'src/shared/communicators/product.service';

@Injectable()
export class WishlistService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  wishlistRepository: WishlistRepository;

  @Inject()
  private readonly httpService: HttpService;

  constructor(private readonly productService: ProductCommunicatorService) {}

  //Create, Update, Delete and Delete All
  async add(auth: any, wishlistRequest: WishlistDto) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;
    //check Already in wishlist or not
    const reqParams: WishlistStatusDto = {
      userName: userName,
      cmpCode: wishlistRequest.cmpCode,
      prodCode: wishlistRequest.prodCode,
      batchCode: wishlistRequest.batchCode,
    };
    const wishlistResponse = await this.wishlistRepository.getWishlistByProduct(
      reqParams,
    );

    if (wishlistResponse) {
      return await this.removeWishlist(userName, wishlistResponse.wishlistId);
    }

    //Find Product
    const prodResponse = await this.productService.getFromProduct(
      {
        cmpCode: wishlistRequest.cmpCode,
        prodCode: wishlistRequest.prodCode,
        batchCode: wishlistRequest.batchCode,
      },
      token,
    );
    const productDetail = prodResponse.data;

    //wishlist SaveInfo
    const saveWishlistReq = {
      cmpCode: productDetail.cmpCode,
      userName: userName,
      prodCode: productDetail.prodCode,
      batchCode: productDetail.batchCode,
      prodName: productDetail.name,
      prodMrp: productDetail.mrp,
      prodRetailerPrice: productDetail.priceToRetailer,
      isWishlist: true,
    };

    //Return Status
    return await this.create(saveWishlistReq);
  }

  async removeWishlist(userName: string, wishlistId: string) {
    const cartDetails = await this.wishlistRepository.getWishlistById(
      userName,
      wishlistId,
    );

    if (cartDetails) {
      return await this.delete(userName, wishlistId);
    } else {
      throw new HttpException('Product Not Found', HttpStatus.NOT_ACCEPTABLE);
    }
  }

  //Fetch Details From List
  async getWishlist(auth: any, paginationReq: any): Promise<any> {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    //Get WishList
    const WishListDetails = await this.wishlistRepository.getWishList(
      userName,
      paginationReq.limit,
      paginationReq.offset,
    );

    if (!WishListDetails.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Records not found in wishlist',
      };
    }

    //Structure the List
    const wishlistResponse = await this.changeStructure(WishListDetails, token);

    return {
      wishlistCount: WishListDetails.length,
      wishlist: wishlistResponse,
    };
  }

  async getWishlistByID(auth: any, wishlistId: string) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    //Get Detail
    const wishlistDetails = [
      await this.wishlistRepository.getWishlistById(userName, wishlistId),
    ];

    if (wishlistDetails[0] === null) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'wishlist not Found',
      };
    }
    //Structure the List
    const wishlistResponse = await this.changeStructure(wishlistDetails, token);

    return {
      wishlist: wishlistResponse[0],
    };
  }

  async getWishlistStatus(auth: any, getWishlistStatus: AddWishlistStatusDto) {
    const userName = auth.user.username;

    const reqParams: WishlistStatusDto = {
      userName: userName,
      cmpCode: getWishlistStatus.cmpCode,
      prodCode: getWishlistStatus.prodCode,
      batchCode: getWishlistStatus.batchCode,
    };
    const wishlistResponse = await this.wishlistRepository.getWishlistByProduct(
      reqParams,
    );

    if (wishlistResponse) {
      return { isWishlist: true };
    } else {
      return { isWishlist: false };
    }
  }

  //Common Repository Calls
  async create(saveRequest: CreateWishlistDto) {
    const save = await this.wishlistRepository.create(saveRequest);
    if (!save) {
      return {
        statusCode: HttpStatus.FORBIDDEN,
        message: 'Wishlist not added',
        isWishlist: false,
      };
    } else {
      return {
        statusCode: HttpStatus.CREATED,
        message: 'Wishlist added successfully',
        isWishlist: true,
      };
    }
  }

  async delete(userName: string, wishlistId: string) {
    const deleteStatus = await this.wishlistRepository.deleteWishlist({
      userName: userName,
      wishlistId: wishlistId,
    });
    if (deleteStatus) {
      return {
        statusCode: HttpStatus.OK,
        message: 'Product removed from Wishlist!',
        isWishlist: false,
      };
    } else {
      return {
        statusCode: HttpStatus.FORBIDDEN,
        message: 'Product failed to remove',
        isWishlist: false,
      };
    }
  }

  //Structure the Response
  async changeStructure(WishListDetails: any, token: string) {
    const list = [];

    for (const WishListDetail of WishListDetails) {
      //Find Product
      const prodResponse = await this.productService.getFromProduct(
        {
          cmpCode: WishListDetail.cmpCode,
          prodCode: WishListDetail.prodCode,
          batchCode: WishListDetail.batchCode,
        },
        token,
      );
      const productDetail = prodResponse.data;

      const data = {
        wishlistId: WishListDetail.wishlistId,
        cmpCode: WishListDetail.cmpCode,
        distrCode: WishListDetail.distrCode,
        prodCode: productDetail.prodCode,
        batchCode: productDetail.batchCode,
        name: productDetail.name,
        desc: productDetail.desc,
        shortName: productDetail.shortName,
        shortDesc: productDetail.shortDesc,
        currencySymbol: productDetail.currencySymbol,
        weightType: productDetail.weightType,
        weightValue: productDetail.weightValue,
        mrp: productDetail.mrp,
        priceToRetailer: productDetail.priceToRetailer,
        suggestedQty: productDetail.suggestedQty,
        minimumOrderQty: productDetail.minimumOrderQty,
        uom: productDetail.uom,
        productMedia: productDetail.productMedia,
        isWishlist: true,
        scheme: productDetail.scheme,
        appliedScheme: productDetail.appliedScheme,
        priceAfterDiscount: productDetail.priceAfterDiscount,
      };
      list.push(data);
    }

    return list;
  }

  async getWishlistInternal(username: string, cmpCode: string) {
    return await this.wishlistRepository.getWishlist(cmpCode, username);
  }
}
