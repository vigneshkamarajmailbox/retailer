import { Component, OnInit } from '@angular/core';
import { StringConstants } from '../service/stringconstants';
import { DataService } from '../service/dataservice';
import { OrderBookingProduct } from '../model/orderbookingproduct';
import { OrderBookingSchemeDetails } from '../model/orderbookingschemedetails';
import { OrderBookingSchemeProductRule } from '../model/orderbookingschemeproductrule';
import { CurrencyPipe } from '@angular/common';
import { AppComponent } from '../../app.component';
import { User } from '../model/user';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { OrderBookingHeader } from '../model/orderbookingheader';
import { OrderBookingDetail } from '../model/orderbookingdetail';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { fromEvent, Observable } from 'rxjs';
import { map, startWith, takeUntil } from 'rxjs/operators';
import { ConfirmationdialogComponent } from '../confirmationdialog/confirmationdialog.component';
import { ReferralsalesmanComponent } from '../referral-salesman/referral-salesman.component';
import { MatDialog } from '@angular/material/dialog';
import { DeleteconfirmationComponent } from '../deleteconfirmation/deleteconfirmation.component';
import { FavoriteProduct } from '../model/favoriteproduct';
import { OrderconfirmdialogComponent } from '../orderconfirmdialog/orderconfirmdialog.component';
import { UploadModel } from '../model/uploadmodel';
import { SchemeCustomerMapping } from "../model/schemecustomermapping";
import { SchemeSlab } from "../model/schemeslab";

@Component({
    selector: 'app-orderbooking',
    templateUrl: './orderbooking.component.html',
    styleUrls: ['./orderbooking.component.css']
})
export class OrderbookingComponent implements OnInit {


    user: User;
    cols: any;
    rowHeight: any;
    brandMap: {};
    brandList: any[];
    productList: OrderBookingProduct[];
    orgOrderBookingProduct: OrderBookingProduct[];
    taxMap: any;
    cartList = [];
    totalLineCount = 0;
    totalGrossValue: any;
    totalSchemeValue: any;
    totalTaxValue: any;
    totalOrderValue: any;
    summaryEnable = false;
    summaryList: any;
    schemeDefinition: any;
    schemeProduct: any;
    schemeSlab: any;
    schemeSlabColumns: string[];
    fromSummary = false;
    noProduct = false;
    keyGenerator: any;
    orderLevelSchemeValue: any;

    productFilterControl = new FormControl('');
    productOptions: Observable<OrderBookingProduct[]>;
    selectedIndex = 0;

    startTime = new Date();
    endTime = new Date();
    lat: any;
    lng: any;
    hideTab = false;

    recommendedProducts = '';
    favoriteProducts = {};
    configuration = {};
    showSchemeTag = false;
    enableSOQ = false;
    prodUomConvFactorMap = {};

    tabSequence = {};
    distributorCustomerMap = {};
    distributorMap = {};
    customerShipAddressMap = {};
    gstStateMap = {};
    gstStateList = [];
    distributorStockDetail = [];

    loading:boolean = false;

    constructor(public dataService: DataService, private currency: CurrencyPipe, public dialog: MatDialog,
        public app: AppComponent, private http: HttpClient, private router: Router) {
        app.checkUpdate();
    }

    ngOnInit(): void {
        history.pushState(null, '');
        fromEvent(window, 'popstate').pipe().subscribe((_) => {
            if (this.summaryEnable == true) {
                history.pushState(null, '');
                this.summaryEnable = false;
            }
        });
        this.app.show();
        this.setCurrentLocation();
        this.startTime = new Date();
        this.schemeSlabColumns = ['slabFrom', 'slabTo', 'payout', 'forEvery', 'uomCode'];
        this.brandMap = {};
        this.setCols(window.innerWidth);
        this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
        this.keyGenerator = JSON.parse(localStorage.getItem(StringConstants.CONST_ORDER_KEY_INFO));
        this.loading=true;
        this.download();
    }
    // @HostListener('window:popstate', ['$event'])
    //   onPopState(event: PopStateEvent): void {
    //   event.preventDefault();
    //   this.router.navigate(['/home']);
    // }

    setCurrentLocation() {
        navigator.geolocation.getCurrentPosition((position) => {
            this.lat = position.coords.latitude;
            this.lng = position.coords.longitude;
        }, err => {
            console.error(err);
        });
    }

    download() {
        const startTime = new Date();
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH + this.user.loginCode,
            this.dataService.getHttpOptions()).subscribe(
                (res: any) => {
                    this.convertProduct(res);
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD, startTime, StringConstants.CONST_SUCCESS);
                }, (err: HttpErrorResponse) => {
                    this.loading = false;
                    console.error(err.message);
                    this.dataService.errorPage(err);
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD, startTime, StringConstants.CONST_FAILURE);
                    this.app.hide();
                }
            );
    }

    convertProduct(res: any) {
        // declare variable
        const prodNameMap = {};
        const prodUomMap = {};
        const prodUomConvFactorMap = {};
        const distributorStockMap = {};
        const customerStockMap = {};
        const taxMap = {};
        const orderBooingProduct = [];
        const distrLobMapping = [];

        // set key generator for order booking
        res.keyGenerator.forEach(data => {
            if (data.screenName === StringConstants.CONST_SCREEN_ORDER_BOOKING
                && (this.keyGenerator === null || (data.suffixNN >= this.keyGenerator.suffixNN))) {
                this.dataService.setOrderKey(data);
                this.keyGenerator = data;
            }
        });

        // set configuration data
        if (res.configuration !== null && res.configuration !== undefined) {
            res.configuration.forEach(data => {
                this.configuration[data.tableName] = data.code;
            });
        }

        // set mapped customer details
        res.mappedCustomer.forEach(data => {
            const mappedCustomerArr = data.split('~');
            this.distributorCustomerMap[mappedCustomerArr[0] + mappedCustomerArr[1]] = mappedCustomerArr[2];
        });

        // set product name map
        res.product.forEach(data => {
            prodNameMap[data.cmpCode + data.prodCode] = data;
        });

        // set product uom map
        res.productUom.forEach(data => {
            let arr = prodUomMap[data.cmpCode + data.prodCode];
            if (arr === null || arr === undefined) {
                arr = [];
            }
            arr.push(data);
            prodUomMap[data.cmpCode + data.prodCode] = arr;
            prodUomConvFactorMap[data.cmpCode + data.prodCode + data.uomCode] = data;
        });
        this.prodUomConvFactorMap = prodUomConvFactorMap;

        // set distributor stock map
        this.distributorStockDetail = res.distributorStock;
        res.distributorStock.forEach(data => {
            distributorStockMap[data.cmpCode + data.distrCode + data.prodCode] = data.saleableQty;
        });

        // set customer stock map
        res.customerStock.forEach(data => {
            customerStockMap[data.cmpCode + data.distrCode + data.customerCode + data.prodCode] = data;
        });

        // set distributor gst state code
        res.distributor.forEach(data => {
            this.distributorMap[data.cmpCode + data.distrCode] = data.gstStateCode;
            if (!this.gstStateList.includes(data.gstStateCode)) {
                this.gstStateList.push(data.gstStateCode);
            }
        });

        // set customer gst state code
        res.customerShipAddress.forEach(data => {
            if (data.defaultShipAddr === StringConstants.CONST_YES) {
                this.customerShipAddressMap[data.cmpCode + data.distrCode + data.customerCode] = data;
            }
        });

        // set union territory flag
        res.gstStateMaster.forEach(data => {
            this.gstStateMap[data.gstStateCode] = data.unionTerritoryFlag;
        });

        // set distributor lob mapping
        res.distributorLOBMapping.forEach(data => {
            distrLobMapping.push(data.lobCode);
        });

        // set recommended products
        this.recommendedProducts = '';
        res.recommendedProducts.forEach(data => {
            this.recommendedProducts = this.recommendedProducts + data.prodCode;
        });

        // set recommended products
        this.favoriteProducts = {};
        res.favoriteProducts.forEach(data => {
            const itemList = [];
            data.favProdCode.split(StringConstants.CONST_CHAR_COMMA).forEach(item => {
                itemList.push(item);
            });
            this.favoriteProducts[data.cmpCode + StringConstants.CONST_CHAR_TILDE
                + data.distrCode + StringConstants.CONST_CHAR_TILDE + data.customerCode] = itemList;
        });

        // set scheme tag
        if (this.configuration[StringConstants.CONST_CONFIG_SHOW_SCHEME_TAG] === StringConstants.CONST_YES) {
            this.showSchemeTag = true;
        }

        // set scheme tag
        if (this.configuration[StringConstants.CONST_CONFIG_ENABLE_SOQ] === StringConstants.CONST_YES) {
            this.enableSOQ = true;
        }

        //set category sequence
        res.categorySequence.forEach(data => {
            this.tabSequence[data.Category] = data.SequenceNo;
        });

        // convert product batch listing
        res.productBatch.forEach(data => {
            const order = new OrderBookingProduct();
            order.cmpCode = data.cmpCode;
            order.distrCode = data.batchLevel;
            order.customerCode = this.distributorCustomerMap[order.cmpCode + order.distrCode];
            order.prodCode = data.prodCode;
            order.prodName = prodNameMap[data.cmpCode + data.prodCode].prodName;
            order.prodBatchCode = data.prodBatchCode;
            order.prodType = data.prodType;
            order.mrp = this.dataService.parseFloat(data.mrp);
            order.sellRate = this.dataService.parseFloat(data.sellRate);
            order.sellRateWithTax = this.dataService.parseFloat(data.sellRateWithTax);
            order.actualSellRate = this.dataService.parseFloat(data.actualSellRate);
            order.favorite = StringConstants.CONST_NO;
            order.distrStateCode = this.distributorMap[order.cmpCode + order.distrCode];
            order.customerStateCode = this.customerShipAddressMap[order.cmpCode + order.distrCode + order.customerCode].gstStateCode;
            order.customerShipCode = this.customerShipAddressMap[order.cmpCode + order.distrCode + order.customerCode].customerShipCode;
            order.unionTerritoryFlag = this.gstStateMap[order.distrStateCode];
            order.prodNetWgt = prodNameMap[data.cmpCode + data.prodCode].prodNetWgt;
            order.prodWgtType = prodNameMap[data.cmpCode + data.prodCode].prodWgtType;

            // check product for customer
            if (this.configuration[StringConstants.CONST_CONFIG_CUSTOMER_WISE_PRODUCT] === StringConstants.CONST_YES
                && (!customerStockMap.hasOwnProperty(order.cmpCode + order.distrCode + order.customerCode + order.prodCode)
                    || (customerStockMap.hasOwnProperty(order.cmpCode + order.distrCode + order.customerCode + order.prodCode)
                        && customerStockMap[order.cmpCode + order.distrCode + order.customerCode + order.prodCode].enableProduct === StringConstants.CONST_NO))) {
                return;
            }

            // set soq and product color
            order.prodColor = StringConstants.CONST_CL_WHITE;
            order.borderColor = StringConstants.CONST_ST_BORDER + StringConstants.CONST_CL_BLUE;
            if (customerStockMap.hasOwnProperty(order.cmpCode + order.distrCode + order.customerCode + order.prodCode)) {
                order.soq = customerStockMap[order.cmpCode + order.distrCode + order.customerCode + order.prodCode].soq;
                if (customerStockMap[order.cmpCode + order.distrCode + order.customerCode + order.prodCode].productColor !== null
                    && customerStockMap[order.cmpCode + order.distrCode + order.customerCode + order.prodCode].productColor !== undefined) {
                    order.prodColor = customerStockMap[order.cmpCode + order.distrCode + order.customerCode + order.prodCode].productColor;
                    order.borderColor = StringConstants.CONST_ST_BORDER + order.prodColor;
                }
            } else {
                order.soq = -1;
            }

            // set brand code and name
            const codeArr = prodNameMap[data.cmpCode + data.prodCode].productHierPathCode.split('/');
            let filterPos = StringConstants.APPLICATION_DEFAULT_BRAND_FILTER;
            if (codeArr.length > Number(localStorage.getItem(StringConstants.APPLICATION_BRAND_FILTER))) {
                filterPos = Number(localStorage.getItem(StringConstants.APPLICATION_BRAND_FILTER));
            }
            order.brandCode = codeArr[codeArr.length - filterPos];
            order.brandName = prodNameMap[data.cmpCode + data.prodCode].productHierPathName
                .split('/')[codeArr.length - filterPos];

            // convert order product listing based on product uom and lob
            if (prodUomMap.hasOwnProperty(data.cmpCode + data.prodCode)
                && prodNameMap.hasOwnProperty(data.cmpCode + data.prodCode)) {
                const uomMap = {};
                const uomDisplayList = [];
                const uomList = prodUomMap[data.cmpCode + data.prodCode].sort((a, b) => b.uomConvFactor - a.uomConvFactor);
                let i = 0;
                for (let j = 0; j < uomList.length; j++) {
                    const uom = uomList[j];
                    if (uom.baseUOM === StringConstants.CONST_YES) {
                        order.baseUOM = uom.uomCode;
                    }
                    if (uom.defaultUOM === StringConstants.CONST_YES) {
                        order.defaultUOM = uom.uomCode;
                    }
                    uomMap[uom.cmpCode + uom.prodCode + uom.uomCode] = uom.uomConvFactor;
                    uom.color = StringConstants.CONST_COLOR_PALATTE[i];
                    if (j < uomList.length - 1) {
                        let conv;
                        if (this.dataService.checkDecimal(uom.uomConvFactor / uomList[j + 1].uomConvFactor)) {
                            conv = this.dataService.parseFloat(uom.uomConvFactor / uomList[j + 1].uomConvFactor)
                        } else {
                            conv = String(uom.uomConvFactor / uomList[j + 1].uomConvFactor);
                        }
                        uomDisplayList.push({
                            value: uom.uomCode,
                            color: uom.color,
                            convSlab: conv + ' ' + uomList[j + 1].uomCode
                        });
                    } else {
                        uomDisplayList.push({
                            value: uom.uomCode,
                            color: uom.color,
                            convSlab: StringConstants.CONST_UOM_1_UNIT
                        });
                    }
                    i++;
                }
                order.uomList = uomMap;
                order.uomDisplayList = uomDisplayList;

                // check product lob
                const product = prodNameMap[data.cmpCode + data.prodCode]
                if (distrLobMapping.includes(product.lobCode) && data.mrp > 0 && data.sellRate > 0) {
                    orderBooingProduct.push(order);
                }
            }
        });
        this.orgOrderBookingProduct = orderBooingProduct;
        this.orgOrderBookingProduct.sort((a, b) => String(a.brandName + a.prodName)
            .localeCompare(String(b.brandName + b.prodName)));

        // set tax structure map
        res.taxStructure.forEach(data => {
            if (this.gstStateList.includes(data.taxStateCode)) {
                taxMap[data.cmpCode + data.taxStateCode + data.prodCode] = data;
            }
        });
        this.taxMap = taxMap;
        this.downloadScheme();
    }

    downloadScheme() {
        const startTime = new Date();
        const user = new User();
        user.loginCode = this.user.loginCode;
        user.appVersion = this.app.version;
        user.systemDate = this.dataService.formatDateToString(new Date());
        user.enableCompress = false;
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_SCHEME
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH + this.user.loginCode,
            this.dataService.getHttpOptions()).subscribe(
                (res: any) => {
                    this.convertSchemeProduct(res);
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCHEME, startTime, StringConstants.CONST_SUCCESS);
                }, (err: HttpErrorResponse) => {
                    console.error(err.message);
                    this.dataService.errorPage(err);
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCHEME, startTime, StringConstants.CONST_FAILURE);
                    this.app.hide();
                    this.loading = false;
                }
            );
    }

    // convert scheme product
    convertSchemeProduct(res: any) {
        this.schemeDefinition = {};
        const schemeMap = {};
        if (
          res.schemeDefinition !== null &&
          res.schemeDefinition !== undefined
        ) {
          res.schemeDefinition.forEach((data) => {
            this.schemeDefinition[data.cmpCode + data.schemeCode] = data;
          });
        }
        if (
          res.schemeCustomerMapping !== null &&
          res.schemeCustomerMapping !== undefined
        ) {
          res.schemeCustomerMapping.forEach((data) => {
            // set scheme map
            let schemeArr = schemeMap[data.cmpCode + data.prodCode];
            // check null condition
            if (schemeArr === null || schemeArr === undefined) {
              schemeArr = [];
            }
            // set scheme color
            data.color = StringConstants.CONST_SCHEME_COLOR;
            schemeArr.push(data);
            schemeMap[data.cmpCode + data.prodCode] = schemeArr;
          });
        }
        this.schemeProduct = schemeMap;
        this.convertSchemeSlab(res);
    }

    // convert scheme slab
    convertSchemeSlab(res: any) {
        // convert scheme slab product map
        const schemeSlabProdMap = {};
        if (res.schemeSlabProduct !== null && res.schemeSlabProduct !== undefined) {
            res.schemeSlabProduct.forEach(slabProduct => {
                let arr = schemeSlabProdMap[slabProduct.cmpCode + slabProduct.schemeCode + slabProduct.slabNo];
                if (arr === null || arr === undefined) {
                    arr = [];
                }
                slabProduct.desc = 'Get ' + slabProduct.qty + ' qty of ' + slabProduct.prodName + ' free';
                arr.push(slabProduct);
                schemeSlabProdMap[slabProduct.cmpCode + slabProduct.schemeCode + slabProduct.slabNo] = arr;
            });
        }
        const schemeSlabMap = {};
        if (res.schemeSlab !== null && res.schemeSlab !== undefined) {
            res.schemeSlab.forEach(data => {
                let arr = schemeSlabMap[data.cmpCode + data.schemeCode];
                if (arr === null || arr === undefined) {
                    arr = [];
                }
                // set scheme slab product array
                data.slabProduct = schemeSlabProdMap[data.cmpCode + data.schemeCode + data.slabNo];
                arr.push(data);
                schemeSlabMap[data.cmpCode + data.schemeCode] = arr;
            });
        }
        this.schemeSlab = schemeSlabMap;
        this.convertOrderBookingProductList();
    }

    convertOrderBookingProductList() {
        const brandList = [];
        const brandArr = [];
        const recommendedList = [];
        const favoriteList = [];

        this.orgOrderBookingProduct.forEach(data => {
            data.qty = '';
            data.mappedSchemePos = 0;
            data.showPrevNavigation = false;
            data.showNextNavigation = false;
            data.uomCode = data.defaultUOM;
            // set product image path
            data.image = localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_PRODUCT_IMAGE + data.prodCode;
            // set brand information
            let items = this.brandMap[data.brandCode];
            if (!this.brandMap.hasOwnProperty(data.brandCode)) {
                items = [];
            }
            items.push(data);
            this.brandMap[data.brandCode] = items;

            if (!brandArr.includes(data.brandCode)) {
                let seq = 99999;
                if (this.tabSequence.hasOwnProperty(data.brandCode)) {
                    seq = this.tabSequence[data.brandCode];
                }
                const obj = { code: data.brandCode, name: data.brandName, seq: seq };
                brandList.push(obj);
                brandArr.push(data.brandCode);
            }
            brandList.sort((a, b) => a.seq - b.seq);

            // set scheme available tag
            data.schemeAvailable = this.schemeProduct.hasOwnProperty(data.cmpCode + data.prodCode);

            // set scheme array
            data.schemeArr = this.schemeProduct[data.cmpCode + data.prodCode];

            // set scheme description available for the product
            let schemeDescription = '';
            let schemeCount = '';
            if (data.schemeArr !== undefined && data.schemeArr !== null && data.schemeArr.length > 0) {
                data.schemeArr.forEach((sch: SchemeCustomerMapping) => {
                    const slabArr = this.schemeSlab[sch.cmpCode + sch.schemeCode];
                    slabArr.forEach((arr: SchemeSlab) => {
                        let uomDescription = arr.uomCode;
                        if (this.prodUomConvFactorMap.hasOwnProperty(sch.cmpCode + sch.prodCode + arr.uomCode)) {
                            uomDescription = this.prodUomConvFactorMap[sch.cmpCode + sch.prodCode + arr.uomCode].uomDescription;
                        }
                        if (sch.schemeBase === StringConstants.CONST_SB_AB) {
                            arr.desc = 'From - ' + this.currency.transform(arr.slabFrom, this.app.currency, StringConstants.CONST_CURRENCY_SYMBOL);
                        } else {
                            arr.desc = 'From - ' + arr.slabFrom + ' ' + uomDescription
                        }
                        if (sch.payOutType === StringConstants.CONST_PAYOUT_TYPE_FREE_PROD) {
                            arr.desc = arr.desc + ' Get Free Product';
                        } else if (sch.payOutType === StringConstants.CONST_PAYOUT_TYPE_FLAT_AMT) {
                            arr.desc = arr.desc + ' Get ' + this.currency.transform(this.dataService.parseFloat(arr.payout), this.app.currency, StringConstants.CONST_CURRENCY_SYMBOL) + ' ' + sch.payOutType;
                        } else {
                            arr.desc = arr.desc + ' Get ' + this.dataService.parseFloat(arr.payout) + ' ' + sch.payOutType;
                        }
                        arr.everyDesc = '';
                        if (arr.forEvery > 0) {
                            arr.everyDesc = 'every ' + arr.forEvery + ' item';
                        }
                    });
                });

                data.schemeArr.forEach(sch => {
                    if (schemeDescription.length === 0) {
                        schemeDescription = schemeDescription + sch['schemeDescription'];
                    }
                });
                if (schemeDescription !== undefined && schemeDescription !== null && schemeDescription.length > 0) {
                    if (data.schemeArr.length > 2) {
                        schemeCount = '+' + (data.schemeArr.length - 1) + ' more schemes';
                    } else if (data.schemeArr.length > 1) {
                        schemeCount = '+' + (data.schemeArr.length - 1) + ' scheme';
                    }
                }
            }
            data.schemeDescription = schemeDescription;
            data.schemeCount = schemeCount;

            // set recommended product list
            if (this.recommendedProducts.includes(data.prodCode)) {
                recommendedList.push(data);
            }

            // set favorite product list
            const favArr = this.favoriteProducts[data.cmpCode + StringConstants.CONST_CHAR_TILDE
                + data.distrCode + StringConstants.CONST_CHAR_TILDE + data.customerCode];
            if (favArr !== null && favArr !== undefined && favArr.includes(data.prodCode)) {
                data.favorite = StringConstants.CONST_YES;
                favoriteList.push(data);
            } else {
                data.favorite = StringConstants.CONST_NO;
            }
        });

        if (favoriteList.length > 0) {
            const robj = {
                code: StringConstants.CONST_FAVORITE_PRODUCT_CODE,
                name: StringConstants.CONST_FAVORITE_PRODUCT_NAME
            };
            brandList.unshift(robj);
            this.brandMap[StringConstants.CONST_FAVORITE_PRODUCT_CODE] = favoriteList;
        }
        this.brandList = brandList;
        if (this.orgOrderBookingProduct.length === 0) {
            this.noProduct = true;
        } else {
            // this.productList = this.brandMap[this.brandList[0].code];
            this.brandMap[this.brandList[0].code].forEach(element => {
                this.distributorStockDetail.forEach(data => {
                    if ((element.prodCode == data.prodCode) && (element.distrCode == data.distrCode) && (element.cmpCode == data.cmpCode)) {
                        element.saleableQty = data.saleableQty;
                    }
                })
                if (element.saleableQty > 0) {
                    element.prodColor = StringConstants.CONST_CL_GREEN;
                } else {
                    element.saleableQty = 0;
                    element.prodColor = StringConstants.CONST_CL_RED;
                }
                element.selectedScheme = element?.schemeArr? element?.schemeArr[element?.mappedSchemePos] : null ;
                element.slabArr = this.schemeSlab[element?.selectedScheme?.cmpCode + element?.selectedScheme?.schemeCode];
            });
            this.productList = this.brandMap[this.brandList[0].code];
        }
        this.app.hide();
        this.loading = false;
    }

    onResize(event) {
        this.setCols(event.target.innerWidth);
    }

    setCols(width: any) {
        if (width <= 640) {
            this.cols = 1;
            this.rowHeight = '5:4';
        } else if (width <= 1024) {
            this.cols = 2;
            this.rowHeight = '4:3';
        } else {
            this.cols = 3;
            this.rowHeight = '4:3';
        }
    }

    addQty(data: any, sellable : any) {
        // if(sellable > 0 && data?.qty+1 <= sellable ) {
        if (data.qty === null || data.qty === '') {
            data.qty = 1;
        } else {
            data.qty = Number(data.qty) + 1;
        }
        this.modifyCartList(data, false);
    // }
    }

    onChangeReset(data: any, sellable : any) {
        data.qty = Number(sellable)
    
    }

    removeQty(data: any, sellable:any) {
        // if(sellable > 0) {
        if (data.qty === null || data.qty === '') {
            data.qty = 0;
        } else {
            if (data.qty > 0) {
                data.qty = Number(data.qty) - 1;
            } else {
                data.qty = 0;
            }
        }
        this.modifyCartList(data, false);
    // }
    }

    onUomChange(data: any, value: any) {
        data.uomCode = value;
        this.modifyCartList(data, false);
    }

    modifyCartList(rowData: any, fromSummary: boolean) {
        // if ((rowData.saleableQty >= rowData.qty) && (rowData.saleableQty != 0)) {
            this.fromSummary = fromSummary;
            this.resetTotalValue(rowData);
        // } 
        // else {
        //     this.addOrRemoveToList(rowData);
        // }

    }

    resetTotalValue(rowData) {
        // reset order value fields
        rowData.orderQty = 0;
        rowData.grossValue = 0;
        rowData.orderValue = 0;
        rowData.actualSellRate = 0;
        // reset tax value field
        rowData.taxAmt = 0;
        // reset scheme value fields
        rowData.schAmt = 0;
        rowData.convertedSchemeQty = 0;
        rowData.schemeAmtDetail = [];
        rowData.freeSchemeProduct = [];
        rowData.schemeSuggestion = false;
        rowData.schemeSuggestionText = '';
        rowData.freeSchemeText = '';
        // reset total value fields
        this.totalGrossValue = 0;
        this.totalSchemeValue = 0;
        this.totalTaxValue = 0;
        this.totalOrderValue = 0;
        this.grossValueCalc(rowData);
    }

    grossValueCalc(rowData: OrderBookingProduct) {
        rowData.inputStr = rowData.qty + ' ' + rowData.uomCode;
        rowData.orderQty = rowData.qty * rowData.uomList[rowData.cmpCode + rowData.prodCode + rowData.uomCode];
        rowData.grossValue = rowData.orderQty * rowData.sellRate;
        rowData.actualSellRate = rowData.sellRate * rowData.uomList[rowData.cmpCode + rowData.prodCode + rowData.uomCode];
        this.schemeCalculation(rowData);
    }

    schemeCalculation(rowData: OrderBookingProduct) {
        if (rowData.qty > 0) {
            // check scheme product
            const schemeArr = this.schemeProduct[rowData.cmpCode + rowData.prodCode];
            if (schemeArr !== null && schemeArr !== undefined) {
                // iterate applicable schemes
                schemeArr.forEach(data => {
                    // check scheme definition for line level and continue
                    const def = this.schemeDefinition[data.cmpCode + data.schemeCode];
                    if (def === null || def === undefined || def.isSkuLevel === StringConstants.CONST_NO) {
                        return;
                    }
                    // iterate scheme slab
                    const slabArr = this.schemeSlab[data.cmpCode + data.schemeCode];
                    for (let j = 0; j < slabArr.length; j++) {
                        const slab = slabArr[j];
                        let nextSlab = null;
                        if (j < slabArr.length - 1) {
                            nextSlab = slabArr[j + 1];
                        }
                        let discPerc = 0;
                        let schemeAmt = 0;
                        let freeQty = 0;
                        // convert to scheme slab uom qty to check quantity based scheme
                        if (data.schemeBase === StringConstants.CONST_SB_QB) {
                            rowData.convertedSchemeQty = rowData.orderQty / rowData.uomList[rowData.cmpCode + rowData.prodCode + slab.uomCode];
                        }
                        // convert to scheme slab uom qty to check weight based scheme
                        let prodOrderWeight = 0;
                        if (data.schemeBase === StringConstants.CONST_SB_WB) {
                            // convert ordered product net weight
                            if (rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_KILO_GRAM
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE_1) {
                                prodOrderWeight = rowData.orderQty * rowData.prodNetWgt * 1000;
                            } else if (rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM_1
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_MILLI_LITRE) {
                                prodOrderWeight = rowData.orderQty * rowData.prodNetWgt;
                            }

                            // convert ordered product net weight to scheme slab unit
                            if (slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_KILO_GRAM
                                || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE
                                || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE_1) {
                                rowData.convertedSchemeWeight = prodOrderWeight / 1000;
                            } else if (slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM
                                || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM_1
                                || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_MILLI_LITRE) {
                                rowData.convertedSchemeWeight = prodOrderWeight;
                            }
                        }

                        // check the slab with entered qty
                        if ((data.schemeBase === StringConstants.CONST_SB_QB
                            && slab.slabFrom <= rowData.convertedSchemeQty
                            && ((nextSlab === null && slab.slabTo === 0)
                                || (nextSlab !== null && rowData.convertedSchemeQty < nextSlab.slabFrom)))
                            || (data.schemeBase === StringConstants.CONST_SB_AB
                                && slab.slabFrom <= rowData.grossValue
                                && ((nextSlab === null && slab.slabTo === 0)
                                    || (nextSlab !== null && rowData.grossValue < nextSlab.slabFrom)))
                            || (data.schemeBase === StringConstants.CONST_SB_WB
                                && slab.slabFrom <= rowData.convertedSchemeWeight
                                && ((nextSlab === null && slab.slabTo === 0)
                                    || (nextSlab !== null && rowData.convertedSchemeWeight < nextSlab.slabFrom)))) {
                            if (data.payOutType === StringConstants.CONST_PAYOUT_TYPE_DISC) {
                                // discount % scheme
                                schemeAmt = rowData.grossValue * (slab.payout / 100);
                                discPerc = slab.payout;
                            } else if (data.payOutType === StringConstants.CONST_PAYOUT_TYPE_FLAT_AMT) {
                                // flat amount scheme
                                if (slab.forEvery > 0) {
                                    if (data.schemeBase === StringConstants.CONST_SB_WB) {
                                        schemeAmt = Math.floor(rowData.convertedSchemeWeight / slab.forEvery) * slab.payout;
                                    } else {
                                        schemeAmt = Math.floor(rowData.orderQty / slab.forEvery) * slab.payout;
                                    }
                                } else {
                                    schemeAmt = slab.payout;
                                }
                            } else if (data.payOutType === StringConstants.CONST_PAYOUT_TYPE_FREE_PROD
                                && slab.slabProduct !== null && slab.slabProduct !== undefined) {
                                // free product scheme
                                slab.slabProduct.forEach(slabProduct => {
                                    if (slab.forEvery > 0) {
                                        if (data.schemeBase === StringConstants.CONST_SB_QB) {
                                            freeQty = Math.floor(rowData.orderQty / slab.forEvery) * slabProduct.qty;
                                        } else {
                                            freeQty = Math.floor(rowData.convertedSchemeWeight / slab.forEvery) * slabProduct.qty;
                                        }
                                    } else {
                                        freeQty = slabProduct.qty;
                                    }
                                    const freeSchemeProduct = new OrderBookingSchemeDetails();
                                    freeSchemeProduct.cmpCode = rowData.cmpCode;
                                    freeSchemeProduct.distrCode = rowData.distrCode;
                                    freeSchemeProduct.schemeCode = slabProduct.schemeCode;
                                    freeSchemeProduct.slabNo = slabProduct.slabNo;
                                    freeSchemeProduct.freeProdCode = slabProduct.prodCode;
                                    freeSchemeProduct.freeQty = freeQty;
                                    rowData.freeSchemeProduct.push(freeSchemeProduct);
                                    if (rowData.freeSchemeText !== null && rowData.freeSchemeText !== undefined
                                        && rowData.freeSchemeText !== '') {
                                        rowData.freeSchemeText = ' and ';
                                    }
                                    rowData.freeSchemeText = slabProduct.prodName + '(Qty: ' + freeQty + ')';
                                    rowData.schemeSuggestion = true;
                                });
                            }
                            if (schemeAmt > 0) {
                                const schemeAmtDetail = new OrderBookingSchemeProductRule();
                                schemeAmtDetail.schemeCode = data.schemeCode;
                                schemeAmtDetail.slabNo = slab.slabNo;
                                schemeAmtDetail.prodCode = rowData.prodCode;
                                schemeAmtDetail.disPerc = discPerc;
                                schemeAmtDetail.discAmt = schemeAmt;
                                schemeAmtDetail.cmpCode = rowData.cmpCode;
                                schemeAmtDetail.distrCode = rowData.distrCode;
                                rowData.schemeAmtDetail.push(schemeAmtDetail);
                                rowData.schAmt = this.dataService.checkNumber(rowData.schAmt) + schemeAmt;
                                rowData.schemeAmtText = this.currency.transform(rowData.schAmt, this.app.currency, StringConstants.CONST_CURRENCY_SYMBOL);
                                rowData.schemeSuggestion = true;
                            }
                            if (rowData.schemeAmtText !== null && rowData.schemeAmtText !== undefined && rowData.schemeAmtText.length > 0) {
                                rowData.schemeSuggestionText = 'You have ' + rowData.schemeAmtText + ' off';
                            }
                            if (rowData.freeSchemeText !== null && rowData.freeSchemeText !== undefined && rowData.freeSchemeText.length > 0) {
                                if (rowData.schemeSuggestionText.length > 0) {
                                    rowData.schemeSuggestionText = rowData.schemeSuggestionText + ' and get product ' + rowData.freeSchemeText + ' free';
                                } else {
                                    rowData.schemeSuggestionText = 'You will get product ' + rowData.freeSchemeText + ' free';
                                }
                            }
                        }
                    }
                });
            }
        }
        this.orderCalculation(rowData);
    }

    orderCalculation(rowData: OrderBookingProduct) {
        this.addOrRemoveToList(rowData);
    }

    addOrRemoveToList(rowData) {
        const index = this.cartList.findIndex(data =>
        (data.cmpCode === rowData.cmpCode && data.distrCode === rowData.distrCode
            && data.prodCode === rowData.prodCode && data.prodBatchCode === rowData.prodBatchCode));
        // if (index < 0 && rowData.qty > 0 && rowData.saleableQty >= rowData.qty) {
        if (index < 0 && rowData.qty > 0) {
            this.cartList.push(rowData);
        // } else if (index >= 0 && (rowData.qty === 0 || rowData.qty === null || rowData.qty === undefined || rowData.qty === '' || rowData.qty > rowData.saleableQty)) {
        } else if (index >= 0 && (rowData.qty === 0 || rowData.qty === null || rowData.qty === undefined || rowData.qty === '')) {
            this.cartList.splice(index, 1);
        } else if (index >= 0 && rowData.qty > 0) {
            this.cartList[index] = rowData;
        }
        this.orderLevelSchemeCalculation(rowData);
    }

    orderLevelSchemeCalculation(lineLevelData) {
        this.orderLevelSchemeValue = 0;
        const orderLevelWgtSchemeMap = {};
        const orderLevelSchemeAmtMap = {};
        // convert order level scheme wgt
        this.cartList.forEach(rowData => {
            if (rowData.qty > 0) {
                // check scheme product
                const schemeArr = this.schemeProduct[rowData.cmpCode + rowData.prodCode];
                if (schemeArr !== null && schemeArr !== undefined) {
                    // iterate applicable schemes
                    schemeArr.forEach(data => {
                        // check scheme definition for order level and continue
                        const def = this.schemeDefinition[data.cmpCode + data.schemeCode];
                        if (def === null || def === undefined || def.isSkuLevel === StringConstants.CONST_YES) {
                            return;
                        }
                        if (data.schemeBase === StringConstants.CONST_SB_WB) {
                            let prodOrderWeight = 0;
                            // convert ordered product net weight
                            if (rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE_1
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_KILO_GRAM) {
                                prodOrderWeight = rowData.orderQty * rowData.prodNetWgt * 1000;
                            } else if (rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_VOL_MILLI_LITRE
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM
                                || rowData.prodWgtType.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM_1) {
                                prodOrderWeight = rowData.orderQty * rowData.prodNetWgt;
                            }
                            orderLevelWgtSchemeMap[data.cmpCode + data.schemeCode] = this.dataService.checkNumber
                                (orderLevelWgtSchemeMap[data.cmpCode + data.schemeCode]) + prodOrderWeight;
                        }
                    });
                }
            }
        });

        // convert order level scheme amt
        for (const [key, value] of Object.entries(orderLevelWgtSchemeMap)) {
            const slabArr = this.schemeSlab[key];
            const schemeDefinition = this.schemeDefinition[key];
            for (let j = 0; j < slabArr.length; j++) {
                const slab = slabArr[j];
                let nextSlab = null;
                if (j < slabArr.length - 1) {
                    nextSlab = slabArr[j + 1];
                }
                let convertedSchemeWeight;
                // convert ordered product net weight to scheme slab unit
                if (slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE
                    || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_LITRE_1
                    || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_KILO_GRAM) {
                    convertedSchemeWeight = this.dataService.checkNumber(value) / 1000;
                } else if (slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM_1
                    || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_VOL_MILLI_LITRE
                    || slab.uomCode.toUpperCase() === StringConstants.CONST_PROD_WGT_GRAM) {
                    convertedSchemeWeight = this.dataService.checkNumber(value) * 1000;
                }

                let schemeAmt = 0;
                if (slab.slabFrom <= convertedSchemeWeight
                    && ((nextSlab === null && slab.slabTo === 0)
                        || (nextSlab !== null && convertedSchemeWeight < nextSlab.slabFrom))) {
                    if (schemeDefinition.payOutType === StringConstants.CONST_PAYOUT_TYPE_FLAT_AMT) {
                        // flat amount scheme
                        if (slab.forEvery > 0) {
                            schemeAmt = Math.floor(convertedSchemeWeight / slab.forEvery) * slab.payout;
                        } else {
                            schemeAmt = slab.payout;
                        }
                    }

                    if (schemeAmt > 0) {
                        let schemeVal = 0;
                        if (orderLevelSchemeAmtMap.hasOwnProperty(key)) {
                            schemeVal = orderLevelSchemeAmtMap[key].discAmt;
                        }
                        const schemeAmtDetail = new OrderBookingSchemeProductRule();
                        schemeAmtDetail.schemeCode = slab.schemeCode;
                        schemeAmtDetail.slabNo = slab.slabNo;
                        schemeAmtDetail.payout = slab.payout;
                        schemeAmtDetail.discAmt = this.dataService.checkNumber(schemeVal) + schemeAmt;
                        orderLevelSchemeAmtMap[key] = schemeAmtDetail;
                    }
                }
            }
        }
        // total order level scheme value
        for (const [key, value] of Object.entries(orderLevelSchemeAmtMap)) {
            const val = value as OrderBookingSchemeProductRule;
            this.orderLevelSchemeValue = this.dataService.checkNumber(this.orderLevelSchemeValue)
                + this.dataService.checkNumber(val.discAmt);
        }
        // split the order level sch amt to line level
        this.cartList.forEach(rowData => {
            if (rowData.qty > 0) {
                rowData.orderLevelSchAmt = 0;
                // check scheme product
                const schemeArr = this.schemeProduct[rowData.cmpCode + rowData.prodCode];
                if (schemeArr !== null && schemeArr !== undefined) {
                    // iterate applicable schemes
                    schemeArr.forEach(data => {
                        // check scheme definition order level and continue
                        const def = this.schemeDefinition[data.cmpCode + data.schemeCode];
                        if (def === null || def === undefined || def.isSkuLevel === StringConstants.CONST_YES) {
                            return;
                        }

                        // scheme amt detail
                        if (orderLevelSchemeAmtMap !== null && orderLevelSchemeAmtMap !== undefined
                            && orderLevelSchemeAmtMap.hasOwnProperty(data.cmpCode + data.schemeCode)) {
                            const val = orderLevelSchemeAmtMap[data.cmpCode + data.schemeCode] as OrderBookingSchemeProductRule;
                            const schemeAmtDetail = new OrderBookingSchemeProductRule();
                            schemeAmtDetail.cmpCode = rowData.cmpCode;
                            schemeAmtDetail.distrCode = rowData.distrCode;
                            schemeAmtDetail.prodCode = rowData.prodCode;
                            schemeAmtDetail.schemeCode = val.schemeCode;
                            schemeAmtDetail.slabNo = val.slabNo;
                            schemeAmtDetail.disPerc = 0;
                            schemeAmtDetail.discAmt = ((rowData.orderQty * rowData.prodNetWgt)
                                / this.dataService.checkNumber(orderLevelWgtSchemeMap[data.cmpCode + data.schemeCode])) * val.discAmt;
                            rowData.schemeAmtDetail.push(schemeAmtDetail);
                            rowData.orderLevelSchAmt = this.dataService.checkNumber(rowData.orderLevelSchAmt) + schemeAmtDetail.discAmt;
                            rowData.schemeSuggestion = true;
                            rowData.schemeAmtText = this.currency.transform(rowData.orderLevelSchAmt, this.app.currency,
                                StringConstants.CONST_CURRENCY_SYMBOL);
                            if (rowData.schemeAmtText !== null && rowData.schemeAmtText !== undefined && rowData.schemeAmtText.length > 0) {
                                rowData.schemeSuggestionText = 'You have ' + rowData.schemeAmtText + ' off'
                            }
                        }
                    });
                }
            }
        });
        this.taxCalculation(lineLevelData);
    }

    taxCalculation(rowData: OrderBookingProduct) {
        const tax = this.taxMap[rowData.cmpCode + rowData.distrStateCode + rowData.prodCode];
        let taxableValue;
        if (this.configuration[StringConstants.CONST_CONFIG_TAXABLE_VALUE_WITHOUT_SCHEME] === StringConstants.CONST_YES) {
            taxableValue = rowData.grossValue - (rowData.schAmt + rowData.orderLevelSchAmt);
        } else {
            taxableValue = rowData.grossValue;
        }
        if (tax !== undefined && tax !== null) {
            rowData.taxCode = rowData.distrStateCode;
            if (rowData.distrStateCode === rowData.customerStateCode) {
                // apply cgst tax
                rowData.cgstPerc = tax.cgst;
                rowData.cgstAmt = taxableValue * (rowData.cgstPerc / 100);
                // check union territory
                if (rowData.unionTerritoryFlag === StringConstants.CONST_YES) {
                    // apply ugst tax
                    rowData.ugstPerc = tax.sgst;
                    rowData.ugstAmt = taxableValue * (rowData.ugstPerc / 100);
                } else {
                    // apply sgst tax
                    rowData.sgstPerc = tax.sgst;
                    rowData.sgstAmt = taxableValue * (rowData.sgstPerc / 100);
                }
            } else {
                // apply igst tax
                rowData.igstPerc = tax.igst;
                rowData.igstAmt = taxableValue * (rowData.igstPerc / 100);
            }
            rowData.taxAmt = this.dataService.checkNumber(rowData.taxAmt) + this.dataService.checkNumber(rowData.sgstAmt)
                + this.dataService.checkNumber(rowData.cgstAmt) + this.dataService.checkNumber(rowData.igstAmt)
                + this.dataService.checkNumber(rowData.ugstAmt);
        }
        this.calculateTotal();
    }

    calculateTotal() {
        this.totalGrossValue = 0;
        this.totalSchemeValue = 0;
        this.totalTaxValue = 0;
        this.totalOrderValue = 0;
        if (this.cartList.length > 0) {
            this.cartList.forEach(data => {
                // set order value
                data.orderValue = data.grossValue - (data.schAmt + data.orderLevelSchAmt) + data.taxAmt;
                // set total gross value
                this.totalGrossValue = this.totalGrossValue + this.dataService.checkNumber(data.grossValue);
                // set total scheme value
                this.totalSchemeValue = this.totalSchemeValue + this.dataService.checkNumber(data.schAmt)
                    + this.dataService.checkNumber(data.orderLevelSchAmt);
                // set total tax value
                this.totalTaxValue = this.totalTaxValue + this.dataService.checkNumber(data.taxAmt);
                // set total order/net value
                this.totalOrderValue = this.totalOrderValue + this.dataService.checkNumber(data.orderValue);
            });
        }
        this.totalGrossValue = this.dataService.parseFloat(this.totalGrossValue);
        this.totalSchemeValue = this.dataService.parseFloat(this.totalSchemeValue);
        this.totalTaxValue = this.dataService.parseFloat(this.totalTaxValue);
        this.totalOrderValue = this.dataService.parseFloat(this.totalOrderValue);
        this.totalLineCount = this.cartList.length;
        if (this.fromSummary && this.cartList.length === 0) {
            this.editCart();
        } else if (this.fromSummary) {
            this.addToCart();
        }
    }

    getBrandImage(code: string) {
        const arr = this.brandMap[code];
        return arr[0].image;
    }

    addToCart() {
        let lineValid = true;
        if (this.cartList.length === 0) {
            this.summaryEnable = false;
            this.app.openSnackBar(StringConstants.MSG_NO_ORDER_PLACED);
            return;
        }
        if (this.totalOrderValue <= 1) {
            this.summaryEnable = false;
            this.app.openSnackBar(StringConstants.MSG_VALID_ORDER_VALUE);
            if (this.fromSummary) {
                this.editCart();
            }
            return;
        }
        if (this.cartList.length > 0) {
            const summaryList = [];
            this.cartList.forEach(data => {
                if (data.orderValue <= 0) {
                    lineValid = false;
                }
                summaryList.push(data);
            });
            if (lineValid) {
                this.summaryEnable = true;
                this.summaryList = summaryList;
            } else {
                this.summaryEnable = false;
                this.app.openSnackBar(StringConstants.MSG_VALID_LINE_ORDER_VALUE);
            }
        }
    }

    editCart() {
        if (this.brandList !== null && this.brandList !== undefined) {
            if (this.brandList.length > 1 && this.brandList[0].code === StringConstants.CONST_EDIT_ORDER_CODE) {
                this.brandList.shift();
            }
            const editList = [];
            this.cartList.forEach(data => {
                editList.push(data);
            });
            if (editList.length > 0) {
                const robj = {
                    code: StringConstants.CONST_EDIT_ORDER_CODE,
                    name: StringConstants.CONST_EDIT_ORDER_NAME
                };
                this.brandList.unshift(robj);
                this.brandMap[StringConstants.CONST_EDIT_ORDER_CODE] = editList;
            }
        }
        this.productList = this.brandMap[this.brandList[0].code];
        this.productList.sort((a, b) => b.qty - a.qty);
        this.selectedIndex = 0;
        this.summaryEnable = false;
    }

    confirmCart() {
        if (this.totalOrderValue <= 1) {
            this.app.openSnackBar(StringConstants.MSG_VALID_ORDER_VALUE);
            return;
        }
        this.orderPlacementConfirmDialog();
    }

    checkFavorite(data: any, fav: string) {
        data.favorite = fav;
        let arr = this.favoriteProducts[data.cmpCode + StringConstants.CONST_CHAR_TILDE
            + data.distrCode + StringConstants.CONST_CHAR_TILDE + data.customerCode];
        if (arr === null || arr === undefined) {
            arr = [];
        }
        if (data.favorite === StringConstants.CONST_YES && !arr.includes(data.prodCode)) {
            arr.push(data.prodCode);
        } else {
            const pos = arr.findIndex(item => (item === data.prodCode));
            arr.splice(pos, 1);
        }
        this.favoriteProducts[data.cmpCode + StringConstants.CONST_CHAR_TILDE
            + data.distrCode + StringConstants.CONST_CHAR_TILDE + data.customerCode] = arr;
    }

    showSchemeCard(data: OrderBookingProduct) {
        data.showSchemeCard = !data.showSchemeCard;
        if (data.schemeArr !== null && data.schemeArr !== undefined && data.schemeArr.length > 0) {
            data.selectedScheme = data.schemeArr[data.mappedSchemePos];
            data.slabArr = this.schemeSlab[data.selectedScheme.cmpCode + data.selectedScheme.schemeCode];
            if (data.schemeArr.length > 1) {
                data.showNextNavigation = true;
            }
        }
    }

    selectNextScheme(data: OrderBookingProduct) {
        if (data.mappedSchemePos >= 0 && data.mappedSchemePos < data.schemeArr.length - 1) {
            data.mappedSchemePos = data.mappedSchemePos + 1;
            data.selectedScheme = data.schemeArr[data.mappedSchemePos];
            data.slabArr = this.schemeSlab[data.selectedScheme.cmpCode + data.selectedScheme.schemeCode];
        }
        this.enableNavigation(data);
    }

    selectPrevScheme(data: OrderBookingProduct) {
        if (data.mappedSchemePos > 0 && data.mappedSchemePos <= data.schemeArr.length - 1) {
            data.mappedSchemePos = data.mappedSchemePos - 1;
            data.selectedScheme = data.schemeArr[data.mappedSchemePos];
            data.slabArr = this.schemeSlab[data.selectedScheme.cmpCode + data.selectedScheme.schemeCode];
        }
        this.enableNavigation(data);
    }

    enableNavigation(data: OrderBookingProduct) {
        if (data.schemeArr.length > 1) {
            data.showNextNavigation = data.mappedSchemePos >= 0 && data.mappedSchemePos < data.schemeArr.length - 1;
            data.showPrevNavigation = data.mappedSchemePos > 0 && data.mappedSchemePos <= data.schemeArr.length - 1;
        } else {
            data.showPrevNavigation = false;
            data.showNextNavigation = false;
        }
    }

    deleteFromCart(data: OrderBookingProduct) {
        data.qty = 0;
        this.deleteConfirmDialog(data);
    }

    private _filter(value: string): OrderBookingProduct[] {
        const filterValue = value.toLowerCase();
        return this.orgOrderBookingProduct.filter(prod => prod.prodName.toLowerCase().indexOf(filterValue) !== -1
            || prod.prodCode.toLowerCase().indexOf(filterValue) !== -1
            || prod.brandCode.toLowerCase().indexOf(filterValue) !== -1
            || prod.brandName.toLowerCase().indexOf(filterValue) !== -1);
    }

    onProductFilterChange(data: OrderBookingProduct) {
        this.hideTab = true;
        this.productList = [];
        this.app.show();
        const pos = JSON.stringify(this.brandMap[data.brandCode].findIndex(prod =>
        (data.cmpCode === prod.cmpCode && data.distrCode === prod.distrCode
            && data.prodCode === prod.prodCode && data.prodBatchCode === prod.prodBatchCode)));
        const item = this.brandMap[data.brandCode][pos];
        if (!item.slabArr || item.slabArr.length == 0) {
          item.slabArr = undefined;
        }
        const arr = [];
        arr.push(item);
        this.productList = arr;
        this.resetOptions();
        this.app.hide();
    }

    orderBookingBrandTabChange(event: any) {
        this.searchProducts(event.index);
    }

    resetProductSearchFilter() {
        this.hideTab = false;
        this.searchProducts(this.selectedIndex);
    }

    searchProducts(index: number) {
        this.app.show();
        // this.productList = this.brandMap[this.brandList[index].code];
        this.brandMap[this.brandList[index].code].forEach(element => {
            this.distributorStockDetail.forEach(data => {
                if ((element.prodCode == data.prodCode) && (element.distrCode == data.distrCode) && (element.cmpCode == data.cmpCode)) {
                    element.saleableQty = data.saleableQty;
                }
            })
            if (element.saleableQty > 0) {
                element.prodColor = StringConstants.CONST_CL_GREEN;
            } else {
                element.saleableQty = 0;
                element.prodColor = StringConstants.CONST_CL_RED;
            }
            element.selectedScheme = element?.schemeArr? element?.schemeArr[element?.mappedSchemePos] : null ;
            element.slabArr = this.schemeSlab[element?.selectedScheme?.cmpCode + element?.selectedScheme?.schemeCode];
        });
        this.productList = this.brandMap[this.brandList[index].code];

        this.productList.sort((a, b) => b.qty - a.qty);
        this.productFilterControl = new FormControl('');
        this.resetOptions();
        this.app.hide();
    }

    resetOptions() {
        this.productOptions = this.productFilterControl.valueChanges
            .pipe(startWith(''), map(value => value.length >= 4 ? this._filter(value) : [])
            );
    }

    confirmDialog(refNo, page?: any): void {
        if (page === 'refconfirm') {
            const pageref = 1;
            const dialogRef = this.dialog.open(ConfirmationdialogComponent, {
                width: '500px',
                data: { pageref }
            });

            dialogRef.afterClosed().subscribe(() => {
                window.location.reload();
            });
        }
        else {
            const delivery = localStorage.getItem(StringConstants.TENTATIVE_DELIVERY);
            const dialogRef = this.dialog.open(ConfirmationdialogComponent, {
                width: '500px',
                data: { refNo, delivery }
            });

            dialogRef.afterClosed().subscribe(() => {
                this.refSalesmanDisalog(refNo)
            });
        }
    }

    refSalesmanDisalog(refNo): void {
        // let salesmanData: any;
        // this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_SALESMAN).subscribe(resp => {
        //     if (resp) {
        //         salesmanData = resp;
        //     }
        // });

        const delivery = localStorage.getItem(StringConstants.TENTATIVE_DELIVERY);
        const dialogRef = this.dialog.open(ReferralsalesmanComponent, {
            width: '500px',
            autoFocus: false, 
            data: { refNo, delivery }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === StringConstants.CONST_YES) {
                this.confirmDialog(refNo, 'refconfirm');
            }
            else {
                window.location.reload();
            }
        });

        // setTimeout(() => {
        //     const delivery = localStorage.getItem(StringConstants.TENTATIVE_DELIVERY);
        //     const dialogRef = this.dialog.open(ReferralsalesmanComponent, {
        //         width: '500px',
        //         data: { refNo, delivery, salesmanData }
        //     });

        //     dialogRef.afterClosed().subscribe(() => {
        //         window.location.reload();
        //         // this.router.navigate([StringConstants.REDIRECT_LANDING_HOME]);
        //     });
        // }, 100); 
    }

    deleteConfirmDialog(orderBookingProduct: OrderBookingProduct): void {
        const prodName = orderBookingProduct.prodName;
        const dialogRef = this.dialog.open(DeleteconfirmationComponent, {
            width: '500px',
            data: { prodName }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === StringConstants.CONST_YES) {
                this.modifyCartList(orderBookingProduct, true);
            }
        });
    }

    orderPlacementConfirmDialog(): void {
        const dialogRef = this.dialog.open(OrderconfirmdialogComponent, {
            width: '500px',
            data: {
                totalGrossValue: this.totalGrossValue,
                totalDiscount: this.totalSchemeValue,
                totalTax: this.totalTaxValue,
                totalOrderValue: this.totalOrderValue,
                itemCount: this.totalLineCount
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === StringConstants.CONST_YES) {
                this.endTime = new Date();
                const orderBookingHeaderList = [];
                const keyGeneratorList = [];
                const orderBookingDetailsMap = {};
                const orderBookingSchemeDetailsMap = {};
                const orderBookingSchemeProductRuleMap = {};
                const totalGrossValueMap = {};
                const totalSchemeValueMap = {};
                const totalTaxValueMap = {};
                const totalOrderValueMap = {};
                this.cartList.forEach((data: OrderBookingProduct) => {
                    const orderNo = data.customerCode + this.dataService.formatDateToRefNo(new Date()) + String(this.keyGenerator.suffixNN).padStart(5, '0');
                    const orderKey = data.cmpCode + data.distrCode + orderNo;
                    // set total gross value
                    let totalGrossValue = this.dataService.checkNumber(totalGrossValueMap[orderKey]);
                    totalGrossValue = totalGrossValue + this.dataService.checkNumber(data.grossValue);
                    totalGrossValueMap[orderKey] = totalGrossValue;
                    // set total scheme value
                    let totalSchemeValue = this.dataService.checkNumber(totalSchemeValueMap[orderKey]);
                    totalSchemeValue = totalSchemeValue + this.dataService.checkNumber(data.schAmt)
                        + this.dataService.checkNumber(data.orderLevelSchAmt);
                    totalSchemeValueMap[orderKey] = totalSchemeValue;
                    // set total tax value
                    let totalTaxValue = this.dataService.checkNumber(totalTaxValueMap[orderKey]);
                    totalTaxValue = totalTaxValue + this.dataService.checkNumber(data.taxAmt);
                    totalTaxValueMap[orderKey] = totalTaxValue;
                    // set order value
                    let totalOrderValue = this.dataService.checkNumber(totalOrderValueMap[orderKey]);
                    const orderValue = data.grossValue - (data.schAmt + data.orderLevelSchAmt) + data.taxAmt;
                    // set total order/net value
                    totalOrderValue = totalOrderValue + this.dataService.checkNumber(orderValue);
                    totalOrderValueMap[orderKey] = totalOrderValue;


                    // set order booking details
                    const orderBookingDetail = new OrderBookingDetail();
                    // set order booking header key information
                    orderBookingDetail.cmpCode = data.cmpCode;
                    // set distributor code
                    orderBookingDetail.distrCode = data.distrCode;
                    // set order number
                    orderBookingDetail.orderNo = orderNo
                    let orderBookingDetailsArr = orderBookingDetailsMap[orderBookingDetail.cmpCode + orderBookingDetail.distrCode + orderBookingDetail.orderNo];
                    if (orderBookingDetailsArr === null || orderBookingDetailsArr === undefined) {
                        orderBookingDetailsArr = [];
                    }
                    // set product and batch information
                    orderBookingDetail.prodCode = data.prodCode;
                    orderBookingDetail.prodName = data.prodName;
                    orderBookingDetail.prodBatchCode = data.prodBatchCode;
                    // set order qty & uom
                    orderBookingDetail.orderQty = data.orderQty;
                    orderBookingDetail.uomCode = data.baseUOM;
                    // set input str - concat of qty and uom
                    orderBookingDetail.inputStr = data.inputStr;
                    // set sell rate and actual sell rate
                    orderBookingDetail.sellRate = data.sellRate;
                    orderBookingDetail.actualSellRate = data.actualSellRate;
                    // set gross value
                    orderBookingDetail.grossValue = data.grossValue;
                    // set scheme amount
                    orderBookingDetail.schAmt = data.schAmt + data.orderLevelSchAmt;
                    // set tax information
                    orderBookingDetail.taxAmt = data.taxAmt;
                    orderBookingDetail.taxCode = data.taxCode;
                    // set cgst perc and amt
                    orderBookingDetail.cgstPerc = data.cgstPerc;
                    orderBookingDetail.cgstAmt = data.cgstAmt;
                    // set sgst perc and amt
                    orderBookingDetail.sgstPerc = data.sgstPerc;
                    orderBookingDetail.sgstAmt = data.sgstAmt;
                    // set ugst perc and amt
                    orderBookingDetail.ugstPerc = data.ugstPerc;
                    orderBookingDetail.ugstAmt = data.ugstAmt;
                    // set igst perc and amt
                    orderBookingDetail.igstPerc = data.igstPerc;
                    orderBookingDetail.igstAmt = data.igstAmt;

                    orderBookingDetail.orderValue = data.orderValue;
                    orderBookingDetail.prodType = 'N';
                    // set upload flag and mod dt
                    orderBookingDetail.uploadFlag = StringConstants.CONST_NO;
                    orderBookingDetail.modDt = new Date();
                    orderBookingDetailsArr.push(orderBookingDetail);
                    orderBookingDetailsMap[orderBookingDetail.cmpCode + orderBookingDetail.distrCode + orderBookingDetail.orderNo] = orderBookingDetailsArr;


                    // set free product scheme information
                    data.freeSchemeProduct.forEach((scheme: OrderBookingSchemeDetails) => {
                        const orderBookingSchemeDetails = new OrderBookingSchemeDetails();
                        // set order booking free product scheme key information
                        orderBookingSchemeDetails.cmpCode = scheme.cmpCode;
                        orderBookingSchemeDetails.distrCode = scheme.distrCode;
                        // set free scheme order number
                        orderBookingSchemeDetails.orderNo = orderNo
                        let orderBookingSchemeDetailsArr = orderBookingSchemeDetailsMap[orderBookingSchemeDetails.cmpCode + orderBookingSchemeDetails.distrCode + orderBookingSchemeDetails.orderNo];
                        if (orderBookingSchemeDetailsArr === null || orderBookingSchemeDetailsArr === undefined) {
                            orderBookingSchemeDetailsArr = [];
                        }

                        // set scheme and slab information
                        orderBookingSchemeDetails.schemeCode = scheme.schemeCode;
                        orderBookingSchemeDetails.slabNo = scheme.slabNo;
                        // set free product with qty details
                        orderBookingSchemeDetails.freeProdCode = scheme.freeProdCode;
                        orderBookingSchemeDetails.freeQty = scheme.freeQty;
                        // set upload flag and mod dt
                        orderBookingSchemeDetails.uploadFlag = StringConstants.CONST_NO;
                        orderBookingSchemeDetails.modDt = new Date();
                        orderBookingSchemeDetailsArr.push(orderBookingSchemeDetails);
                        orderBookingSchemeDetailsMap[orderBookingSchemeDetails.cmpCode + orderBookingSchemeDetails.distrCode + orderBookingSchemeDetails.orderNo] = orderBookingSchemeDetailsArr;
                    });

                    // set scheme amount details
                    data.schemeAmtDetail.forEach((schemeRule: OrderBookingSchemeProductRule) => {
                        const spr = new OrderBookingSchemeProductRule();
                        spr.cmpCode = schemeRule.cmpCode;
                        spr.distrCode = schemeRule.distrCode;
                        // set order number
                        spr.orderNo = orderNo
                        let orderBookingSchemeProductRuleArr = orderBookingSchemeProductRuleMap[spr.cmpCode + spr.distrCode + spr.orderNo];
                        if (orderBookingSchemeProductRuleArr === null || orderBookingSchemeProductRuleArr === undefined) {
                            orderBookingSchemeProductRuleArr = [];
                        }
                        // set scheme code
                        spr.schemeCode = schemeRule.schemeCode;
                        // set slab no
                        spr.slabNo = schemeRule.slabNo;
                        // set product with discount percentage and amt
                        spr.prodCode = schemeRule.prodCode;
                        spr.disPerc = schemeRule.disPerc;
                        spr.discAmt = schemeRule.discAmt;
                        spr.uploadFlag = StringConstants.CONST_NO;
                        spr.modDt = new Date();
                        orderBookingSchemeProductRuleArr.push(spr);
                        orderBookingSchemeProductRuleMap[spr.cmpCode + spr.distrCode + spr.orderNo] = orderBookingSchemeProductRuleArr;
                    });
                });

                // set customer reference number
                let customerRefNo = '';
                let orderNoList = [];
                // iterate cart list
                this.cartList.forEach((data: OrderBookingProduct) => {
                    const orderBookingHeader = new OrderBookingHeader();
                    // set customer reference number
                    customerRefNo = this.dataService.formatDateToRefNo(new Date()) + String(this.keyGenerator.suffixNN).padStart(5, '0');
                    // set order booking header and reference number for customer
                    orderBookingHeader.customerRefNo = customerRefNo;
                    orderBookingHeader.orderNo = data.customerCode + customerRefNo;
                    if (!orderNoList.includes(orderBookingHeader.orderNo)) {
                        orderBookingHeader.cmpCode = data.cmpCode;
                        orderBookingHeader.distrCode = data.distrCode;
                        const key = orderBookingHeader.cmpCode + orderBookingHeader.distrCode + orderBookingHeader.orderNo;
                        // set customer code and ship code
                        orderBookingHeader.customerCode = data.customerCode;
                        orderBookingHeader.customerShipCode = data.customerShipCode;
                        // set current data as order date
                        orderBookingHeader.orderDt = new Date();
                        // set order status as pending
                        orderBookingHeader.orderStatus = StringConstants.CONST_PENDING;
                        // set total gross, scheme, tax and net values
                        orderBookingHeader.totalGrossValue = totalGrossValueMap[key];
                        orderBookingHeader.totalDiscount = totalSchemeValueMap[key];
                        orderBookingHeader.totalTax = totalTaxValueMap[key];
                        orderBookingHeader.totalOrderValue = totalOrderValueMap[key];
                        // set remarks to identify the order
                        orderBookingHeader.remarks = StringConstants.MSG_ORDER_REMARKS;
                        // set start and end time of order booking
                        orderBookingHeader.startTime = this.startTime;
                        orderBookingHeader.endTime = this.endTime;
                        // set lat & long
                        orderBookingHeader.latitude = this.lat;
                        orderBookingHeader.longitude = this.lng;
                        orderBookingHeader.uploadFlag = StringConstants.CONST_NO;
                        orderBookingHeader.modDt = new Date();
                        orderNoList.push(orderBookingHeader.orderNo);

                        orderBookingHeader.orderBookingDetailsList = orderBookingDetailsMap[key];
                        orderBookingHeader.orderBookingSchemeDetailsList = orderBookingSchemeDetailsMap[key];
                        orderBookingHeader.orderBookingSchemeProductRuleList = orderBookingSchemeProductRuleMap[key];
                        orderBookingHeaderList.push(orderBookingHeader);
                    }
                });

                // set favorite products
                let favProdCode = '';
                const favoriteProductList = [];
                for (const [key, value] of Object.entries(this.favoriteProducts)) {
                    Object.entries(value).forEach(val => {
                        if (favProdCode.length > 0) {
                            favProdCode = favProdCode + StringConstants.CONST_CHAR_COMMA;
                        }
                        favProdCode = favProdCode + val[1];
                    });

                    // set favorite object
                    const favKeyArr = key.split(StringConstants.CONST_CHAR_TILDE);
                    const fav = new FavoriteProduct();
                    fav.cmpCode = favKeyArr[0];
                    fav.distrCode = favKeyArr[1];
                    fav.customerCode = favKeyArr[2];
                    fav.favProdCode = favProdCode;
                    fav.uploadFlag = StringConstants.CONST_NO;
                    fav.modDt = new Date();
                    favoriteProductList.push(fav);
                }

                // set key generator
                this.keyGenerator.suffixNN = Number(this.keyGenerator.suffixNN) + 1;
                this.keyGenerator.uploadFlag = StringConstants.CONST_NO;
                this.keyGenerator.modDt = new Date();

                keyGeneratorList.push(this.keyGenerator);
                const uploadModel = new UploadModel();
                uploadModel.enableCompress = false;
                uploadModel.orderBookingHeaderList = orderBookingHeaderList;
                uploadModel.keyGeneratorList = keyGeneratorList;
                uploadModel.favoriteProductList = favoriteProductList;
                this.dataService.setUploadData(uploadModel);
                this.dataService.setOrderKey(this.keyGenerator);
                this.confirmDialog(customerRefNo);
            }
        });
    }

}
