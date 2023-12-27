import {
  Body,
  Controller,
  Get,
  Post,
  Query,
  UseGuards,
  Request,
} from '@nestjs/common';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import { ProductExplore } from './dto/product-explore-dto';
import { HomeTService } from './home-t.service';

@Controller('home')
export class HomeTController {
  constructor(private readonly homeTService: HomeTService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('home')
  async getHome(@Query('cmpCode') cmpCode: string, @Request() req: any) {
    return await this.homeTService.getHomeData(cmpCode, req.user.username);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('explore/more')
  async getExploreMore(
    @Body() productExplore: ProductExplore,
    @Request() req: any,
  ) {
    return await this.homeTService.getProductTag(
      productExplore,
      req.user.username,
    );
  }
}
