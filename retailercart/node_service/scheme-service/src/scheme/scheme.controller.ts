import { Body, Controller, Post } from '@nestjs/common';
import {
  FetchAllSchemePayload,
  FetchBySchemeCodePayload,
} from './dto/request-payload.dto';
import { SchemeService } from './scheme.service';

@Controller()
export class SchemeController {
  constructor(private readonly schemeService: SchemeService) {}

  @Post('fetch-all')
  async findAll(@Body() data: FetchAllSchemePayload) {
    return await this.schemeService.findAll(data);
  }

  @Post('fetch-all/by-code')
  async fetchSchemeBySchemeCode(@Body() data: FetchBySchemeCodePayload) {
    return await this.schemeService.findAllBySchemeCode(data);
  }
}
