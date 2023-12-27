import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class RetailerCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async getRetailerInfo(cmpCode: any, token: string) {
    const url =
      this.config.get('RETAILER_SERVICE_URL') + '/profile?cmpCode=' + cmpCode;

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
            console.log(resp.data);
            if (resp.data.profile !== null) {
              return resp.data.profile;
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
