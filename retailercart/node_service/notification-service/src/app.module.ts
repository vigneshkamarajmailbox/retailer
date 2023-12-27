import { Module } from '@nestjs/common';
import { SharedModule } from './shared/shared.module';
import { SMSModule } from './sms/sms.module';
import { NotificationModule } from './notification/notification.module';
import { EmailModule } from './email/email.module';

@Module({
  imports: [SharedModule, SMSModule, NotificationModule, EmailModule],
})
export class AppModule {}
