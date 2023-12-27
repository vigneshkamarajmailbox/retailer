import {
  Body,
  Controller,
  HttpCode,
  HttpStatus,
  Inject,
  Post,
} from '@nestjs/common';
import { SMSCommunicatorService } from './sms.service';
import { OTPModel } from './dto/otp.dto';

@Controller('sms')
export class SMSController {
  @Inject()
  private sms: SMSCommunicatorService;

  @HttpCode(HttpStatus.OK)
  @Post('otp')
  otp(@Body() otp: OTPModel) {
    console.log('here ', otp);
    return this.sms.sentSMS(otp);
  }
}
