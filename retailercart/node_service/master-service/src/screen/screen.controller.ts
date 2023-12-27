import { Controller, Get, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { ScreenParams } from 'src/shared/enum/query-params.enum';
import { ScreenService } from './screen.service';

@Controller('screen')
export class ScreenController {
  constructor(private readonly screenService: ScreenService) {}

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.screenService.findAll();
  }

  @ApiExcludeEndpoint()
  @Get('all-access')
  async findAllAccess(@Query() screenparam: ScreenParams) {
    return await this.screenService.findAllAccess(
      screenparam.moduleNo,
      screenparam.screenNo,
    );
  }

  @ApiExcludeEndpoint()
  @Get('company-selection')
  async findAllAccessCompanySelection(@Query() screenparam: ScreenParams) {
    return await this.screenService.companySelection(
      screenparam.cmpCode,
      screenparam.groupCode,
    );
  }
}
