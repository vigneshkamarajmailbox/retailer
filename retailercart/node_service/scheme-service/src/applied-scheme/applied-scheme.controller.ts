import { Body, Controller, Post } from '@nestjs/common';
import { AppliedSchemeService } from './applied-scheme.service';
import { CreateAppliedSchemeDto } from './dto/create-applied-scheme.dto';

@Controller('applied-scheme')
export class AppliedSchemeController {
  constructor(private readonly appliedSchemeService: AppliedSchemeService) {}

  @Post()
  async create(@Body() createAppliedSchemeDto: CreateAppliedSchemeDto) {
    return await this.appliedSchemeService.create(createAppliedSchemeDto);
  }
}
