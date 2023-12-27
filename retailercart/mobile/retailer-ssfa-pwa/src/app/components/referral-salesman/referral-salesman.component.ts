import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { DataService } from '../service/dataservice';
// import { AppComponent } from 'src/app/app.component';
import { User } from '../model/user';
import { StringConstants } from '../service/stringconstants';
import {OrderbookingComponent} from '../orderbooking/orderbooking.component';
import { MatDialog } from '@angular/material/dialog';
import {MAT_DIALOG_DATA, MatDialogRef, } from '@angular/material/dialog';
import { Console } from 'console';
import { ConfirmationdialogComponent } from '../confirmationdialog/confirmationdialog.component';


@Component({
    selector: 'app-referral-salesman',
    templateUrl: './referral-salesman.component.html',
    styleUrls: ['./referral-salesman.component.css']
})
export class ReferralsalesmanComponent implements OnInit {

    referralCodeForm: FormGroup;
    loading = false;
    referralCodeErrMsg = '';
    loginData: User;
    salesmanOptions:any=[];
    

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,private fb: FormBuilder, private router: Router,
        private http: HttpClient, private dataService: DataService, public dialogRef: MatDialogRef<OrderbookingComponent>, public dialog: MatDialog,) {
        // app.checkUpdate();

        this.loginData = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
    }

    ngOnInit(): void {
        this.referralCodeForm = this.fb.group({
            referral_code: new FormControl('', Validators.required)
        });

        this.fetchSalesman();
    }

    fetchSalesman():void
    {
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_SALESMAN+ '?customerCode='+localStorage.getItem(StringConstants.CUSTOMER_CODE), this.dataService.getHttpOptions()).subscribe(resp => {
            if (resp) 
            {
                this.salesmanOptions = resp;
                this.loading = false;
            }
        }, (err: HttpErrorResponse) => {
            this.referralCodeErrMsg = StringConstants.MSG_FETCH_SALESMAN_FAILED;
            this.loading = false;
        });
    }

    // onSubmitCode() {
    //     this.loading = true;
    //     const referralCode = this.referralCodeForm.get('referral_code').value;
    //     this.referralCodeErrMsg = '';
    //     let param: any;

    //     this.salesmanOptions.forEach(data=> {
    //         if(data.salesmanCode === referralCode)
    //         {
    //             param = 
    //             {
    //                 "cmpCode": data.cmpCode,
    //                 "distrCode" : data.distrCode,
    //                 "salesmanCode" : data.salesmanCode,
    //                 "customerCode" : localStorage.getItem(StringConstants.LOGIN_CODE),
    //             }
    //         }
            
    //      });

    //     if(param)
    //     {
    //         this.http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_SAVE_SALESMAN, JSON.stringify(param),
    //         {
    //             headers: new HttpHeaders({
    //                 'Content-Type': StringConstants.CONTENT_TYPE_JSON
    //             }), observe: StringConstants.OBSERVER_RESPONSE
    //         }).subscribe(resp => {
    //             if (resp) {
    //                 // this.app.openSnackBar(StringConstants.MSG_CHANGE_PASSWORD_SUCCESS);
    //                 this.loading = false;
    //                 this.navigateToHome();
    //             }
    //         }, (err: HttpErrorResponse) => {
    //             this.referralCodeErrMsg = StringConstants.MSG_CHANGE_PASSWORD_FAILED;
    //             this.loading = false;
    //         });
    //     }
        
    // }
    onSubmitCode() {
        this.loading = true;
        const referralCode = this.referralCodeForm.get('referral_code').value;
        this.referralCodeErrMsg = '';
        let param: any;
      
        this.salesmanOptions.forEach(data=> {
            if(data.salesmanCode === referralCode)
            {
                param = 
                {
                    "cmpCode": data.cmpCode,
                    "distrCode" : data.distrCode,
                    "salesmanCode" : data.salesmanCode,
                    "customerCode" : localStorage.getItem(StringConstants.CUSTOMER_CODE),
                    "orderNo" : localStorage.getItem(StringConstants.CUSTOMER_CODE)+this.data.refNo,
                }
            }
            
         });
      
        if(param)
        {
            console.log(param);
            this.http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_SAVE_SALESMAN_FOR_ORDER, JSON.stringify(param),
            {
                headers: new HttpHeaders({
                    'Content-Type': StringConstants.CONTENT_TYPE_JSON,
                    'X-Auth-Token': localStorage.getItem(StringConstants.TOKEN),
                    'X-Login-Code': localStorage.getItem(StringConstants.LOGIN_CODE),
                    'Cache-Control': StringConstants.NO_CACHE,
                    Pragma: StringConstants.NO_CACHE,
                    Expires: StringConstants.EXPIRES
                  }), observe: StringConstants.OBSERVER_RESPONSE
            }).subscribe(resp => {
                if (resp) {
                    this.loading = false;
                    this.dialogRef.close(StringConstants.CONST_YES);
                }
            }, (err: HttpErrorResponse) => {
                this.referralCodeErrMsg = StringConstants.MSG_SALESMAN_REF_FAILED_TO_SAVE;
                this.loading = false;
            });
        }
        
      }

    navigateToHome() {
        this.router.navigate([StringConstants.REDIRECT_LANDING_HOME]);
    }

    referralCodeMsg() {
        return this.referralCodeErrMsg !== '';
    }
}
