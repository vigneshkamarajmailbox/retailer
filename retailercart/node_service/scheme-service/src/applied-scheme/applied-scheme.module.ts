import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SchemeDistributor } from 'src/scheme-distributor/entities/scheme-distributor.entity';
import { Scheme } from 'src/scheme/entities/scheme.entity';
import { SchemeService } from 'src/scheme/scheme.service';
import { AppliedSchemeController } from './applied-scheme.controller';
import { AppliedSchemeService } from './applied-scheme.service';
import { AppliedScheme } from './entities/applied-scheme.entity';

@Module({
  imports: [
    TypeOrmModule.forFeature([AppliedScheme, Scheme, SchemeDistributor]),
  ],
  controllers: [AppliedSchemeController],
  providers: [AppliedSchemeService, SchemeService],
})
export class AppliedSchemeModule {}
