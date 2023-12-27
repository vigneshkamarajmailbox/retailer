import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SchemeAttribute } from './entities/scheme-attribute.entity';

@Module({
  imports: [TypeOrmModule.forFeature([SchemeAttribute])],
})
export class SchemeAttributesModule {}
