import { Controller, Get, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { LocationParams } from 'src/shared/enum/query-params.enum';
import { LocationService } from './location.service';

@Controller('location')
export class LocationController {
  constructor(private readonly locationService: LocationService) {}

  @ApiExcludeEndpoint()
  @Get('country')
  async findAllCountry() {
    return await this.locationService.findAllCountry();
  }

  @ApiExcludeEndpoint()
  @Get('state')
  async findAllState(@Query() location: LocationParams) {
    return await this.locationService.findAllState(location.countryCode);
  }

  @ApiExcludeEndpoint()
  @Get('city')
  async findAllCity(@Query() location: LocationParams) {
    return await this.locationService.findAllCity(location.stateCode);
  }
}
