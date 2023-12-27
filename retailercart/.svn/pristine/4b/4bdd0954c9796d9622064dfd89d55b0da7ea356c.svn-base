import { Body, Controller, Post } from '@nestjs/common';
import { CreateSchemeHistoryDto } from './dto/create-scheme-history.dto';
import { SchemeHistoryService } from './scheme-history.service';

@Controller('scheme-history')
export class SchemeHistoryController {
  constructor(private readonly schemeHistoryService: SchemeHistoryService) {}

  @Post()
  async create(@Body() createSchemeHistoryDto: CreateSchemeHistoryDto) {
    return await this.schemeHistoryService.create(createSchemeHistoryDto);
  }

  @Post('by-scheme')
  async findAll(@Body() data) {
    return await this.schemeHistoryService.findAll(data);
  }
}
