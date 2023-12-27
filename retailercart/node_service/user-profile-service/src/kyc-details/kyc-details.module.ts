import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { KycDetail } from './entities/kyc-detail.entity';
import { KycDetailsService } from './kyc-details.service';
import { KycDetailsController } from './kyc-details.controller';
import { KycRepository } from './kyc-details.repository';
import { HttpModule } from '@nestjs/axios';
import { MasterCommunicatorService } from 'src/communicators/master/master.service';
import { Company } from 'src/shared/entities/company.entity';
import { validate } from 'src/common/config/config.service';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { AwsService } from 'src/common/helper/aws-service';
import { CacheService } from 'src/shared/cache.service';

@Module({
  controllers: [KycDetailsController],
  providers: [KycDetailsService, KycRepository, MasterCommunicatorService, ConfigService, AwsService, CacheService],
  imports: [
    TypeOrmModule.forFeature([KycDetail, Company]),
    HttpModule,
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.env',
      validate: validate,
    }),
  ],
  exports: [KycDetailsService, ConfigService],
})
export class KycDetailsModule {}
