import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { validate } from 'src/common/config/config.service';
import { TypeOrmConfigService } from '../common/config/typeorm/typeorm.service';
import { JwtModule } from '@nestjs/jwt';
import * as redisStore from 'cache-manager-redis-store';
import { CacheModule } from '@nestjs/cache-manager';
import { CacheService } from './cache.service';

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
    JwtModule.register({
      global: true,
      secret: 'secret',
      signOptions: { expiresIn: '8h' },
    }),

    CacheModule.register({
      imports: [ConfigModule],
      useFactory: (configService: ConfigService) => ({
        ttl: configService.get('CACHE_TTL'),
        host: configService.get('REDIS_HOST'),
        port: Number(configService.get('REDIS_PORT')),
        store: redisStore,
        password: 'Botree@Redis#2019',
        // configService.get('REDIS_PASSWORD'),
      }),
      inject: [ConfigService],
      isGlobal: true,
    }),
  ],
  controllers: [],
  providers: [ConfigService, CacheService],
  exports: [ConfigService],
})
export class SharedModule {}
