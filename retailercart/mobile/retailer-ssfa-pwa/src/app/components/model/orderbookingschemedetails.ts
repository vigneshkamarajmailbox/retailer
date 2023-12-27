import {Injectable} from '@angular/core';

@Injectable()
export class OrderBookingSchemeDetails {
  cmpCode: string;
  distrCode: string;
  orderNo: string;
  schemeCode: string;
  slabNo: number;
  freeProdCode: string;
  freeQty: number;
  uploadFlag: string;
  modDt: Date;
}
