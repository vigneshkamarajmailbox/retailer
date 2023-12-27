import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UomMaster } from './entities/uom-master.entity';
import { UomMasterController } from './uom-master.controller';
import { UomMasterService } from './uom-master.service';

@Module({
  imports: [TypeOrmModule.forFeature([UomMaster])],
  controllers: [UomMasterController],
  providers: [UomMasterService],
})
export class UomMasterModule {}
