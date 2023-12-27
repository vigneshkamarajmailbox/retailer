import { Body, Controller, Get, Post, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { LanguageParams } from 'src/shared/enum/query-params.enum';
import { LanguageService } from './language.service';

@Controller('language')
export class LanguageController {
  constructor(private readonly languageService: LanguageService) {}

  @ApiExcludeEndpoint()
  @Post('by-code')
  async fetchLanguageByCode(@Body() data) {
    return await this.languageService.fetchLanguageByCode(data);
  }

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.languageService.findAll();
  }

  @ApiExcludeEndpoint()
  @Get('by-code')
  async findOne(@Query() languageParam: LanguageParams) {
    return await this.languageService.findOne(languageParam.code);
  }
}
