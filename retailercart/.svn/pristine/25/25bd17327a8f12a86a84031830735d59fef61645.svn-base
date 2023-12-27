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
                {
                  status: HttpStatus.NOT_FOUND,
                  message: 'Product Not Found',
                },
                HttpStatus.NOT_FOUND,
              );
            }
          }),
          catchError(async () => {
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Product service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }
}
