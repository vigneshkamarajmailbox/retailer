import { Module } from '@nestjs/common';
import { MediaCategoryService } from './media-category.service';
import { MediaCategoryController } from './media-category.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { MediaCategory } from './entities/media-category.entity';
import { MediaSubCategory } from './entities/media-sub-category.entity';

@Module({
  imports: [TypeOrmModule.forFeature([MediaCategory, MediaSubCategory])],
  controllers: [MediaCategoryController],
  providers: [MediaCategoryService],
})
export class MediaCategoryModule {}
