import { Module } from '@nestjs/common';
import { KpiService } from './kpi.service';
import { KpiController } from './kpi.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Kpi } from './entities/kpi.entity';
import { Company } from 'src/shared/entities/company.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Kpi, Company])],
  controllers: [KpiController],
  providers: [KpiService],
})
export class KpiModule {}
