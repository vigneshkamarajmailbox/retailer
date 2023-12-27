import { Module } from '@nestjs/common';
import { SchemeHistoryService } from './scheme-history.service';
import { SchemeHistoryController } from './scheme-history.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SchemeHistory } from './entities/scheme-history.entity';

@Module({
  imports: [TypeOrmModule.forFeature([SchemeHistory])],
  controllers: [SchemeHistoryController],
  providers: [SchemeHistoryService],
})
export class SchemeHistoryModule {}
