import { Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, lastValueFrom, map } from 'rxjs';
import * as moment from 'moment';

export class SchemeServiceCommunicator {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async schemeList(cmpCode, userInfo, channelCode, groupCode, classCode) {
    const url = this.config.get('SCHEME');

    const currentDate = moment();

    const formattedDate = currentDate.format('YYYY-MM-DD');

    const response = await lastValueFrom(
      this.httpService
        .post(
          url + '/fetch-all',
          {
            currentDate: formattedDate,
            cmpCode: cmpCode,
            distCode: userInfo.distributorList,
            productHier: {
              levelValue: [],
            },
            retailerChannel: {
              channelCode: channelCode,
              groupCode: groupCode,
              classCode: classCode,
            },
            retailerCode: [userInfo.retailerCode],
            productCode: [],
            productQty: 0,
          },
          {
            headers: { 'Content-Type': 'application/json' },
          },
        )
        .pipe(
          map(async (resp) => {
            if (resp) {
              return resp.data.scheme;
            } else {
              return {
                message: 'NO DATA FOUND',
              };
            }
          }),
          catchError(async (error) => {
            return { data: error, isError: true };
          }),
        ),
    );
    return response;
  }
}
