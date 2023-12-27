import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class CartCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  //Get Cart Details
  async getDetailsFromCart(cartListId: string, token: string) {
    const url = this.config.get('CART_SERVICE_URL') + '/' + cartListId;

    const response = await lastValueFrom(
      this.httpService
        .get(url, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token,
          },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data) {
              return resp.data.cartList;
            } else {
              throw new HttpException(
                {
                  status: HttpStatus.FORBIDDEN,
                  message: 'Cart Details not found',
                },
                HttpStatus.FORBIDDEN,
              );
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Cart service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  //Get Cart header Details
  async getFromCartHeder(userName: string, token: string) {
    const url = this.config.get('CART_SERVICE_URL') + '/cart-header-info/';

    const response = await lastValueFrom(
      this.httpService
        .get(url + userName, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token,
          },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data) {
              return resp.data;
            } else {
              return null;
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Cart service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  //Change cart details status
  async changeCartDetailsStatus(cartListIds: any, token: string) {
    const url = this.config.get('CART_SERVICE_URL') + '/change-cartStatus';

    const response = await lastValueFrom(
      this.httpService
        .post(url, cartListIds, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token,
          },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data) {
              return { data: resp.data, isError: false };
            } else {
              return {
                isError: true,
                statusCode: HttpStatus.NO_CONTENT,
                message: 'Cart service not running.',
              };
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Cart service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  async changeCartHederStatus(cartId: any, token: string) {
    const url =
      this.config.get('CART_SERVICE_URL') +
      '/change-cartHeaderStatus/' +
      cartId;

    const response = await lastValueFrom(
      this.httpService
        .get(url, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token,
          },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data) {
              return { data: resp.data, isError: false };
            } else {
              return {
                isError: true,
                statusCode: HttpStatus.NO_CONTENT,
                message: 'Cart service not running.',
              };
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Cart service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }
}
