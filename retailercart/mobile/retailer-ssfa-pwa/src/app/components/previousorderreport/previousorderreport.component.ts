import {Component, OnInit, ViewChild} from '@angular/core';
import {StringConstants} from '../service/stringconstants';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AppComponent} from '../../app.component';
import {DataService} from '../service/dataservice';
import {User} from '../model/user';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-previousorderreport',
  templateUrl: './previousorderreport.component.html',
  styleUrls: ['./previousorderreport.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', visibility: 'hidden'})),
      state('expanded', style({height: '*', visibility: 'visible'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class PreviousorderreportComponent implements OnInit {

  user: User;
  dataSource = new MatTableDataSource();
  columnsToDisplay = [];
  detailColumns = [];
  expandedElement: null;
  totSchemeValue = 0;
  totTaxValue = 0;
  totOrderValue = 0;
  searchInput = '';
  selectedTab = 0;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(public app: AppComponent, private http: HttpClient, public dataService: DataService) {
    app.checkUpdate();
  }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
    this.columnsToDisplay = ['PREVIOUS_ORDER.HEADER.ORDER_STATUS', 'PREVIOUS_ORDER.HEADER.ORDER_DATE', 'PREVIOUS_ORDER.HEADER.ORDER_NO',
      'PREVIOUS_ORDER.HEADER.DISTR_NAME', 'PREVIOUS_ORDER.HEADER.GROSS_VALUE',
      'PREVIOUS_ORDER.HEADER.DISCOUNT', 'PREVIOUS_ORDER.HEADER.TAX', 'PREVIOUS_ORDER.HEADER.ORDER_VALUE'];
    this.detailColumns = ['PREVIOUS_ORDER.DETAIL.PRODUCT_NAME',
      'PREVIOUS_ORDER.DETAIL.QTY', 'PREVIOUS_ORDER.DETAIL.SELL_RATE', 'PREVIOUS_ORDER.DETAIL.GROSS_VALUE',
      'PREVIOUS_ORDER.DETAIL.SCHEME_DISC', 'PREVIOUS_ORDER.DETAIL.TAX', 'PREVIOUS_ORDER.DETAIL.NET_VALUE'];
    this.download();
  }

  download() {
    const startTime = new Date();
    this.app.show();
    this.http.get(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_FETCH_PREVIOUS_ORDER
      + StringConstants.CONST_CHAR_FORWARD_SLASH + this.app.language + StringConstants.CONST_CHAR_FORWARD_SLASH + this.user.loginCode,
      this.dataService.getHttpOptions()).subscribe(
      (res: any) => {
        this.convertOrderReport(res.previousOrderDetail);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_PREV_ORDER, startTime, StringConstants.CONST_SUCCESS);
      }, (err: HttpErrorResponse) => {
        console.error(err.message);
        this.dataService.errorPage(err);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_DOWNLOAD_PREV_ORDER, startTime, StringConstants.CONST_FAILURE);
        this.app.hide();
      }
    );
  }

  convertOrderReport(previousOrderDetail: any) {
    const arr = [];
    previousOrderDetail.forEach(data => {
      const detailArr = [];
      let totGrossAmt = 0;
      data.orderBookingDetailsList.forEach(data1 => {
        const grossAmt = this.dataService.parseFloat(data1.orderQty * data1.sellRate);
        totGrossAmt = totGrossAmt + Number(grossAmt);
        const detail = {
          'PREVIOUS_ORDER.DETAIL.PRODUCT_NAME': data1.prodName,
          'PREVIOUS_ORDER.DETAIL.QTY': data1.inputStr,
          'PREVIOUS_ORDER.DETAIL.SELL_RATE': this.dataService.parseFloat(data1.actualSellRate),
          'PREVIOUS_ORDER.DETAIL.GROSS_VALUE': grossAmt,
          'PREVIOUS_ORDER.DETAIL.SCHEME_DISC': this.dataService.parseFloat(data1.schAmt),
          'PREVIOUS_ORDER.DETAIL.TAX': this.dataService.parseFloat(data1.taxAmt),
          'PREVIOUS_ORDER.DETAIL.NET_VALUE': this.dataService.parseFloat(data1.orderValue)
        };
        detailArr.push(detail);
      });

      let orderStatus = 'Order Generated';
      const trackerArr = [];
      let pos = data.actionOrderStatusList.length;
      data.actionOrderStatusList.forEach(data2 => {
        if (pos < data.actionOrderStatusList.length) {
          const tracker = {
            status: data2.orderStatus,
            time: data2.actionTime,
            text: data2.freeText,
            state: pos
          };
          trackerArr.push(tracker);
        } else {
          const tracker = {
            status: data2.orderStatus,
            time: data2.actionTime,
            text: data2.freeText,
            state: 'done'
          };
          trackerArr.push(tracker);
        }
        pos = pos - 1;
      });
      if (trackerArr.length === 0) {
        const tracker = {
          text: 'No data available for tracker',
          time: new Date(),
          state: 'error'
        };
        trackerArr.push(tracker);
      } else {
          orderStatus = trackerArr[0].text;
      }
      const header = {
        'PREVIOUS_ORDER.HEADER.ORDER_STATUS': orderStatus,
        'PREVIOUS_ORDER.HEADER.ORDER_DATE': this.dataService.formatDateToStandard(data.orderDt),
        'PREVIOUS_ORDER.HEADER.ORDER_NO': data.customerRefNo,
        'PREVIOUS_ORDER.HEADER.DISTR_NAME': data.distrName,
        'PREVIOUS_ORDER.HEADER.GROSS_VALUE': this.dataService.parseFloat(totGrossAmt),
        'PREVIOUS_ORDER.HEADER.DISCOUNT': this.dataService.parseFloat(data.totalDiscount),
        'PREVIOUS_ORDER.HEADER.TAX': this.dataService.parseFloat(data.totalTax),
        'PREVIOUS_ORDER.HEADER.ORDER_VALUE': this.dataService.parseFloat(data.totalOrderValue),
        details: detailArr,
        tracker: trackerArr,
        trackerSelectedIndex: trackerArr.length - 1
      };
      arr.push(header);

      this.totSchemeValue = this.totSchemeValue + this.dataService.checkNumber(data.totalDiscount);
      this.totTaxValue = this.totTaxValue + this.dataService.checkNumber(data.totalTax);
      this.totOrderValue = this.totOrderValue + this.dataService.checkNumber(data.totalOrderValue);
    });
    this.dataSource = new MatTableDataSource(arr);
    this.dataSource.paginator = this.paginator;
    this.app.hide();
  }

  applyFilter() {
    if (this.searchInput.trim().length > 2) {
      this.app.show();
      this.dataSource.filter = this.searchInput.trim().toLowerCase();
      this.app.hide();
    } else if (this.searchInput.trim().length === 0) {
      this.clearFilter();
    }
  }

  clearFilter() {
    this.searchInput = '';
    this.dataSource.filter = this.searchInput.trim().toLowerCase();
  }

  textAlign(isHeader: boolean, column: any) {
    if (isHeader) {
      return this.app.alignCenter;
    }
    const statusLink = {
      'text-align': 'center',
      'background-color': '#FFF59D'
    };
    if (column === 'PREVIOUS_ORDER.HEADER.ORDER_STATUS') {
      return statusLink;
    } else if(column === 'PREVIOUS_ORDER.HEADER.DISTR_NAME' || column === 'PREVIOUS_ORDER.DETAIL.PRODUCT_NAME') {
      return this.app.alignLeft;
    }
  }

  showTab(column: any) {
    if (column === 'PREVIOUS_ORDER.HEADER.ORDER_STATUS') {
      this.selectedTab = 1;
    } else {
      this.selectedTab = 0;
    }
  }
}
