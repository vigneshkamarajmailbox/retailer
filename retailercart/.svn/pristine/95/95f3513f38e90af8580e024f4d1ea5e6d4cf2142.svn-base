import { Module } from '@nestjs/common';
import { WishlistService } from './wishlist.service';
import { WishlistController } from './wishlist.controller';
import { WishlistRepository } from './wishlist.repository';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Wishlist } from './entities/wishlist.entity';
import { HttpModule } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { JwtModule } from '@nestjs/jwt';
import { ProductCommunicatorService } from 'src/shared/communicators/product.service';

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
  controllers: [WishlistController],
  providers: [WishlistService, WishlistRepository, ProductCommunicatorService],
  imports: [
    TypeOrmModule.forFeature([Wishlist]),
    HttpModule,
    JwtModule.registerAsync(jwtFactory),
  ],
  exports: [WishlistService, WishlistRepository],
})
export class WishlistModule {}
