import {Component, OnInit} from '@angular/core';
import {StringConstants} from '../service/stringconstants';
import {User} from '../model/user';
import {DataService} from '../service/dataservice';
import {Router} from '@angular/router';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: User;
  welcomeMsg;
  welcomeSymbol;
  sliderImages = [];
  images = [];
  notificationList = [];
  showSlide = false;
  salesInfoCards:any = [];                      

  constructor(private dataService: DataService, private router: Router,
              private http: HttpClient, private app: AppComponent) {
    app.checkUpdate();
  }

  ngOnInit(): void {

    this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
    this.welcomeMsg = this.dataService.welcomeNote();
    this.welcomeSymbol = this.dataService.welcomeGreeting();
    // call notification and banner service
    this.notification();

    this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_DASHBOARD_CARD_INFO + this.app.language + 
    StringConstants.CONST_CHAR_FORWARD_SLASH + localStorage.getItem(StringConstants.CUSTOMER_CODE), this.dataService.getHttpOptions()).subscribe(resp => {
        if (resp) 
        {
            this.salesInfoCards = resp;
            this.salesInfoCards.forEach((data,i) => {
              data.bordercolor='8px solid '+ data.displayColor;
            });
        }
    }, (err: HttpErrorResponse) => {
      this.app.openSnackBar(StringConstants.MSG_DASHBOARD_CARD_DATA_FAILED);
    }); 
      
    
  }

  hexToRgb(hex) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
      r: parseInt(result[1], 16),
      g: parseInt(result[2], 16),
      b: parseInt(result[3], 16)
    } : null;
  }
  

  // call notification and banner service
  notification() {
    const startTime = new Date();
    this.app.show();
    this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_NOTIFICATION
      + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH + this.user.loginCode,
      this.dataService.getHttpOptions()).subscribe(
      (res: any) => {
        this.convertBanner(res.bannerImageTemplate);
        this.convertNotification(res);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_NOTIFICATION, startTime, StringConstants.CONST_SUCCESS);
      }, (err: HttpErrorResponse) => {
        console.error(err.message);
        this.dataService.errorPage(err);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_NOTIFICATION, startTime, StringConstants.CONST_FAILURE);
        this.app.hide();
      }
    );
  }

  // convert banner
  convertBanner(bannerImageTemplate: any) {
    if (bannerImageTemplate.length > 1) {
      this.convertSlider(bannerImageTemplate, this.sliderImages);
      this.showSlide = true;
    } else {
      this.convertSlider(bannerImageTemplate, this.images);
    }
  }

  convertSlider(bannerImageTemplate: any, arr: any) {
    bannerImageTemplate.forEach(template => {
      if (template.bannerType === StringConstants.CONST_BANNER_TYPE_VIDEO) {
        arr.push({
          image: '',
          thumbImage: '',
          video: template.fileName,
          bannerType: template.bannerType,
          fileName: template.fileName,
          title: template.bannerDesc
        });
      } else if (template.bannerType === StringConstants.CONST_BANNER_TYPE_S3VIDEO) {
        arr.push({
          image: '',
          thumbImage: '',
          video: localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_BANNER_IMAGE
            + template.bannerType + StringConstants.CONST_CHAR_FORWARD_SLASH
            + template.fileName.split(StringConstants.CONST_CHAR_DOT)[0]
            + StringConstants.CONST_CHAR_TILDE + template.fileName.split(StringConstants.CONST_CHAR_DOT)[1],
          bannerType: template.bannerType,
          fileName: template.fileName,
          title: template.bannerDesc
        });
      } else {
        arr.push({
          video: '',
          title: '',
          image: localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_BANNER_IMAGE
            + template.bannerType + StringConstants.CONST_CHAR_FORWARD_SLASH
            + template.fileName.split(StringConstants.CONST_CHAR_DOT)[0],
          thumbImage: localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_BANNER_IMAGE
            + template.bannerType + StringConstants.CONST_CHAR_FORWARD_SLASH
            + template.fileName.split(StringConstants.CONST_CHAR_DOT)[0],
          bannerType: template.bannerType,
          fileName: template.fileName.split(StringConstants.CONST_CHAR_DOT)[0]
        });
      }
    });
  }

  // convert notifciation
  convertNotification(res: any) {
    const notificationList = [];
    const colorMap = {};
    for (let i = 1; i <= res.notificationTypeMaster.length; i++) {
      colorMap[res.notificationTypeMaster[i]] = 'color' + [i];
    }

    res.notification.forEach(data => {
      let imagePath = '';
      if (data.fileName !== null && data.fileName !== undefined && data.fileName !== '') {
        const arr = data.fileName.split(StringConstants.CONST_CHAR_DOT);
        imagePath = localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_NOTIFICATION
          + arr[0] + StringConstants.CONST_CHAR_FORWARD_SLASH + arr[1];
      }
      notificationList.push({
        subject: data.subject,
        notificationType: data.notificationType,
        important: data.important,
        color: colorMap[data.notificationType],
        description: data.messageDetail,
        image: imagePath,
        time: this.dataService.formatDateToStandard(data.createdDt) + ' ' + data.createdTime
      });
    });
    this.notificationList = notificationList;
    this.app.hide();
  }

  navigateScreen(screen: string) {
    this.router.navigate([StringConstants.REDIRECT_LANDING + screen]);
  }

  getImageSize() {
    if (window.innerWidth > 640) {
      return {width: '100%', height: '300px'};
    } else {
      return {width: '100%', height: '200px'};
    }
  }

  bannerSliderView(event: any) {
    this.app.playBanner = true;
    const banner = this.sliderImages[event];
    if (banner !== null && banner !== undefined) {
      this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_VIEW_BANNER, new Date(), banner.fileName);
    }
  }

  bannerView(event: any) {
    this.app.playBanner = true;
    const banner = this.images[event];
    if (banner !== null && banner !== undefined) {
      this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_VIEW_BANNER, new Date(), banner.fileName);
    }
  }

  showDashboardBar(displayCode: any) {
    return !(displayCode != null && displayCode !== '');
  }
}
