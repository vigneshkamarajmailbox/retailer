import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';

import { CartDetailsService } from './cart-details.service';
import { CartDetailsController } from './cart-details.controller';
import { CartRepository } from './cart.repository';

import { ProductCommunicatorService } from 'src/shared/communicators/product.service';

import { CartDetails } from './entities/cart-detail.entity';
import { CartHeader } from './entities/cart-header.entity';
import { SchemeCommunicatorService } from 'src/shared/communicators/scheme.service';
import { RetailerCommunicatorService } from 'src/shared/communicators/retailer.service';

const jwtFactory = {
  useFactory: async (configService: ConfigService) => ({
    secret: configService.get('JWT_SECRET'),
    signOptions: {
      expiresIn: configService.get('JWT_EXP_H'),
    },
  }),
  inject: [ConfigService],
};

@Module({
  controllers: [CartDetailsController],
  providers: [
    CartDetailsService,
    CartRepository,
    ProductCommunicatorService,
    SchemeCommunicatorService,
    RetailerCommunicatorService,
  ],
  imports: [
    TypeOrmModule.forFeature([CartDetails, CartHeader]),
    HttpModule,
    JwtModule.registerAsync(jwtFactory),
  ],
  exports: [CartDetailsService, CartRepository],
})
export class CartDetailsModule {}
