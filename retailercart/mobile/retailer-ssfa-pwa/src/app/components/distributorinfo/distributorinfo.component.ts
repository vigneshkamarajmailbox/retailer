import {Component, OnInit} from '@angular/core';
import {StringConstants} from '../service/stringconstants';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {DataService} from '../service/dataservice';
import {AppComponent} from '../../app.component';
import {User} from '../model/user';
import {Distributor} from '../model/distributor';

@Component({
  selector: 'app-distributorinfo',
  templateUrl: './distributorinfo.component.html',
  styleUrls: ['./distributorinfo.component.css']
})
export class DistributorinfoComponent implements OnInit {

  user: User;
  cols: any;
  distributors: Distributor[];
  distributorLobCols: string[];
  salesmansData:any = [];

  constructor(private dataService: DataService, private app: AppComponent, private http: HttpClient) {
    app.checkUpdate();
  }

  ngOnInit(): void {
    this.distributorLobCols = ['lobCode', 'lobName'];
    this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
    this.setCols(window.innerWidth);
    this.download();
    this.fetchSalesman();
  }

  fetchSalesman():void
  {
      this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_SALESMAN + '?customerCode='+localStorage.getItem(StringConstants.CUSTOMER_CODE), this.dataService.getHttpOptions()).subscribe(resp => {
          if (resp) 
          {
              this.salesmansData = resp;
          }
      }, (err: HttpErrorResponse) => {
        this.app.openSnackBar(StringConstants.MSG_FETCH_SALESMAN_FAILED);
      });
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
    this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_DISTRIBUTOR
      + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH
      + this.user.loginCode, this.dataService.getHttpOptions()).subscribe(
      (res: any) => {
        this.convertDistributor(res);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_DISTRIBUTOR, startTime, StringConstants.CONST_SUCCESS);
      }, (err: HttpErrorResponse) => {
        console.error(err.message);
        this.dataService.errorPage(err);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_DISTRIBUTOR, startTime, StringConstants.CONST_FAILURE);
        this.app.hide();
      }
    );
  }

  convertDistributor(res: any) {
    // GST State Master map
    const gstStateMap = {};
    res.gstStateMaster.forEach(data => {
      gstStateMap[data.gstStateCode] = data.gstStateName;
    });

    // set lob map
    const lobMap = {};
    res.lobMaster.forEach(data => {
      lobMap[data.lobCode] = data.lobName;
    });

    // set distributor lob mapping
    const distributorLOBMap = {};
    res.distributorLOBMapping.forEach(data => {
      data.lobName = lobMap[data.lobCode];
      let arr = distributorLOBMap[data.cmpCode + data.distrCode];
      if (arr === null || arr === undefined) {
        arr = [];
      }
      arr.push(data);
      distributorLOBMap[data.cmpCode + data.distrCode] = arr;
    });
    this.distributors = res.distributor;
    this.distributors.forEach((data: Distributor) => {
      const dayOff = data.dayOff.split(StringConstants.CONST_CHAR_COMMA);
      dayOff.sort((a, b) =>
          StringConstants.DAY_NAMES[a.toLowerCase()] - StringConstants.DAY_NAMES[b.toLowerCase()]);
      data.dayOff = dayOff.toString();
      data.image = localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_COMPANY_IMAGE
        + data.cmpCode;
      data.gstStateName = data.gstStateCode + ' - ' + gstStateMap[data.gstStateCode];
      data.distributorLOBMapping = distributorLOBMap[data.cmpCode + data.distrCode];
    });
    this.app.hide();
  }
}
