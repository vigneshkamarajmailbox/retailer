import { Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, lastValueFrom, map } from 'rxjs';

export class NotificationServiceCommunicator {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async sentSMS(req: any) {
    const url = this.config.get('SMS_COMMUNICATION') + '/sms/otp';
    const response = await lastValueFrom(
      this.httpService
        .post(url, req, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          map(async (resp) => {
            if (resp?.data) {
              console.log('data', Object.keys(resp.data));
              return { msg: resp.data, isError: false };
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
  async sendEmail(email, subject, tempvalue, newPass) {
    const url = this.config.get('SEND_EMAIL');
    const response = await lastValueFrom(
      this.httpService
        .post(
          url,
          { to: email, subject: subject, templateValue: tempvalue, password: newPass },
          {
            headers: { 'Content-Type': 'application/json' },
          },
        )
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
            console.log(error);
            return false;
          }),
        ),
    );
    return response;
  }
}
