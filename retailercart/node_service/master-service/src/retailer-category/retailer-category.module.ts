import { Module } from '@nestjs/common';
import { RetailerCategoryService } from './retailer-category.service';
import { RetailerCategoryController } from './retailer-category.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { RetailerCategory } from './entities/retailer-category.entity';

@Module({
  imports: [TypeOrmModule.forFeature([RetailerCategory])],
  controllers: [RetailerCategoryController],
  providers: [RetailerCategoryService]
})
export class RetailerCategoryModule {}
