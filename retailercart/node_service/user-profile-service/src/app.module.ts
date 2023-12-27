import { Module } from '@nestjs/common';
import { SharedModule } from './shared/shared.module';
import { AuthModule } from './auth/auth.module';
import { ContactUsModule } from './contact-us/contact-us.module';
import { KycDetailsModule } from './kyc-details/kyc-details.module';
import { FeedbackModule } from './feedback/feedback.module';
import { DigitalContentModule } from './digital-content/digital-content.module';
import { KpiModule } from './kpi/kpi.module';
import { RetailerModule } from './retailer/retailer.module';
import { DistributorModule } from './distributor/distributor.module';
import { SalesHierarchyModule } from './sales-hierarchy/sales-hierarchy.module';
import { NotificationModule } from './notification/notification.module';

@Module({
  imports: [
    SharedModule,
    AuthModule,
    ContactUsModule,
    KycDetailsModule,
    FeedbackModule,
    DigitalContentModule,
    KpiModule,
    RetailerModule,
    DistributorModule,
    SalesHierarchyModule,
    NotificationModule,
  ],
})
export class AppModule {}
