import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Configuration } from './entities/configuration.entity';

@Injectable()
export class ConfigurationService {
  constructor(
    @InjectRepository(Configuration)
    private configurationRepositor: Repository<Configuration>,
  ) {}

  async findOne(configParams) {
    const configValue = await this.configurationRepositor.findOne({
      where: {
        userGroupCode: configParams.groupCode,
        moduleNo: configParams.moduleNo,
        screenNo: configParams.screenNo,
        configKey: configParams.configKey,
      },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Configuration',
      configValue: configValue,
    };
  }
}
