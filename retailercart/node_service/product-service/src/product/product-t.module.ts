import { Module } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ProductBatch } from 'src/product/entities/product-batch.entity';
import { ProductGroupEntity } from 'src/product/entities/product-group-mapping.entity';
import { ProductHierCrossMapping } from 'src/product/entities/product-hier-cross-mapping.entity';
import { ProductHierarchyLevel } from 'src/product/entities/product-hierarchy-level.entity';
import { ProductHierarchyValue } from 'src/product/entities/product-hierarchy-value.entity';
import { ProductEntity } from 'src/product/entities/product.entity';
import { ProductTController } from 'src/product/product-t.controller';
import { ProductTService } from 'src/product/product-t.service';
import { ProductTags } from './entities/product-tags.entity';
import { ProductTRepository } from './product-t-repository';
import { SchemeServiceCommunicator } from 'src/communicators/scheme/scheme.service';
import { UserServiceCommunicator } from 'src/communicators/user-profile/user.service';
import { HttpModule } from '@nestjs/axios';
import { CartServiceCommunicator } from 'src/communicators/cart/cart.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([
      ProductGroupEntity,
      ProductBatch,
      ProductHierarchyLevel,
      ProductHierarchyValue,
      ProductHierCrossMapping,
      ProductEntity,
      ProductTags,
    ]),
    HttpModule,
  ],

  controllers: [ProductTController],
  providers: [
    ProductTService,
    ProductTRepository,
    JwtService,
    SchemeServiceCommunicator,
    UserServiceCommunicator,
    CartServiceCommunicator,
  ],
})
export class ProductModule {}
