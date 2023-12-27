import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ContactUs } from './entities/contact-us.entity';
import { ContactUsService } from './contact-us.service';
import { ContactUsController } from './contact-us.controller';
import { ContactUsRepository } from './contact-us.repository';
import { Company } from 'src/shared/entities/company.entity';
import { CacheService } from 'src/shared/cache.service';
@Module({
  controllers: [ContactUsController],
  providers: [ContactUsService, ContactUsRepository, CacheService],
  imports: [TypeOrmModule.forFeature([ContactUs, Company])],
  exports: [ContactUsService, ContactUsRepository],
})
export class ContactUsModule {}
