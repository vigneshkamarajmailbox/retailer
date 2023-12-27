import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { validate } from 'src/common/config/config.service';
import { TypeOrmConfigService } from '../common/config/typeorm/typeorm.service';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.env',
      validate: validate,
    }),
    TypeOrmModule.forRootAsync({
      extraProviders: [ConfigService],
      useClass: TypeOrmConfigService,
    }),
    HttpModule,
  ],
  controllers: [],
  providers: [ConfigService],
  exports: [ConfigService],
})
export class SharedModule {}
