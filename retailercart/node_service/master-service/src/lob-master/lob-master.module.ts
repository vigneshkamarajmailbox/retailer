import { Module } from '@nestjs/common';
import { LobMasterService } from './lob-master.service';
import { LobMasterController } from './lob-master.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { LobMaster } from './entities/lob-master.entity';

@Module({
  imports: [TypeOrmModule.forFeature([LobMaster])],
  controllers: [LobMasterController],
  providers: [LobMasterService],
})
export class LobMasterModule {}
