import { Injectable, Inject } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { TypeOrmOptionsFactory, TypeOrmModuleOptions } from '@nestjs/typeorm';
import { TypeOrmLoggerContainer } from 'src/common/helper/typeorm-logger';

@Injectable()
export class TypeOrmConfigService implements TypeOrmOptionsFactory {
  @Inject(ConfigService)
  private readonly config: ConfigService;

  public createTypeOrmOptions(): TypeOrmModuleOptions {
    return {
      type: this.config.get<string>('DATABASE_TYPE') as any,
      host: this.config.get<string>('DATABASE_HOST'),
      port: this.config.get<number>('DATABASE_PORT'),
      database: this.config.get<string>('DATABASE_NAME'),
      username: this.config.get<string>('DATABASE_USER'),
      password: this.config.get<string>('DATABASE_PASSWORD'),
      entities: [this.config.get<string>('DATABASE_ENTITIES')],
      migrations: [this.config.get<string>('DATABASE_MIGRATIONS')],
      migrationsTableName: this.config.get<string>('DATABASE_MIGRATION_TABLE'),
      logger: new TypeOrmLoggerContainer(),
      logging: 'all',
      synchronize: this.config.get<boolean>('DATABASE_SYNCHRONIZE'),
    };
  }
}
