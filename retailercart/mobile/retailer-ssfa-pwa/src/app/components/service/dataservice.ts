import {Injectable} from '@angular/core';
import {User} from '../model/user';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {StringConstants} from './stringconstants';
import {UploadModel} from '../model/uploadmodel';
import {SyncLog} from '../model/synclog';

@Injectable()
export class DataService {

  schemeBaseMap = {};

  constructor(private http: HttpClient) {
    this.schemeBaseMap[StringConstants.CONST_SB_AB] = StringConstants.CONST_SB_AB_DESC;
    this.schemeBaseMap[StringConstants.CONST_SB_QB] = StringConstants.CONST_SB_QB_DESC;
    this.schemeBaseMap[StringConstants.CONST_SB_WB] = StringConstants.CONST_SB_WB_DESC;
  }

  setConfiguration() {
    this.http.get(StringConstants.ASSETS_CONFIGURATION).subscribe(data => {
      localStorage.setItem(StringConstants.EXTERNAL_API_URL, data[StringConstants.EXTERNAL_API_URL]);
      localStorage.setItem(StringConstants.APPLICATION_VERSION, data[StringConstants.APPLICATION_VERSION]);
      localStorage.setItem(StringConstants.APPLICATION_BRAND_FILTER, data[StringConstants.APPLICATION_BRAND_FILTER]);
    });
  }

  setUser(userIn: User) {
    localStorage.setItem(StringConstants.CONST_USER, JSON.stringify(userIn));
    localStorage.setItem(StringConstants.LOGIN_CODE, userIn.loginCode);
    localStorage.setItem(StringConstants.CUSTOMER_CODE, userIn.customerCode);
    localStorage.setItem(StringConstants.TOKEN, userIn.token);
    localStorage.setItem(StringConstants.CONST_LOGIN_STATUS, StringConstants.CONST_SUCCESS);
  }

  setUploadData(uploadIn: UploadModel) {
    let arr: UploadModel[] = JSON.parse(localStorage.getItem(StringConstants.CONST_UPLOAD_ORDER_INFO));
    if (arr === null || arr === undefined) {
      arr = [];
    }
    arr.push(uploadIn);
    localStorage.setItem(StringConstants.CONST_UPLOAD_ORDER_INFO, JSON.stringify(arr));
  }

  setOrderKey(keyIn: any) {
    localStorage.setItem(StringConstants.CONST_ORDER_KEY_INFO, JSON.stringify(keyIn));
  }

  welcomeNote() {
    const today = new Date();
    const hour = today.getHours();
    if (hour < 12) {
      return 'COMMON.GM';
    } else if (hour < 18) {
      return 'COMMON.GA';
    } else {
      return 'COMMON.GE';
    }
  }

  welcomeGreeting() {
    const today = new Date();
    const hour = today.getHours();
    if (hour < 12) {
      return 'brightness_2';
    } else if (hour < 18) {
      return 'wb_sunny';
    } else {
      return 'star_half';
    }
  }

  formatDateToString(val: any) {
    const d = new Date(val);
    const yyyy = d.getFullYear().toString();
    const mm = (d.getMonth() + 1).toString(); // getMonth() is zero-based
    const dd = d.getDate().toString();
    return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0]);
  }

  formatDateToStandard(val: any) {
    const d = new Date(val);
    const yyyy = d.getFullYear().toString();
    const mm = (d.getMonth() + 1).toString(); // getMonth() is zero-based
    const dd = d.getDate().toString();
    return (dd[1] ? dd : '0' + dd[0]) + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + yyyy;
  }

  formatDateToRefNo(val: any) {
    const date = new Date(val);
    const yyyy = date.getFullYear().toString();
    const mm = (date.getMonth() + 1).toString(); // getMonth() is zero-based
    const dd = date.getDate().toString();
    return yyyy + (mm[1] ? mm : '0' + mm[0]) + (dd[1] ? dd : '0' + dd[0]);
  }

  formatDateTime(val: any) {
    const date = new Date(val);
    const yyyy = date.getFullYear().toString();
    const mm = (date.getMonth() + 1).toString(); // getMonth() is zero-based
    const dd = date.getDate().toString();

    let hours = date.getHours();
    const minutes = date.getMinutes();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    const mins = minutes < 10 ? '0' + minutes : minutes;
    const time = hours + ':' + mins + ' ' + ampm;
    return (dd[1] ? dd : '0' + dd[0]) + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + yyyy + ' ' + time;
  }

  formatTime(val: any) {
    const date = new Date(val);
    const hrs = date.getHours().toString();
    const mins = date.getMinutes().toString();
    const secs = date.getSeconds().toString();
    return (hrs[1] ? hrs : '0' + hrs[0]) + ':' + (mins[1] ? mins : '0' + mins[0]) + ':' + (secs[1] ? secs : '0' + secs[0]);
  }

  getHttpOptions() {
    return {
      headers: new HttpHeaders({
        'Content-Type': StringConstants.CONTENT_TYPE_JSON,
        'X-Auth-Token': localStorage.getItem(StringConstants.TOKEN),
        'X-Login-Code': localStorage.getItem(StringConstants.LOGIN_CODE),
        'Cache-Control': StringConstants.NO_CACHE,
        Pragma: StringConstants.NO_CACHE,
        Expires: StringConstants.EXPIRES
      })
    };
  }

  checkNumber(val: any) {
    if (val === '' || val === null || val === undefined) {
      val = 0;
    }
    return val;
  }

  parseFloat(val: any) {
    if (val === '' || val === null || val === undefined) {
      val = 0;
    }
    const str = Number(val).toFixed(2);
    const pre = str.split('.')[0];
    let dec: string = str.split('.')[1];
    if (dec.length === 0) {
      dec = dec + '00';
    } else if (dec.length === 1) {
      dec = dec + '0';
    }
    return pre + '.' + dec;
  }

  errorPage(err: HttpErrorResponse) {
    if (err.status === 410) {
      window.location.href = '/assets/pages/sessionexpired.html';
    } else if (err.status === 401) {
      window.location.href = '/assets/pages/access.html';
    }
  }

  uploadSyncLog(processName: string, startTime: any, message: string) {
    const user: User = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
    const arr = [];
    const data = new SyncLog();
    data.loginCode = user.loginCode;
    data.appVersion = localStorage.getItem(StringConstants.APPLICATION_VERSION);
    data.syncDt = new Date();
    data.syncStartTime = this.formatTime(startTime);
    data.syncEndTime = this.formatTime(new Date());
    data.mode = StringConstants.CONST_ONLINE;
    data.processName = processName;
    data.userName = user.userName;
    data.uploadFlag = StringConstants.CONST_NO;
    data.modDt = new Date();
    data.message = message;
    arr.push(data);

    const uploadModel = new UploadModel();
    uploadModel.enableCompress = false;
    uploadModel.syncLogList = arr;
    this.http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_UPLOAD,
      uploadModel, this.getHttpOptions()).subscribe((err: HttpErrorResponse) => {
        this.errorPage(err);
      }
    );
  }

  checkDecimal(val: number) {
    return (val - Math.floor(val)) !== 0;
  }

  getLanguages() {
    return this.http.get('assets/config/language.json');
  }

  setTentativeDelivery(val: any){
    localStorage.setItem(StringConstants.TENTATIVE_DELIVERY, val);
  }

  getSchemeBase(schemeBase: string){
    if (this.schemeBaseMap.hasOwnProperty(schemeBase)) {
      return this.schemeBaseMap[schemeBase];
    }
    return '';
  }
}
