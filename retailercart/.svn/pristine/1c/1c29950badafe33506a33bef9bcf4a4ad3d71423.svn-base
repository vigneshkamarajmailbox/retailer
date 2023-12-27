import { Controller, Get, Query, Request, UseGuards } from '@nestjs/common';
import { ApiBearerAuth, ApiExcludeEndpoint } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';
import { QueryCompanyInput } from 'src/shared/query-imput';
import { RetailerService } from './retailer.service';

@Controller()
export class RetailerController {
  constructor(private readonly retailerService: RetailerService) {}

  @Get('profile')
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  async findOne(@Request() req: any, @Query() data: QueryCompanyInput) {
    console.log('UserName', req?.user?.username);
    return await this.retailerService.findOne(req?.user?.username, data.cmpCode);
  }

  @ApiExcludeEndpoint()
  @Get('retailer/info')
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  async retailerInfo(@Request() req: any, @Query('cmpCode') cmpCode: string) {
    return await this.retailerService.retailerInfo(req?.user?.username, cmpCode);
  }
}
