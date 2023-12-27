import { Controller, Get, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { ConfigurationParams } from 'src/shared/enum/query-params.enum';
import { ConfigurationService } from './configuration.service';

@Controller('configuration')
export class ConfigurationController {
  constructor(private readonly configurationService: ConfigurationService) {}

  @ApiExcludeEndpoint()
  @Get('by-config-key')
  findOne(@Query() configParams: ConfigurationParams) {
    return this.configurationService.findOne(configParams);
  }
}
