import {Component, OnInit} from '@angular/core';
import {StringConstants} from '../service/stringconstants';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {DataService} from '../service/dataservice';
import {AppComponent} from '../../app.component';
import {User} from '../model/user';
import {Retailer} from '../model/retailer';
import { salesofficer } from '../model/salesofficer';

@Component({
    selector: 'app-myprofile',
    templateUrl: './myprofile.component.html',
    styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {

    user: User;
    cols: any;
    customers: Retailer[];
    salesOfficer: salesofficer[];
    salesOfficerInitialData: salesofficer[];
    salesmanCols: string[];
    showMorebtn:boolean = false;
    loading:boolean = false;

    constructor(private dataService: DataService, private app: AppComponent, private http: HttpClient) {
        app.checkUpdate();
    }

    ngOnInit(): void {
        this.salesmanCols = ['distrName', 'salesmanName', 'salesmanMobileNo', 'coverageDay'];
        this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
        this.setCols(window.innerWidth);
        this.loading = true;
        this.download();
    }

    onResize(event) {
        this.setCols(event.target.innerWidth);
    }

    setCols(width: any) {
        if (width <= 768) {
            this.cols = 1;
        } else {
            this.cols = 2;
        }
    }

    download() {
        const startTime = new Date();
        this.app.show();
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_MYPROFILE
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH
            + this.user.loginCode, this.dataService.getHttpOptions()).subscribe(
            (res: any) => {
                this.convertDistributor(res);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_MY_PROFILE, startTime, StringConstants.CONST_SUCCESS);
            }, (err: HttpErrorResponse) => {
                this.loading = false;
                console.error(err.message);
                this.dataService.errorPage(err);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_MY_PROFILE, startTime, StringConstants.CONST_FAILURE);
                this.app.hide();
            }
        );
    }

    convertDistributor(res: any) {
        const customerShipAddressMap = {};
        res.customerShipAddress.forEach(data => {
            customerShipAddressMap[data.customerCode] = data;
        });

        const salesmanMap = {};
        if (res.customerSalesmanMapping !== null && res.customerSalesmanMapping !== undefined) {
            res.customerSalesmanMapping.forEach(data => {
                let arr = salesmanMap[data.cmpCode];
                if (arr === null || arr === undefined) {
                    arr = [];
                }
                if (data.coverageDay !== null && data.coverageDay !== undefined) {
                    const cov = data.coverageDay.split(StringConstants.CONST_CHAR_COMMA);
                    cov.sort((a, b) =>
                        StringConstants.DAY_NAMES[a.toLowerCase()] - StringConstants.DAY_NAMES[b.toLowerCase()]);
                    data.coverageDay = cov.toString();
                }
                arr.push(data);
                salesmanMap[data.cmpCode] = arr;
            });
        }

        this.customers = res.customer;
        this.salesOfficer = res.salesOfficer;
        this.salesOfficerInitialData = res.salesOfficer.slice(0, 10);
        if(this.salesOfficer.length > 10){
            this.showMorebtn = true;
        }
        this.customers.forEach((data: Retailer) => {
            data.image = localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_COMPANY_IMAGE
                + data.cmpCode;
            if (data.customerType === StringConstants.CONST_CUSTOMER_TYPE) {
                data.customerType = StringConstants.CONST_TYPE_YES;
            } else {
                data.customerType = StringConstants.CONST_TYPE_NO;
            }
            if (data.gstTinNo === StringConstants.CONST_NULL) {
                data.gstTinNo = StringConstants.CONST_EMPTY;
            }
            if (customerShipAddressMap.hasOwnProperty(data.customerCode)) {
                const val = customerShipAddressMap[data.customerCode] as Retailer;
                data.customerShipAddr1 = val.customerShipAddr1;
                data.customerShipAddr2 = val.customerShipAddr2;
                data.city = val.city;
            }
            data.salesmanMapping = salesmanMap[data.cmpCode];
        });
        this.app.hide();
        this.loading = false;
    }

    showMore() {
        let newLength = this.salesOfficerInitialData.length + 10;
        if (newLength >= this.salesOfficer.length) {
            newLength = this.salesOfficer.length
            this.showMorebtn = false;
        }
         this.salesOfficerInitialData = this.salesOfficer.slice(0, newLength);
      }
}
