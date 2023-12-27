import { Controller, Get, Query } from '@nestjs/common';
import { ContactUsService } from './contact-us.service';
import { ApiBearerAuth } from '@nestjs/swagger';
import { QueryCompanyInput } from 'src/shared/query-imput';

@Controller('contact-us')
export class ContactUsController {
  constructor(private readonly contactUsService: ContactUsService) {}

  @ApiBearerAuth('access-token')
  @Get()
  findAll(@Query() data: QueryCompanyInput) {
    return this.contactUsService.findAll(data);
  }
}
