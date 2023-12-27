import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { catchError, lastValueFrom, map } from 'rxjs';

export class SchemeCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async getSchemesList(req: any) {
    const url = this.config.get('SCHEMES_SERVICE_URL') + '/fetch-all';

    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data.length) {
              return resp.data.scheme;
            } else {
              return [];
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Scheme service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  async getSchemesByCode(req: any) {
    const url = this.config.get('SCHEMES_SERVICE_URL') + '/fetch-all/by-code';

    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data.length) {
              return { data: resp.data.scheme[0], isError: false };
            } else {
              throw new HttpException(
                {
                  status: HttpStatus.NO_CONTENT,
                  message: 'No Schemes Available',
                },
                HttpStatus.NOT_FOUND,
              );
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Scheme service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  async updateSchemeHistory(req: any) {
    const url = this.config.get('SCHEMES_SERVICE_URL') + '/scheme-history';

    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp.data) {
              return { data: resp.data, isError: false };
            } else {
              throw new HttpException(
                {
                  status: HttpStatus.NO_CONTENT,
                  message: 'Scheme history not changed',
                },
                HttpStatus.NO_CONTENT,
              );
            }
          }),
          catchError(async (error) => {
            console.log(error);
            throw new HttpException(
              {
                status: HttpStatus.FORBIDDEN,
                message: 'Scheme service not running',
              },
              HttpStatus.FORBIDDEN,
            );
          }),
        ),
    );
    return response;
  }

  async schemesApiCal(req: any) {
    const url = this.config.get('SCHEMES_SERVICE_URL') + '/applied-scheme';

    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            console.log(resp.data);
            if (resp.data) {
              return { data: resp.data, isError: false };
            } else {
              return {
                isError: true,
                statusCode: HttpStatus.NO_CONTENT,
                message: 'Schemes Not applied.',
              };
            }
          }),
          catchError(async (error) => {
            console.log(error);
            return { data: error, isError: true };
          }),
        ),
    );
    return response;
  }
}
