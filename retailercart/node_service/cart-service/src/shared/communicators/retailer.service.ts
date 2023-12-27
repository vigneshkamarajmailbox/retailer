import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class RetailerCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async getRetailerInfo(req: any, token: string) {
    const url =
      this.config.get('RETAILER_SERVICE_URL') + '/retailer/info?cmpCode=' + req;

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
            if (resp.data !== null) {
              return resp.data;
            } else {
              return [];
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Retailer service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }
}
