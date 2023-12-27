import { HttpStatus, Inject, Injectable } from '@nestjs/common';
import {
  AddCartDetailsDto,
  CreateCartDetailsDto,
  DeleteCartDto,
  GetCartDetailsByProdDto,
  UpdateCartDetailDto,
  UpdateCartDetailsDto,
} from './dto/create-cart-detail.dto';
import { CartRepository } from './cart.repository';
import { SchemeHistoryStatus, SchemeType } from 'src/shared/enum/pattern.enum';
import { ProductCommunicatorService } from 'src/shared/communicators/product.service';
import { SchemeCommunicatorService } from 'src/shared/communicators/scheme.service';
import { RetailerCommunicatorService } from 'src/shared/communicators/retailer.service';

@Injectable()
export class CartDetailsService {
  @Inject()
  cartRepository: CartRepository;

  constructor(
    private readonly productService: ProductCommunicatorService,
    private readonly schemeService: SchemeCommunicatorService,
    private readonly retailerService: RetailerCommunicatorService,
  ) {}

  //Create, Update, Delete and Delete All
  async addToCart(auth: any, saveRequests: AddCartDetailsDto[]) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    const saveDetails: CreateCartDetailsDto[] = [];
    for await (const saveRequest of saveRequests) {
      //Find product detail in cart
      const reqParams: GetCartDetailsByProdDto = {
        userName: userName,
        cmpCode: saveRequest.cmpCode,
        prodCode: saveRequest.prodCode,
        isActive: true,
      };
      const cartProducts = await this.cartRepository.getCartDetailByProduct(
        reqParams,
      );

      if (saveRequest.uom === undefined && saveRequest.uom.length == 0) {
        return {
          statusCode: HttpStatus.BAD_REQUEST,
          message: 'Bad Request',
        };
      }

      //Already added Product update In Cart List
      if (cartProducts) {
        const updateRequest = {
          id: cartProducts.id,
          cmpCode: saveRequest.cmpCode,
          prodCode: saveRequest.prodCode,
          batchCode: saveRequest.batchCode,
          taxPercentage: 0,
          uom: saveRequest.uom,
        };

        await this.updateCartDetail(
          auth,
          cartProducts.cartListId,
          updateRequest,
        );
      } else {
        //Find Product
        const prodResponse = await this.productService.getFromProduct(
          {
            cmpCode: saveRequest.cmpCode,
            prodCode: saveRequest.prodCode,
            batchCode: saveRequest.batchCode,
          },
          token,
        );
        const productDetail = prodResponse.data;

        //UOM based addition the quantity
        let overAllProdQty = 0;
        for await (const uomDetail of saveRequest.uom) {
          overAllProdQty +=
            Number(uomDetail.orderQty) * Number(uomDetail.conversionFactor);
        }

        //Tax Calculation
        const prodQty = overAllProdQty;
        const taxPercentage = 0;
        const total = prodQty * productDetail.priceToRetailer;
        const taxAmount = total * (taxPercentage / 100);

        //Save Info Object
        saveDetails.push({
          cmpCode: productDetail.cmpCode,
          userName: userName,
          distCode: productDetail.distrCode,
          prodCode: productDetail.prodCode,
          prodName: productDetail.name,
          batchCode: productDetail.batchCode,
          prodQty: prodQty,
          minQty: productDetail.minimumOrderQty,
          cartQtyDetails: saveRequest.uom,
          cartUomDetails: productDetail.uom,
          weightCode: productDetail.weightType,
          weightValue: productDetail.weightValue,
          isApplyScheme: 0, //productDetail.appliedScheme,
          schemePercentage: null,
          schemeAmount: null,
          taxPercentage: taxPercentage,
          taxAmount: taxAmount,
          totalAmount: Number(total + taxAmount),
          isActive: true, //true means item in Cart
          isFreeProd: 0,
        });
      }
    }

    //Return Status
    if (saveDetails.length) {
      return await this.create(saveDetails);
    } else {
      return {
        statusCode: HttpStatus.OK,
        message: 'Product quantity Updated in Successfully!',
      };
    }
  }

  async updateCartDetail(
    auth: any,
    cartListId: string,
    updateRequest: UpdateCartDetailsDto,
  ) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    const cartDetails = await this.cartRepository.getCartDetailById(
      userName,
      cartListId,
    );

    if (!cartDetails) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: '#' + cartListId + 'cart item not Found',
      };
    }

    //Find Product
    const prodResponse = await this.productService.getFromProduct(
      {
        cmpCode: updateRequest.cmpCode,
        prodCode: updateRequest.prodCode,
        batchCode: updateRequest.batchCode,
      },
      token,
    );
    const productDetail = prodResponse.data;

    //UOM based addition the quantity
    let overAllProdQty = 0;
    for await (const uomDetail of updateRequest.uom) {
      overAllProdQty +=
        Number(uomDetail.orderQty) * Number(uomDetail.conversionFactor);
    }

    // Update
    //Tax Calculation
    const prodQty = overAllProdQty;
    const taxPercentage = 0;
    const total = prodQty * Number(productDetail.priceToRetailer);
    const taxAmount = total * (taxPercentage / 100);

    //Empty Quantity to delete Cart
    if (prodQty == 0) {
      const cartId: any = { cartListIds: [cartListId] };
      return await this.delete(userName, cartId);
    }

    const updateDetails = {
      prodQty: prodQty,
      taxPercentage: taxPercentage,
      taxAmount: taxAmount,
      totalAmount: total + taxAmount,
      cartQtyDetails: updateRequest.uom,
      cartUomDetails: productDetail.uom,
    };

    return await this.update(userName, cartListId, updateDetails, token);
  }

  //Fetch Details From List
  async getCartList(auth: any, paginationReq: any): Promise<any> {
    const userName = auth.user.username;
    const token = auth.headers.authorization;
    //Get Cart List
    const cartDetailsList = await this.cartRepository.getCartList(
      userName,
      paginationReq.limit,
      paginationReq.offset,
    );

    if (!cartDetailsList.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Cart Item not Found',
      };
    }

    //Structure the List
    const cartResponse = await this.changeStructure(cartDetailsList, token);

    const schemesList = await this.findSchemes(cartResponse, userName, token);

    return {
      totalCartCount: cartDetailsList.length,
      totalCartAmount: cartDetailsList.reduce((a, b) => +a + +b.totalAmount, 0),
      cartList: cartResponse,
      schemesList: schemesList,
    };
  }

  //Final Cart Total Calculation
  async getFinalPrice(auth: any, request: any): Promise<any> {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    //request Cart Items validation
    if (!request.cartItems.length) {
      return {
        statusCode: HttpStatus.BAD_REQUEST,
        message: 'Cart Items Should not be Empty',
      };
    }

    //Get Cart Details
    const cartDetails = await this.cartRepository.getCartDetailsMultipleIds(
      userName,
      request.cartItems,
    );

    if (!cartDetails.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Cart Item not Found',
      };
    }

    //For Cart Details
    let cmpCode: string;
    let cartTotalAmount = 0;
    let cartQty = 0;

    //For Scheme Details
    let schemeRequest = [];
    let isApplyScheme = 0;
    let discountPercentage: number;
    let offerAmount = 0;
    let discountAmount = 0;
    const saveFreeProd = [];
    let totalDiscountAmt = 0;
    let payableAmount = 0;

    //Find company code, product qty from cart details
    for await (const cartDetail of cartDetails) {
      cartTotalAmount += Number(cartDetail.totalAmount);
      cmpCode = cartDetail.cmpCode;
      cartQty += cartDetail.prodQty;
    }

    //Scheme calculation
    if (request.schemes.length) {
      schemeRequest = request.schemes;
      isApplyScheme = 1;
      for await (const scheme of request.schemes) {
        discountPercentage = 0;
        const distCode = [];
        if (scheme.distCode !== undefined && scheme.distCode !== null) {
          distCode.push(scheme.distCode);
        }

        const schemeRequest = {
          currentDate: new Date().toISOString().split('T')[0],
          schemeCode: [scheme.schemeCode],
          slapCode: [scheme.slapCode],
          distCode: distCode,
          cmpCode: cmpCode,
          productQty: Number(cartQty),
        };

        const schemeResponse = await this.schemeService.getSchemesByCode(
          schemeRequest,
        );
        const schemeData = schemeResponse.data;

        if (schemeData.schemeType === SchemeType.AMOUNT) {
          offerAmount += Number(schemeData.flatAmount);
        } else if (schemeData.schemeType === SchemeType.DISCOUNT) {
          discountPercentage = Number(schemeData.discount);
          discountAmount = cartTotalAmount * (discountPercentage / 100);
          totalDiscountAmt += discountAmount;
        } else if (schemeData.schemeType === SchemeType.FREEPRODUCT) {
          //Find Product
          const prodResponse = await this.productService.getFromProduct(
            {
              cmpCode: cmpCode,
              prodCode: schemeData.freeProductCode,
            },
            token,
          );

          const productDetail = prodResponse.data;

          saveFreeProd.push({
            cmpCode: cmpCode,
            distCode: productDetail.distrCode,
            prodCode: schemeData.freeProductCode,
            batchCode: productDetail.batchCode,
            prodName: productDetail.name,
            productMedia: productDetail.productMedia,
            prodQty: schemeData.freeQty,
            weightCode: productDetail.weightCode,
            weightValue: productDetail.weightValue,
          });
        }
      }
    }

    payableAmount = cartTotalAmount - totalDiscountAmt - offerAmount;

    const saveSchemeTemp = {
      userName: userName,
      cmpCode: cmpCode,
      CartItems: request.cartItems,
      schemeCodes: schemeRequest,
      overAllAmount: cartTotalAmount,
      offerAmount: offerAmount,
      discountAmount: totalDiscountAmt,
      freeProduct: saveFreeProd,
      payableAmount: payableAmount,
      isApplyScheme: isApplyScheme,
      isActiveSts: 1,
    };

    await this.saveCartHeader(saveSchemeTemp, userName);

    return {
      priceToRetailer: cartTotalAmount,
      grassAmount: cartTotalAmount, //before deduction
      netAmount: payableAmount, //after deduction
      discountAmount: cartTotalAmount - payableAmount,
      priceAfterDiscount: payableAmount,
      totalTaxAmount: 0,
      freeProduct: saveFreeProd,
    };
  }

  //Common Repository Calls
  async create(saveDetails: CreateCartDetailsDto[]) {
    const save = await this.cartRepository.create(saveDetails);
    if (!save) {
      return {
        statusCode: HttpStatus.FORBIDDEN,
        message: 'Product failed to added in Cart',
      };
    } else {
      const cartId = [];
      for await (const saveInfo of save) {
        cartId.push(saveInfo.cartListId);
      }
      return {
        statusCode: HttpStatus.CREATED,
        message: 'Cart items Saved Successfully',
        cartId: cartId,
      };
    }
  }

  async update(
    userName: string,
    cartListId: string,
    updateDetails: UpdateCartDetailDto,
    token: string,
  ) {
    const update = await this.cartRepository.update(
      userName,
      cartListId,
      updateDetails,
    );
    if (update) {
      const updatedRecord = await this.changeStructure([update], token);
      return {
        statusCode: HttpStatus.OK,
        message: 'Product quantity Updated in Successfully!',
        cartList: updatedRecord[0],
      };
    } else {
      return {
        statusCode: HttpStatus.NOT_MODIFIED,
        message: 'Product quantity not changed',
      };
    }
  }

  async delete(auth: any, request: DeleteCartDto) {
    const userName = auth.user.username;

    const deleteStatus = await this.cartRepository.deleteCartDetail(
      userName,
      request.cartIds,
    );
    if (deleteStatus) {
      return {
        statusCode: HttpStatus.OK,
        message: 'Product removed from Cart!',
      };
    } else {
      return {
        statusCode: HttpStatus.FORBIDDEN,
        message: 'Product failed to remove',
      };
    }
  }

  //Structure the Response
  async changeStructure(cartDetailsList: any, token: string) {
    const list = [];

    for (const cartDetail of cartDetailsList) {
      //Find Product
      const prodResponse = await this.productService.getFromProduct(
        {
          cmpCode: cartDetail.cmpCode,
          prodCode: cartDetail.prodCode,
          batchCode: cartDetail.batchCode,
        },
        token,
      );
      const productDetail = prodResponse.data;

      const data = {
        cartId: cartDetail.cartListId,
        cmpCode: cartDetail.cmpCode,
        distCode: cartDetail.distCode,
        prodCode: cartDetail.prodCode,
        batchCode: cartDetail.batchCode,
        prodName: cartDetail.prodName,
        productMedia: productDetail.productMedia,
        minimumOrderQty: productDetail.minimumOrderQty,
        totalOrderedQty: cartDetail.prodQty,
        // uomCode: productDetail.uom,
        uom: cartDetail.cartQtyDetails,
        weightCode: cartDetail.weightCode,
        weightValue: cartDetail.weightValue,
        mrp: productDetail.mrp,
        priceToRetailer: productDetail.priceToRetailer,
        scheme: productDetail.scheme,
        appliedScheme: productDetail.appliedScheme,
        priceAfterDiscount: productDetail.priceAfterDiscount,
        taxAmount: cartDetail.taxAmount,
        totalAmount: cartDetail.totalAmount,
      };
      list.push(data);
    }

    return list;
  }

  //Cart header save and update
  async saveCartHeader(CartHeaderData: any, userName: string) {
    const find = await this.cartRepository.getCartHeader(userName);
    if (!find) {
      const createInfo = await this.cartRepository.createCartHeader(
        CartHeaderData,
      );

      if (CartHeaderData.schemeCodes.length) {
        for await (const scheme of CartHeaderData.schemeCodes) {
          const schemeHistory = {
            cmpCode: CartHeaderData.cmpCode,
            schemeCode: scheme.schemeCode,
            userCode: userName,
            cartId: createInfo.cartId,
            orderId: '',
            schemeStatus: SchemeHistoryStatus.INITIATED,
          };
          await this.schemeService.updateSchemeHistory(schemeHistory);
        }
      }
    } else {
      await this.cartRepository.updateCartHeader(find.cartId, CartHeaderData);

      if (CartHeaderData.schemeCodes.length) {
        for await (const scheme of CartHeaderData.schemeCodes) {
          const schemeHistory = {
            cmpCode: CartHeaderData.cmpCode,
            schemeCode: scheme.schemeCode,
            userCode: userName,
            cartId: find.cartId,
            orderId: '',
            schemeStatus: SchemeHistoryStatus.INITIATED,
          };

          await this.schemeService.updateSchemeHistory(schemeHistory);
        }
      }
    }
  }

  //schemes
  async findSchemes(cartList: any, userName: string, token: string) {
    let cmpCode: string;
    let cartTotalQty = 0;
    const listOfDistCode = [];
    const listOfProdCode = [];

    for await (const cart of cartList) {
      listOfDistCode.push(cart.distCode);
      listOfProdCode.push(cart.prodCode);
      cmpCode = cart.cmpCode;
      cartTotalQty += Number(cart.totalOrderedQty);
    }

    const retailerInfo = await this.retailerService.getRetailerInfo(
      cmpCode,
      token,
    );
    let retailerChannel: any;

    if (retailerInfo.retailerCategory.length) {
      retailerChannel = {
        channelCode: retailerInfo.retailerCategory[0].channelCode,
        groupCode: retailerInfo.retailerCategory[0].groupCode,
        classCode: retailerInfo.retailerCategory[0].classCode,
      };
    } else {
      retailerChannel = {
        channelCode: retailerChannel.channelCode,
        groupCode: retailerChannel.groupCode,
        classCode: retailerChannel.classCode,
      };
    }

    if (retailerInfo !== null) {
      const requestFromScheme = {
        currentDate: new Date().toISOString().split('T')[0],
        cmpCode: cmpCode,
        distCode: listOfDistCode,
        productHier: {
          levelValue: [],
        },
        retailerChannel: retailerChannel,
        retailerCode: [retailerInfo.retailerCode],
        productCode: listOfProdCode,
        productQty: cartTotalQty,
      };
      return await this.schemeService.getSchemesList(requestFromScheme);
    } else {
      return [];
    }
  }

  //Internal API Calls
  //Get Cart Detail by id
  async getCartDetailByID(auth: any, cartListId: string) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    const cartDetails = [
      await this.cartRepository.getCartDetailById(userName, cartListId),
    ];

    if (cartDetails[0] === null) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Cart Item not Found',
      };
    }
    //Structure the List
    const cartResponse = await this.changeStructure(cartDetails, token);

    return {
      cartList: cartResponse[0],
    };
  }

  //get Cart header details
  async getCartHeaderDetails(userName: string) {
    return await this.cartRepository.getCartHeader(userName);
  }

  //cart details status change
  async updateCartStatus(cartListIds: any) {
    const data = await this.cartRepository.updateCartDetailStatus(cartListIds);
    if (data) {
      return true;
    } else {
      return false;
    }
  }

  //cart header status change
  async updateCartHeaderStatus(cartId: any) {
    const data = await this.cartRepository.updateCartHeaderStatus(cartId);
    if (data) {
      return true;
    } else {
      return false;
    }
  }
}
