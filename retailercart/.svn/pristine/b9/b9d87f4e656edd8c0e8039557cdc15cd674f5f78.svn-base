import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Retailer } from './entities/retailer.entity';
import { RetailerController } from './retailer.controller';
import { RetailerRepository } from './retailer.repository';
import { RetailerService } from './retailer.service';
import { Company } from 'src/shared/entities/company.entity';

@Module({
  controllers: [RetailerController],
  providers: [RetailerService, RetailerRepository],
  imports: [TypeOrmModule.forFeature([Retailer, Company])],
  exports: [RetailerService, RetailerRepository],
})
export class RetailerModule {}
