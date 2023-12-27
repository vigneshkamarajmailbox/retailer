import { Inject } from '@nestjs/common';
import { SMS } from './dto/sms.model.dto';
import { ConfigService } from '@nestjs/config';
import { HttpService } from '@nestjs/axios';
import { catchError, map } from 'rxjs';
import { OTPModel } from './dto/otp.dto';

export class SMSCommunicatorService {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  async sentSMS(otp: OTPModel) {
    let template = this.config.get<string>('SMS_OTP_TEMPLATE');
    template = template.replace('{0}', otp.code.toString());
    template = template.replace('{1}', otp.id);
    const smsModel: SMS = {
      from: this.config.get('SMS_FROM'),
      to: this.config.get('SMS_CODE') + otp.mobile,
      password: this.config.get('SMS_PASSWORD'),
      username: this.config.get('SMS_USERNAME'),
      text: template,
    };

    console.log(template);
    const response = this.httpService
      .get(this.config.get('SMS_URL'), { params: smsModel })
      .pipe(
        map(async (resp) => {
          if (resp?.data) {
            return { msg: resp?.data };
          } else {
            return { msg: 'no response' };
          }
        }),
        catchError(async (error) => {
          console.log(Object.keys(error));
          return 'failed';
        }),
      );
    return response;
  }
}
