import { Controller, Get, Param } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { ThemeService } from './theme.service';

@Controller('theme')
export class ThemeController {
  constructor(private readonly themeService: ThemeService) {}

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.themeService.findAll();
  }

  @ApiExcludeEndpoint()
  @Get('by-screen/:moduleNo/:screenNo')
  async findAllAccess(
    @Param('moduleNo') moduleNo: number,
    @Param('screenNo') screenNo: number,
  ) {
    return await this.themeService.findThemeByScreen(+moduleNo, +screenNo);
  }
}
