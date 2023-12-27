import { HttpService } from '@nestjs/axios';
import { HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class MasterCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async getLanguage() {
    const url = this.config.get('MASTER_COMMUNICATION');
    const response = await lastValueFrom(
      this.httpService
        .get(url + '/language', {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp?.data) {
              console.log('data', Object.keys(resp.data));
              return { languageData: resp.data.languageData, isError: false };
            } else {
              console.log('no response');
              return { msg: 'no response', isError: false };
            }
          }),
          catchError(async (error) => {
            console.log('failed', Object.keys(error));
            return { msg: 'failed', error: error, isError: true };
          }),
        ),
    );
    return response;
  }

  async getCompanySelection(cmpCode: string, groupCode: string) {
    const url = this.config.get('MASTER_COMMUNICATION');
    const response = await lastValueFrom(
      this.httpService
        .get(url + '/screen/company-selection?cmpCode=' + cmpCode + '&groupCode=' + groupCode, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp?.data) {
              console.log('data', Object.keys(resp.data));
              return { theme: resp.data.theme, screenList: resp.data.screenList };
            } else {
              console.log('no response');
              return { msg: 'no response', isError: false };
            }
          }),
          catchError(async (error) => {
            console.log('failed', Object.keys(error));
            return { msg: 'failed', error: error, isError: true };
          }),
        ),
    );
    return response;
  }

  async getDocType(docType) {
    const url = this.config.get('KYC_TYPE') + '/by-code/';

    const response = await lastValueFrom(
      this.httpService.get(url, docType).pipe(
        map(async (resp) => {
          if (resp) {
            return { data: resp.data.kycTypes, isError: false };
          } else {
            return {
              isError: true,
              statusCode: HttpStatus.NO_CONTENT,
              message: 'Product service not running.',
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
