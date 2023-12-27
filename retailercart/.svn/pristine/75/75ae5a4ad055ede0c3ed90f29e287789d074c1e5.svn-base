import { Body, Controller, Get, Post } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { UomMasterService } from './uom-master.service';

@Controller('uom-master')
export class UomMasterController {
  constructor(private readonly uomMasterService: UomMasterService) {}

  @ApiExcludeEndpoint()
  @Post('by-code')
  async fetchUomByCode(@Body() data) {
    return await this.uomMasterService.fetchUomByCode(data);
  }

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.uomMasterService.findAll();
  }
}
