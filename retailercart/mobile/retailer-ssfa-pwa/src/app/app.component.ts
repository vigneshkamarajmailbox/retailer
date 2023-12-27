import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DataService} from './components/service/dataservice';
import {SwUpdate} from '@angular/service-worker';
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from '@angular/material/snack-bar';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {StringConstants} from './components/service/stringconstants';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  version = '';
  updateAvailable = false;
  currency = 'INR';
  language = 'EN';
  progress = false;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  alignRight = {
    'text-align': 'right'
  };

  alignLeft = {
    'text-align': 'left'
  };

  alignCenter = {
    'text-align': 'center'
  };
  playBanner = false;

  constructor(private dataService: DataService, private updates: SwUpdate,
              private cd: ChangeDetectorRef, private _snackBar: MatSnackBar,
              private http: HttpClient, private router: Router,
              public translate: TranslateService) {
    this.setLanguage(this.language);
    window.onpopstate = () => {
      if (this.playBanner) {
        history.replaceState(null, null, '#/landing/home');
        history.go(1);
        this.playBanner = false;
      } else {
        history.replaceState(null, null, window.location.href);
        if (window.location.hash === '#/') {
          const brandFilter = localStorage.getItem(StringConstants.APPLICATION_BRAND_FILTER);
          const version = localStorage.getItem(StringConstants.APPLICATION_VERSION);
          const externalAPIUrl = localStorage.getItem(StringConstants.EXTERNAL_API_URL);
          localStorage.clear();
          localStorage.setItem(StringConstants.EXTERNAL_API_URL, externalAPIUrl);
          localStorage.setItem(StringConstants.APPLICATION_VERSION, version);
          localStorage.setItem(StringConstants.APPLICATION_BRAND_FILTER, brandFilter);
        }
      }
    };

    this.checkUpdate();

    let schedulerTime = 5000;
    let timerId = setTimeout(function request() {
      if (localStorage.getItem(StringConstants.CONST_UPLOAD_ORDER_INFO) !== null
        && localStorage.getItem(StringConstants.CONST_UPLOAD_ORDER_INFO) !== undefined) {
        const startTime = new Date();
        const arr = JSON.parse(localStorage.getItem(StringConstants.CONST_UPLOAD_ORDER_INFO));
        arr.forEach(data => {
          http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_UPLOAD,
            data, dataService.getHttpOptions()).subscribe(
            (res: any) => {
              localStorage.removeItem(StringConstants.CONST_UPLOAD_ORDER_INFO);
              schedulerTime *= 1;
              timerId = setTimeout(request, schedulerTime);
              dataService.uploadSyncLog(StringConstants.CONST_PROCESS_UPLOAD, startTime, StringConstants.CONST_SUCCESS);
            }, (err: HttpErrorResponse) => {
              console.error(err.message);
              schedulerTime *= 1;
              timerId = setTimeout(request, schedulerTime);
            }
          );
        });
      } else {
        schedulerTime *= 1;
        timerId = setTimeout(request, schedulerTime);
      }
    }, schedulerTime);
  }

  ngOnInit() {
    this.dataService.setConfiguration();
    this.http.get(StringConstants.ASSETS_CONFIGURATION).subscribe(data => {
      this.version = data[StringConstants.APPLICATION_VERSION];
    });
  }

  checkUpdate() {
    this.updates.available.subscribe((event) => {
      this.router.navigate(['/']);
      this.updateAvailable = true;
    });
  }

  show() {
    this.cd.detectChanges();
    setTimeout(() => {
      this.progress = true;
    }, 50);
  }

  hide() {
    this.cd.detectChanges();
    setTimeout(() => {
      this.progress = false;
    }, 50);
  }

  openSnackBar(message: string) {
    this._snackBar.open(this.translate.instant(message), '', {
      duration: 2000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition
    });
  }

  setLanguage(lang: string) {
    this.language = lang;
    this.translate.use(this.language.toLowerCase());
  }
}
