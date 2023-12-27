import { Module } from '@nestjs/common';
import { ReturnService } from './return.service';
import { ReturnController } from './return.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ReturnHeader } from './entities/return-header.entity';
import { ReturnDetail } from './entities/retrun-details.entity';
import { ReturnRepository } from './return.repository';
import { HttpModule } from '@nestjs/axios';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';

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
  imports: [
    TypeOrmModule.forFeature([ReturnHeader, ReturnDetail]),
    HttpModule,
    JwtModule.registerAsync(jwtFactory),
  ],
  controllers: [ReturnController],
  providers: [ReturnService, ReturnRepository],
})
export class ReturnModule {}
