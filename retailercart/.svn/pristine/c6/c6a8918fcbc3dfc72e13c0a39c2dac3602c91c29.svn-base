import { Controller, Get, UseGuards } from '@nestjs/common';
import { DistributorService } from './distributor.service';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';

@Controller('distributor')
export class DistributorController {
  constructor(private readonly distributorService: DistributorService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('info')
  async getDistributorInfo() {
    return await this.distributorService.getDistributorInfo();
  }
}
