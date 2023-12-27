import { Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, lastValueFrom, map } from 'rxjs';

export class UserServiceCommunicator {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async userInfo(bearerToken: string, cmpCode: string) {
    const url = this.config.get('USERPROFILE');

    const response = await lastValueFrom(
      this.httpService
        .get(url + `/retailer/info?cmpCode=${cmpCode}`, {
          headers: { Authorization: bearerToken },
        })
        .pipe(
          map(async (resp) => {
            if (resp) {
              return resp.data;
            } else {
              return {
                message: 'NO DATA FOUND',
              };
            }
          }),
          catchError(async (error) => {
            return {
              error: error,
              isError: true,
            };
          }),
        ),
    );
    return response;
  }
}
