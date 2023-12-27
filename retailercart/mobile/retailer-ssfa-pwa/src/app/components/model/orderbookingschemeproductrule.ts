import {Injectable} from '@angular/core';

@Injectable()
export class OrderBookingSchemeProductRule {
  cmpCode: string;
  distrCode: string;
  orderNo: string;
  schemeCode: string;
  slabNo: number;
  prodCode: string;
  disPerc: number;
  discAmt: number;
  uploadFlag: string;
  modDt: Date;
  payout: number;
}
