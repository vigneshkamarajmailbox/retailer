import { Logger, QueryRunner } from 'typeorm';

export class TypeOrmLoggerContainer implements Logger {
  logQuery(query: string, parameters?: any[], queryRunner?: QueryRunner): any {
    parameters;
    queryRunner;
    console.log(query);
  }

  log(
    level: 'log' | 'info' | 'warn',
    message: any,
    queryRunner?: QueryRunner,
  ): any {
    level;
    queryRunner;
    console.log(message);
  }

  logMigration(message: string, queryRunner?: QueryRunner): any {
    console.log(message, queryRunner);
  }

  logQueryError(
    error: string | Error,
    query: string,
    parameters?: any[],
    queryRunner?: QueryRunner,
  ): any {
    parameters;
    queryRunner;
    console.log(query, error);
  }

  logQuerySlow(
    time: number,
    query: string,
    parameters?: any[],
    queryRunner?: QueryRunner,
  ): any {
    time;
    parameters;
    queryRunner;
    console.log(query);
  }

  logSchemaBuild(message: string, queryRunner?: QueryRunner): any {
    console.log(message, queryRunner);
  }
}
