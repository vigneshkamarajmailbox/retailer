import { Module } from '@nestjs/common';
import { DigitalContentService } from './digital-content.service';
import { DigitalContentController } from './digital-content.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { DigitalContent } from './entities/digital-content.entity';
import { CacheService } from 'src/shared/cache.service';

@Module({
  imports: [TypeOrmModule.forFeature([DigitalContent])],
  controllers: [DigitalContentController],
  providers: [DigitalContentService, CacheService],
})
export class DigitalContentModule {}
