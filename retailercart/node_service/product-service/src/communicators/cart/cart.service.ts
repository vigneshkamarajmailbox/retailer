import { Inject, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, lastValueFrom, map } from 'rxjs';

@Injectable()
export class CartServiceCommunicator {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async wishListStatus(userName: string, prodCode: string) {
    const url = this.config.get('CART');

    const response = await lastValueFrom(
      this.httpService
        .post(
          url + '/wishlist/status',
          {
            userName: userName,
            prodCode: prodCode,
          },
          {
            headers: { 'Content-Type': 'application/json' },
          },
        )
        .pipe(
          map(async (resp) => {
            if (resp) {
              return resp.data.isWishlist;
            } else {
              return {
                message: 'NO DATA FOUND',
              };
            }
          }),
          catchError(async (error) => {
            console.log(error);
            return false;
          }),
        ),
    );
    return response;
  }

  async getWishlist(username: string, cmpCode: string) {
    const url =
      this.config.get('CART') +
      `/wishlist/status/list?username=${username}&cmpCode=${cmpCode}`;
    const response = await lastValueFrom(
      this.httpService
        .get(url, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            console.log(resp);
            if (resp) {
              console.log('resp.data', resp.data);
              return resp.data;
            } else {
              return {
                message: 'NO DATA FOUND',
              };
            }
          }),
          catchError(async (error) => {
            console.log(Object.keys(error));
            console.error(error);
            return 'failed';
          }),
        ),
    );
    return response;
  }
}
