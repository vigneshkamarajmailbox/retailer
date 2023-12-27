import { Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, lastValueFrom, map } from 'rxjs';

export class MasterServiceCommunicator {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async uomMaster(prod_uom_code) {
    const url = this.config.get('MASTER');

    const response = await lastValueFrom(
      this.httpService
        .post(
          url + '/uom-master/by-code',
          { code: prod_uom_code },
          {
            headers: { 'Content-Type': 'application/json' },
          },
        )
        .pipe(
          map(async (resp) => {
            if (resp) {
              return resp.data.uom;
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
