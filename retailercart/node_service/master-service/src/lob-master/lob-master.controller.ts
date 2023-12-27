import { Body, Controller, Get, Post } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { LobMasterService } from './lob-master.service';

@Controller('lob-master')
export class LobMasterController {
  constructor(private readonly lobMasterService: LobMasterService) {}

  @ApiExcludeEndpoint()
  @Post('by-lob-code')
  async fetchAllByCode(@Body() data) {
    return await this.lobMasterService.fetchAllByCode(data);
  }

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.lobMasterService.findAll();
  }
}
