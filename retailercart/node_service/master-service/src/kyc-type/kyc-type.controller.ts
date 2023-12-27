import { Body, Controller, Get, Post, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { KycParams } from 'src/shared/enum/query-params.enum';
import { CreateKycTypeDto } from './dto/create-kyc-type.dto';
import { KycTypeService } from './kyc-type.service';

@Controller('kyc-type')
export class KycTypeController {
  constructor(private readonly kycTypeService: KycTypeService) {}

  @ApiExcludeEndpoint()
  @Post('save')
  async create(@Body() createKycTypeDto: CreateKycTypeDto) {
    return await this.kycTypeService.create(createKycTypeDto);
  }
  @ApiExcludeEndpoint()
  @Post('by-code')
  async fetchKycTypeByCode(@Body() data) {
    return await this.kycTypeService.fetchKycTypeByCode(data);
  }

  @Get()
  async findAll() {
    return await this.kycTypeService.findAll();
  }

  @ApiExcludeEndpoint()
  @Get('by-code')
  async findOne(@Query() kycParam: KycParams) {
    return await this.kycTypeService.findOne(kycParam.code);
  }
}
