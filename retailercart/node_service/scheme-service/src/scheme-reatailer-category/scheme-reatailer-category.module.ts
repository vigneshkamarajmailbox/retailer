import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SchemeReatailerCategory } from './entities/scheme-reatailer-category.entity';

@Module({
  imports: [TypeOrmModule.forFeature([SchemeReatailerCategory])],
})
export class SchemeReatailerCategoryModule {}
