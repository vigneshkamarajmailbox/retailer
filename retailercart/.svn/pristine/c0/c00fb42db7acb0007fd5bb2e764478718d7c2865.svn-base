import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { validate } from 'src/common/config/config.service';
import { TypeOrmConfigService } from '../common/config/typeorm/typeorm.service';

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
  ],
  controllers: [],
  providers: [ConfigService],
  exports: [
    ConfigService,
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.env',
      validate: validate,
    }),
  ],
})
export class SharedModule {}
