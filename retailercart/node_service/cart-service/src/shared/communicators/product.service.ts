import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class ProductCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async getFromProduct(req: any, token: string) {
    const url = this.config.get('PRODUCT_SERVICE_URL') + '/product/cart';
    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: token,
          },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data.product) {
              return { data: resp.data.product, isError: false };
            } else {
              throw new HttpException(
                'Product Not Found',
                HttpStatus.NOT_ACCEPTABLE,
              );
            }
          }),
          catchError(async (error: any) => {
            console.log(error);
            console.log(Object.keys(error));
            if (error?.response?.data?.statusCode == 406) {
              throw new HttpException(
                'Product Not Found',
                HttpStatus.NOT_ACCEPTABLE,
              );
            } else {
              throw new HttpException(
                'Product service not running',
                HttpStatus.FORBIDDEN,
              );
            }
          }),
        ),
    );
    return response;
  }
}
