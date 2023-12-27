import { Controller, Get, UseGuards } from '@nestjs/common';
import { DigitalContentService } from './digital-content.service';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';

@Controller('digital')
export class DigitalContentController {
  constructor(private readonly digitalContentService: DigitalContentService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('content')
  findAll() {
    return this.digitalContentService.findAll();
  }
}
