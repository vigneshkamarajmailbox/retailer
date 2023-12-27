import { Module } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { TypeOrmModule } from '@nestjs/typeorm';
import { BannerEntity } from 'src/home/entities/home-banner.entity';
import { HighlightsEntity } from 'src/home/entities/home-highlights.entity';
import { ProductBatch } from 'src/product/entities/product-batch.entity';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductHierCrossMapping } from 'src/product/entities/product-hier-cross-mapping.entity';
import { ProductHierarchyLevel } from 'src/product/entities/product-hierarchy-level.entity';
import { ProductHierarchyValue } from 'src/product/entities/product-hierarchy-value.entity';
import { ProductTags } from 'src/product/entities/product-tags.entity';
import { ProductEntity } from 'src/product/entities/product.entity';
import { HomeTController } from './home-t.controller';
import { HomeTRepository } from './home-t.repository';
import { HomeTService } from './home-t.service';
import { CartServiceCommunicator } from 'src/communicators/cart/cart.service';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [
    TypeOrmModule.forFeature([
      ProductGroupEntity,
      ProductBatch,
      ProductHierarchyLevel,
      ProductHierarchyValue,
      ProductHierCrossMapping,
      ProductEntity,
      HighlightsEntity,
      ProductTags,
      BannerEntity,
    ]),
    HttpModule,
  ],

  controllers: [HomeTController],
  providers: [
    HomeTService,
    HomeTRepository,
    JwtService,
    CartServiceCommunicator,
  ],
})
export class HomeTModule {}
