import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Feedback } from './entities/feedback.entity';
import { FeedbackService } from './feedback.service';
import { FeedbackController } from './feedback.controller';
import { FeedbackRepository } from './feedback.repository';
import { Company } from 'src/shared/entities/company.entity';
import { CacheService } from 'src/shared/cache.service';
import { IsCompanyExistsValidator } from 'src/common/validation/companyValidation';
import { IsUserNameExistsValidator } from 'src/common/validation/userNameValidation';
import { UserEntity } from 'src/users/entities/user.entity';

@Module({
  controllers: [FeedbackController],
  providers: [FeedbackService, FeedbackRepository, CacheService, IsCompanyExistsValidator, IsUserNameExistsValidator],
  imports: [TypeOrmModule.forFeature([Feedback, Company, UserEntity])],
  exports: [FeedbackService, FeedbackRepository],
})
export class FeedbackModule {}
