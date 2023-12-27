import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { KycType } from './entities/kyc-type.entity';
import { KycTypeController } from './kyc-type.controller';
import { KycTypeService } from './kyc-type.service';

@Module({
  imports: [TypeOrmModule.forFeature([KycType])],
  controllers: [KycTypeController],
  providers: [KycTypeService],
})
export class KycTypeModule {}
