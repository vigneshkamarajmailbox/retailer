import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { CreateOrderHeaderDto } from './dto/create-order-header.dto';
import { OrderRepository } from './order.repository';
import { SchemeHistoryStatus, schemeEnum } from 'src/shared/enum/pattern.enum';
import { PaginationDto, RequestOrderDto } from './dto/order.dto';
import { ProductCommunicatorService } from 'src/shared/communicators/product.service';
import { SchemeCommunicatorService } from 'src/shared/communicators/scheme.service';
import { CartCommunicatorService } from 'src/shared/communicators/cart.service';
import { RetailerCommunicatorService } from 'src/shared/communicators/retailer.service';
import * as moment from 'moment';

@Injectable()
export class OrderService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  orderRepo: OrderRepository;

  @Inject()
  private readonly httpService: HttpService;

  constructor(
    private readonly productService: ProductCommunicatorService,
    private readonly schemeService: SchemeCommunicatorService,
    private readonly cartService: CartCommunicatorService,
    private readonly retailerService: RetailerCommunicatorService,
  ) {}

  async placeOrder(auth: any, orderRequest: RequestOrderDto) {
    const userName = auth.user.username;
    const token = auth.headers.authorization;
    //Cart Variables
    let payableAmount = 0;
    let discountAmount = 0;
    let taxAmount = 0;
    let grossAmount = 0;
    let isApplyScheme = 0;
    let overAllOrderQty = 0;
    let headerCmpCode: string;
    let freeProduct = [];
    const orderDetails = [];

    for await (const cartListId of orderRequest.cartItems) {
      const cartResponse = await this.cartService.getDetailsFromCart(
        cartListId,
        token,
      );

      if (!cartResponse) {
        return {
          statusCode: HttpStatus.FORBIDDEN,
          message: 'Cart Item Not Found',
        };
      }

      payableAmount += Number(cartResponse.totalAmount);
      taxAmount += Number(cartResponse.taxAmount);
      headerCmpCode = cartResponse.cmpCode;
      overAllOrderQty += Number(cartResponse.totalOrderedQty);

      orderDetails.push({
        orderNo: 'ORDTEMPNO',
        cmpCode: cartResponse.cmpCode,
        userName: userName,
        distCode: cartResponse.distCode,
        prodCode: cartResponse.prodCode,
        prodName: cartResponse.prodName,
        batchCode: cartResponse.batchCode,
        prodImage: cartResponse.productMedia,
        prodMrp: cartResponse.mrp,
        prodRetailerPrice: cartResponse.priceToRetailer,
        prodQty: cartResponse.totalOrderedQty,
        uomCode: cartResponse.uom,
        weightCode: cartResponse.weightCode,
        weightValue: cartResponse.weightValue,
        taxPercentage: 0,
        taxAmount: cartResponse.taxAmount,
        totalAmount: cartResponse.totalAmount,
        orderStatus: 'Order Placed',
        isActive: 1,
        isFree: false,
      });
    }
    grossAmount = Number(payableAmount) + taxAmount;
    const cartHeaderInfo = await this.cartService.getFromCartHeder(
      userName,
      token,
    );
    console.log(cartHeaderInfo);

    if (cartHeaderInfo !== null) {
      freeProduct = cartHeaderInfo.freeProduct;
      payableAmount = Number(cartHeaderInfo.payableAmount);
      isApplyScheme = Number(cartHeaderInfo.isApplyScheme);

      discountAmount =
        Number(cartHeaderInfo.offerAmount) +
        Number(cartHeaderInfo.discountAmount);
      grossAmount = Number(cartHeaderInfo.overAllAmount);

      //Add Free product in order details
      if (freeProduct.length) {
        //Find Product
        const prodResponse = await this.productService.getFromProduct(
          {
            cmpCode: freeProduct[0].cmpCode,
            prodCode: freeProduct[0].prodCode,
            batchCode: freeProduct[0].batchCode,
          },
          token,
        );
        const productDetail = prodResponse.data;

        orderDetails.push({
          orderNo: 'ORDTEMPNO',
          cmpCode: productDetail.cmpCode,
          userName: userName,
          distCode: productDetail.distrCode,
          prodCode: productDetail.prodCode,
          prodName: productDetail.name,
          batchCode: productDetail.batchCode,
          prodImage: productDetail.productMedia,
          prodMrp: productDetail.mrp,
          prodRetailerPrice: 0,
          prodQty: freeProduct[0].prodQty,
          uomCode: productDetail.uom,
          weightCode: productDetail.weightType,
          weightValue: productDetail.weightValue,
          taxPercentage: 0,
          taxAmount: 0,
          totalAmount: 0,
          orderStatus: 'Order Placed',
          isActive: 1,
          isFree: true,
        });
      }
    }

    //Find Retailer Address
    let billingAddress1 = '';
    let billingAddress2 = '';
    const retailerInfo = await this.retailerService.getRetailerInfo(
      headerCmpCode,
      token,
    );

    if (retailerInfo.address.length) {
      billingAddress1 = retailerInfo.address[0].address1;
      billingAddress2 = retailerInfo.address[0].address2;
    }

    const saveOrderHeader = {
      cmpCode: headerCmpCode,
      userName: userName,
      orderDate: new Date(),
      billDate: new Date(),
      deliveryDate: new Date(),
      address1: billingAddress1,
      address2: billingAddress2,
      netAmount: payableAmount,
      grossAmount: grossAmount,
      taxAmount: Number(taxAmount),
      isApplyScheme: isApplyScheme,
      discountAmount: discountAmount,
      reOrderStatus: 0,
      orderStatus: 'Order Placed',
    };

    //save Order in header and Details
    const saveStatus = await this.saveOrders(saveOrderHeader, orderDetails);

    if (saveStatus.statusCode == 201) {
      let isProductBasedScheme = false;
      await this.cartService.changeCartDetailsStatus(
        orderRequest.cartItems,
        token,
      );
      if (isApplyScheme == 1) {
        for await (const scheme of cartHeaderInfo.schemeCodes) {
          const distCode = [];
          let distributorCode = '';
          if (scheme.distCode !== undefined && scheme.distCode !== null) {
            distCode.push(scheme.distCode);
            distributorCode = scheme.distCode;
          }

          const schemeReq = {
            currentDate: new Date().toISOString().split('T')[0],
            schemeCode: [scheme.schemeCode],
            slapCode: [scheme.slapCode],
            cmpCode: headerCmpCode,
            distCode: distCode,
            productQty: Number(overAllOrderQty),
          };
          const schemeData = await this.schemeService.getSchemesByCode(
            schemeReq,
          );

          if (schemeData.data.schemeHierTypeCode === schemeEnum.ALL_PRODUCTS) {
            isProductBasedScheme = true;
          }

          let schemeValue = '';

          if (schemeData.data.schemeType === schemeEnum.FREEPRODUCT) {
            schemeValue = schemeData.data.freeQty.toString();
          } else if (schemeData.data.schemeType === schemeEnum.DISCOUNT) {
            schemeValue = (
              grossAmount *
              (Number(schemeData.data.discount) / 100)
            ).toString();
          } else if (schemeData.data.schemeType === schemeEnum.AMOUNT) {
            schemeValue = schemeData.data.flatAmount.toString();
          }

          const applyScheme = {
            cmpCode: headerCmpCode,
            schemeCode: scheme.schemeCode,
            userCode: userName,
            orderNo: saveStatus.orderNo,
            schemeValue: schemeValue,
            distCode: distributorCode,
            isProductBasedScheme: isProductBasedScheme,
          };
          console.log(applyScheme);
          const applySchemeSts = await this.schemeService.schemesApiCal(
            applyScheme,
          );

          if (!applySchemeSts.isError) {
            const schemeHistory = {
              cmpCode: headerCmpCode,
              schemeCode: scheme.schemeCode,
              userCode: userName,
              cartId: cartHeaderInfo.cartId,
              orderId: saveStatus.orderNo,
              schemeStatus: SchemeHistoryStatus.CONFIRMED,
            };
            await this.schemeService.updateSchemeHistory(schemeHistory);
          }
        }
      }

      if (cartHeaderInfo !== null) {
        await this.cartService.changeCartHederStatus(
          cartHeaderInfo.cartId,
          token,
        );
      }
    }

    return saveStatus;
  }

  //Save Order
  async saveOrders(orderHeaderData: CreateOrderHeaderDto, orderDetails: any) {
    const saveOrderHeader = await this.orderRepo.createOrderHeader(
      orderHeaderData,
    );
    console.log('saveOrderHeader ', saveOrderHeader);
    if (!saveOrderHeader) {
      throw new HttpException(
        {
          status: HttpStatus.FORBIDDEN,
          message: 'Order not Placed',
        },
        HttpStatus.FORBIDDEN,
      );
    } else {
      const saveDataList = await this.changeOrderNo(
        orderDetails,
        saveOrderHeader.orderNo,
      );

      const saveOrder = await this.orderRepo.createOrder(saveDataList);
      console.log('saveOrder ', saveOrder);
      if (saveOrder.length == 0) {
        throw new HttpException(
          {
            status: HttpStatus.FORBIDDEN,
            message: 'Order not Placed',
          },
          HttpStatus.FORBIDDEN,
        );
      } else {
        return {
          statusCode: HttpStatus.CREATED,
          message:
            'your OrderNo #' + saveOrder[0]?.orderNo ??
            saveOrder.orderNo + ' Placed Successfully',
          orderNo: saveOrder[0]?.orderNo ?? saveOrder.orderNo,
        };
      }
    }
  }

  //Common Function
  async changeOrderNo(orderDetails: any, orderNo: any) {
    await orderDetails.forEach((order) => {
      if (order.orderNo === 'ORDTEMPNO') {
        order.orderNo = orderNo;
      }
    });
    return orderDetails;
  }

  //Fetch order History
  async findAll(auth: any, params: PaginationDto) {
    console.log(' auth.user ', auth.user);
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    const limit: number = params.limit;
    const offset: number = params.offset;

    const OrderList = await this.orderRepo.findOrder(userName, limit, offset);

    if (!OrderList.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Order History not found',
      };
    } else {
      const orderHistory = await this.changeStructure(OrderList, token);
      return {
        orderHistory: orderHistory,
        count: orderHistory.length,
        limit: limit + 10,
        offset: offset + limit,
        // totalPage: 100,
      };
    }
  }

  async findOrderById(auth: any, orderNo: string) {
    console.log('auth.user ', auth.user);
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    const orderDetails = await this.orderRepo.findOrderById(userName, orderNo);
    if (!orderDetails.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'Order Summary not found',
      };
    } else {
      const orderSummary = await this.changeStructure(orderDetails, token);

      return {
        orderSummary: orderSummary,
        termAndCondition: {
          title: 'Replacement Condition',
          details: 'Replacement details in 7 days of delivery',
        },
      };
    }
  }

  //Buy Again
  async createReOrder(auth: any, orderNo: string) {
    console.log('auth.user.username', auth.user);
    const userName = auth.user.username;
    const token = auth.headers.authorization;

    let orderHeaderDetail;
    const reOrderDetails = [];
    let orderedProducts;

    let netAmount = 0;
    let taxAmount = 0;

    const getOrder = await this.findOrderById(auth, orderNo);
    if (getOrder.statusCode == 204) {
      return getOrder;
    }
    console.log('getOrder', getOrder);
    const headerCmpCode = getOrder.orderSummary[0].cmpCode;

    if (getOrder.orderSummary) {
      orderHeaderDetail = getOrder.orderSummary;
      orderedProducts = orderHeaderDetail.orderedProduct;
    } else {
      return getOrder;
    }

    console.log('orderedProducts ', orderedProducts);
    if (orderedProducts) {
      for (const orderedProduct of orderedProducts) {
        let prodQty = 0;
        let taxperc = 0;
        let total = 0;
        let taxamt = 0;

        //Find Product
        const prodResponse = await this.productService.getFromProduct(
          {
            cmpCode: orderedProduct.cmpCode,
            prodCode: orderedProduct.prodCode,
            batchCode: orderedProduct.batchCode,
          },
          token,
        );
        const productDetail = prodResponse.data;

        prodQty = Number(orderedProduct.prodQty);
        taxperc = 0;
        total = Number(prodQty) * Number(productDetail.priceToRetailer);
        taxamt = total * (taxperc / 100);

        reOrderDetails.push({
          orderNo: 'ORDTEMPNO',
          cmpCode: productDetail.cmpCode,
          userName: userName,
          distCode: productDetail.distrCode,
          prodCode: productDetail.prodCode,
          prodName: productDetail.name,
          batchCode: productDetail.batchCode,
          prodImage: productDetail.productMedia,
          prodMrp: productDetail.mrp,
          prodRetailerPrice: productDetail.priceToRetailer,
          prodQty: orderedProduct.prodQty,
          uomCode: productDetail.uom,
          weightCode: productDetail.weightType,
          weightValue: productDetail.weightValue,
          taxPercentage: Number(taxperc),
          taxAmount: taxamt,
          totalAmount: total + taxamt,
          orderStatus: 'Order Placed',
        });

        netAmount = Number(netAmount) + Number(total);
        taxAmount = Number(taxAmount) + Number(taxamt);
      }
    }
    //Find Retailer Address
    let billingAddress1 = '';
    let billingAddress2 = '';
    const retailerInfo = await this.retailerService.getRetailerInfo(
      headerCmpCode,
      token,
    );

    if (retailerInfo.address.length) {
      billingAddress1 = retailerInfo.address.address1;
      billingAddress2 = retailerInfo.address.address2;
    }

    const saveOrderHeaderData = {
      cmpCode: headerCmpCode,
      userName: userName,
      orderDate: new Date(),
      billDate: new Date(),
      deliveryDate: new Date(),
      address1: billingAddress1,
      address2: billingAddress2,
      netAmount: netAmount,
      grossAmount: netAmount - taxAmount,
      taxAmount: taxAmount,
      isApplyScheme: 0,
      discountAmount: 0,
      reOrderStatus: 1,
      orderStatus: 'Order Placed',
    };

    return await this.saveOrders(saveOrderHeaderData, reOrderDetails);
  }

  async findReOrders(userName: string) {
    const reOrderDetails = await this.orderRepo.findReOrders(userName);
    if (!reOrderDetails.length) {
      return {
        statusCode: HttpStatus.NO_CONTENT,
        message: 'ReOrder History not found',
      };
    } else {
      return { orderHistory: reOrderDetails[0] };
    }
  }

  //Structure the Response
  async changeStructure(orderDetailsList: any, token: string) {
    const list = [];
    const productGroup = [];

    for await (const orderDetail of orderDetailsList) {
      for await (const orderedProd of orderDetail.orderedProduct) {
        productGroup.push({
          prodCode: orderedProd.prodCode,
          prodName: orderedProd.prodName,
          prodImage: orderedProd.prodImage,
          prodRetailerPrice: orderedProd.prodRetailerPrice,
          qty: orderedProd.prodQty,
          uom: orderedProd.uomCode,
        });
      }
      //Find Product
      // const prodResponse = await this.productService.getFromProduct(
      //   {
      //     cmpCode: cartDetail.cmpCode,
      //     prodCode: cartDetail.prodCode,
      //     batchCode: cartDetail.batchCode,
      //   },
      //   token,
      // );
      // const productDetail = prodResponse.data;

      const data = {
        cmpCode: orderDetail.cmpCode,
        orderNo: orderDetail.orderNo,
        orderDate: moment(orderDetail.orderDate).format(
          'DD-MM-YYYY, hh:mm:ss A',
        ),
        billDate: moment(orderDetail.billDate).format('DD-MM-YYYY, hh:mm:ss A'),
        deliveryDate: moment(orderDetail.deliveryDate).format(
          'DD-MM-YYYY, hh:mm:ss A',
        ),
        billingShippingAddress: orderDetail.address1,
        billingShippingAddress2: orderDetail.address2,
        orderStatus: orderDetail.orderStatus,
        netAmount: orderDetail.netAmount,
        grossAmount: orderDetail.grossAmount,
        taxAmount: orderDetail.taxAmount,
        productGroup: productGroup,
        taxBreakup: [
          {
            taxCode: 'GST',
            taxValue: 100,
          },
          {
            taxCode: 'CGST',
            taxValue: 80,
          },
        ],
      };

      list.push(data);
    }

    return list;
  }

  async deliveryTracking(req: any, orderNo: string) {
    const order = await this.orderRepo.findOneOrderById(
      req.user.username,
      orderNo,
    );
    console.log(order);
    if (order) {
      return {
        statusCode: 200,
        deliveryTracking: {
          orderNo: order.orderNo,
          orderDate: order.orderDate,
          orderStatus: order.orderStatus,
          orderPlacedDate: order.orderDate,
          vehicleAllocatedDate: null,
          deliveryDate: null,
          isOrderBilled: true,
          isOrderPlaced: true,
          isVehicleAllocated: false,
          isDelivered: false,
        },
      };
    } else {
      return {
        statusCode: 200,
        message: 'Order Not found',
      };
    }
  }
}
