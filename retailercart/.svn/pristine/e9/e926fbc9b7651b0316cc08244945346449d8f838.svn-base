import { Module } from '@nestjs/common';
import { SchemeService } from './scheme.service';
import { SchemeController } from './scheme.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Scheme } from './entities/scheme.entity';
import { SchemeDistributor } from 'src/scheme-distributor/entities/scheme-distributor.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Scheme, SchemeDistributor])],
  controllers: [SchemeController],
  providers: [SchemeService],
})
export class SchemeModule {}
