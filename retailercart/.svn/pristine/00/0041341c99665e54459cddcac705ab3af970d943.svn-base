import { Module } from '@nestjs/common';
import { DistributorService } from './distributor.service';
import { DistributorController } from './distributor.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Distributor } from './entities/distributor.entities';

@Module({
  controllers: [DistributorController],
  imports: [TypeOrmModule.forFeature([Distributor])],
  providers: [DistributorService],
})
export class DistributorModule {}
