import {Injectable} from '@angular/core';

@Injectable()
export class FavoriteProduct {
  cmpCode: string;
  distrCode: string;
  customerCode: string;
  favProdCode: string;
  uploadFlag: string;
  modDt: Date;
}
