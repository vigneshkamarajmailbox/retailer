import {Component, OnInit} from '@angular/core';
import {StringConstants} from '../service/stringconstants';
import {User} from '../model/user';
import {DataService} from '../service/dataservice';
import {Router} from '@angular/router';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Screen} from '../model/screen';
import {AppComponent} from '../../app.component';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    user: User;
    welcomeMsg;
    welcomeSymbol;
    rightPanelVisible = false;
    rightPanelActive = false;
    screenList: Screen[];
    schemeCount;
    distributorDayOff = '';

    constructor(private dataService: DataService, private router: Router,
                private http: HttpClient, private app: AppComponent) {
    }

    ngOnInit(): void {
        localStorage.removeItem(StringConstants.TENTATIVE_DELIVERY);
        this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
        this.welcomeMsg = this.dataService.welcomeNote();
        this.welcomeSymbol = this.dataService.welcomeGreeting();
        this.fetchScreen();
    }

    showRightPanel() {
        this.rightPanelVisible = !this.rightPanelVisible;
        this.rightPanelActive = !this.rightPanelActive;
    }

    navigateScreen(screen: string) {
        const startTime = new Date();
        this.rightPanelVisible = false;
        const user = new User();
        user.loginCode = this.user.loginCode;
        if (screen === StringConstants.CONST_LINK_LOGOUT) {
            this.router.navigate(['/']);
            this.http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_LOG_OUT,
                JSON.stringify(user), this.dataService.getHttpOptions()).subscribe(
                (res: any) => {
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_LOGOUT, startTime, StringConstants.CONST_SUCCESS);
                    this.clearLocalStorage();
                    this.app.hide();
                }, (err: HttpErrorResponse) => {
                    console.error(err.message);
                    this.dataService.errorPage(err);
                    this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_LOGOUT, startTime, StringConstants.CONST_FAILURE);
                    this.clearLocalStorage();
                    this.app.hide();
                }
            );
        } else {
            this.router.navigate([StringConstants.REDIRECT_LANDING + screen]);
        }
    }

    clearLocalStorage() {
        const externalAPIUrl = localStorage.getItem(StringConstants.EXTERNAL_API_URL);
        const version = localStorage.getItem(StringConstants.APPLICATION_VERSION);
        const brandFilter = localStorage.getItem(StringConstants.APPLICATION_BRAND_FILTER);
        localStorage.clear();
        localStorage.setItem(StringConstants.EXTERNAL_API_URL, externalAPIUrl);
        localStorage.setItem(StringConstants.APPLICATION_VERSION, version);
        localStorage.setItem(StringConstants.APPLICATION_BRAND_FILTER, brandFilter);
    }

    fetchScreen() {
        const startTime = new Date();
        this.app.show();
        this.screenList = [];
        const user = new User();
        user.loginCode = this.user.loginCode;
        user.enableCompress = false;
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_SCREEN_ACCESS
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH
            + this.user.loginCode, this.dataService.getHttpOptions()).subscribe(
            (res: any) => {
                res.screenAccess.forEach(data => {
                    if (data.IsAccess === StringConstants.CONST_YES
                        && StringConstants.CONST_SCREEN_ACCESS.has(String(data.ModuleNo) + String(data.ScreenNo))) {
                        data.icon = StringConstants.CONST_SCREEN_ACCESS.get(String(data.ModuleNo) + String(data.ScreenNo)).icon;
                        data.link = StringConstants.CONST_SCREEN_ACCESS.get(String(data.ModuleNo) + String(data.ScreenNo)).link;
                        data.name = StringConstants.CONST_SCREEN_ACCESS.get(String(data.ModuleNo) + String(data.ScreenNo)).name;
                        this.screenList.push(data);
                    }
                });
                const screen = new Screen();
                screen.ScreenName = StringConstants.CONST_LOGOUT;
                screen.icon = StringConstants.CONST_ICON_LOGOUT;
                screen.link = StringConstants.CONST_LINK_LOGOUT;
                screen.name = StringConstants.CONST_SCREEN_LOGOUT;
                this.screenList.push(screen);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCREEN_ACCESS, startTime, StringConstants.CONST_SUCCESS);
                this.downloadSchemeInfo();
            }, (err: HttpErrorResponse) => {
                console.error(err.message);
                this.dataService.errorPage(err);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCREEN_ACCESS, startTime, StringConstants.CONST_FAILURE);
                this.app.hide();
            }
        );
    }

    downloadSchemeInfo() {
        const startTime1 = new Date();
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_SCHEME
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH + this.user.loginCode,
            this.dataService.getHttpOptions()).subscribe(
            (res: any) => {
                this.convertSchemeCount(res);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCHEME, startTime1, StringConstants.CONST_SUCCESS);
            }, (err: HttpErrorResponse) => {
                console.error(err.message);
                this.dataService.errorPage(err);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_SCHEME, startTime1, StringConstants.CONST_FAILURE);
                this.app.hide();
            }
        );
    }

    convertSchemeCount(resp: any) {
        const arr = [];
        if (resp.schemeCustomerMapping !== undefined && resp.schemeCustomerMapping !== null) {
            resp.schemeCustomerMapping.forEach(data1 => {
                if (!arr.includes(data1.cmpCode + data1.schemeCode)) {
                    arr.push(data1.cmpCode + data1.schemeCode);
                }
            });
        }
        this.schemeCount = arr.length;
        this.downloadDistributorDayOff();
    }

    downloadDistributorDayOff() {
        const startTime = new Date();
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_DISTRIBUTOR
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH
            + this.user.loginCode, this.dataService.getHttpOptions()).subscribe(
            (res: any) => {
                this.convertDistributorDayOff(res);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_DISTRIBUTOR, startTime, StringConstants.CONST_SUCCESS);
            }, (err: HttpErrorResponse) => {
                console.error(err.message);
                this.dataService.errorPage(err);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_DISTRIBUTOR, startTime, StringConstants.CONST_FAILURE);
                this.app.hide();
            }
        );
    }

    convertDistributorDayOff(res: any) {
        if (res !== null && res !== undefined && res.distributor !== null && res.distributor !== undefined
            && res.distributor.length === 1) {
            this.distributorDayOff = res.distributor[0].dayOff;
        }
        this.downloadProfile();
    }

    downloadProfile() {
        const startTime = new Date();
        this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_DOWNLOAD_MYPROFILE
            + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH
            + this.user.loginCode, this.dataService.getHttpOptions()).subscribe(
            (res: any) => {
                this.getCoverageDay(res);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_MY_PROFILE, startTime, StringConstants.CONST_SUCCESS);
            }, (err: HttpErrorResponse) => {
                console.error(err.message);
                this.dataService.errorPage(err);
                this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_MY_PROFILE, startTime, StringConstants.CONST_FAILURE);
                this.app.hide();
            }
        );
    }

    getCoverageDay(res: any) {
        if (res.customerSalesmanMapping === null || res.customerSalesmanMapping === undefined
            || res.customerSalesmanMapping.length === 0) {
            this.app.hide();
            return;
        }
        const data = res.customerSalesmanMapping[0];
        const coveragePlanArr = [];
        if (data.coverageDay !== null && data.coverageDay !== undefined) {
            const coverageArr = data.coverageDay.split(StringConstants.CONST_CHAR_COMMA);
            coverageArr.sort((a, b) =>
                StringConstants.DAY_NAMES[a.toLowerCase()] - StringConstants.DAY_NAMES[b.toLowerCase()]);
            coverageArr.forEach(day => {
                coveragePlanArr.push(StringConstants.DAY_NAMES[day.toLowerCase()])
            });
        }

        const distributorDayOff = [];
        const dayOff = this.distributorDayOff.split(StringConstants.CONST_CHAR_COMMA);
        dayOff.sort((a, b) =>
            StringConstants.DAY_NAMES[a.toLowerCase()] - StringConstants.DAY_NAMES[b.toLowerCase()]);
        dayOff.forEach(day => {
            distributorDayOff.push(StringConstants.DAY_NAMES[day.toLowerCase()])
        })

        const dayOfWeek = new Date().getDay();
        let covArr = [];
        for (let i = 1; i <= 7; i++) {
            if (dayOfWeek <= i && (!distributorDayOff.includes(i) ||
                (distributorDayOff.includes(i) && coveragePlanArr.includes(i)))) {
                covArr.push(i);
            }
        }
        for (let j = 1; j <= 7; j++) {
            if (!distributorDayOff.includes((j + 10) % 10) ||
                (distributorDayOff.includes((j + 10) % 10) && coveragePlanArr.includes((j + 10) % 10))) {
                covArr.push(j + 10);
            }
        }
        for (let k = 1; k <= 7; k++) {
            if (!distributorDayOff.includes((k + 20) % 10) ||
                (distributorDayOff.includes((k + 20) % 10) && coveragePlanArr.includes((k + 20) % 10))) {
                covArr.push(k + 20);
            }
        }
        let status = false;
        let item = 0;
        for (let k = 0; k < covArr.length; k++) {
            if (coveragePlanArr.includes(covArr[k] % 10)) {
                item = covArr[k + 1];
                status = true;
                break;
            }
        }
        if (status) {
            const today = new Date();
            if (item > 20) {
                item = item - 6;
            } else if (item > 10) {
                item = item - 3;
            }
            today.setDate(today.getDate() + (item - dayOfWeek));
            this.dataService.setTentativeDelivery(StringConstants.DAY_NAMES_INDEX[today.getDay()]
                + ', ' + this.dataService.formatDateToStandard(today));
        }
        this.app.hide();
    }
}
