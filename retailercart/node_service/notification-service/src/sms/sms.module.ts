import { Module } from '@nestjs/common';
import { SMSController } from './sms.controller';
import { SMSCommunicatorService } from './sms.service';
import { SharedModule } from 'src/shared/shared.module';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [SharedModule, HttpModule],
  controllers: [SMSController],
  providers: [SMSCommunicatorService],
})
export class SMSModule {}
