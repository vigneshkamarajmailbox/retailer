import { Module } from '@nestjs/common';
import { UsersService } from './users.service';
import { UserRepository } from './users.repository';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserEntity } from 'src/users/entities/user.entity';
import { UserOTPEntity } from 'src/users/entities/user-otp.entity';
import { UserCompanyMapEntity } from 'src/users/entities/user-company-mapping.entity';
import { Company } from 'src/shared/entities/company.entity';
import { HttpModule } from '@nestjs/axios';
import { RetailerRepository } from 'src/retailer/retailer.repository';
import { Retailer } from 'src/retailer/entities/retailer.entity';
import { NotificationServiceCommunicator } from 'src/communicators/notification/notification.service';

@Module({
  imports: [TypeOrmModule.forFeature([UserEntity, UserOTPEntity, UserCompanyMapEntity, Company, Retailer]), HttpModule],
  controllers: [],
  providers: [UsersService, UserRepository, NotificationServiceCommunicator, RetailerRepository],
  exports: [UsersService, UserRepository],
})
export class UsersModule {}
