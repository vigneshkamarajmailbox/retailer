import { Module } from '@nestjs/common';
import { OrderService } from './order.service';
import { OrderController } from './order.controller';
import { ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { HttpModule } from '@nestjs/axios';
import { JwtModule } from '@nestjs/jwt';
import { OrderHeader } from './entities/order-header.entity';
import { OrderRepository } from './order.repository';
import { Order } from './entities/order.entity';
import { ProductCommunicatorService } from 'src/shared/communicators/product.service';
import { SchemeCommunicatorService } from 'src/shared/communicators/scheme.service';
import { CartCommunicatorService } from 'src/shared/communicators/cart.service';
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
  controllers: [OrderController],
  providers: [
    OrderService,
    OrderRepository,
    ProductCommunicatorService,
    SchemeCommunicatorService,
    CartCommunicatorService,
    RetailerCommunicatorService,
  ],
  imports: [
    TypeOrmModule.forFeature([OrderHeader, Order]),
    HttpModule,
    JwtModule.registerAsync(jwtFactory),
  ],
  exports: [OrderService, OrderRepository],
})
export class OrderModule {}
