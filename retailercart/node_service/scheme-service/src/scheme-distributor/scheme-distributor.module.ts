import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SchemeDistributor } from './entities/scheme-distributor.entity';

@Module({
  imports: [TypeOrmModule.forFeature([SchemeDistributor])],
})
export class SchemeDistributorModule {}
