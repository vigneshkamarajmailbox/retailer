import { Controller, Get, Query, Request, UseGuards } from '@nestjs/common';
import { KpiService } from './kpi.service';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';
import { QueryCompanyInput } from 'src/shared/query-imput';

@Controller('kpi')
export class KpiController {
  constructor(private readonly kpiService: KpiService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get()
  findAll(@Request() req: any, @Query() data: QueryCompanyInput) {
    return this.kpiService.findAll(req?.user?.username, data.cmpCode);
  }
}
