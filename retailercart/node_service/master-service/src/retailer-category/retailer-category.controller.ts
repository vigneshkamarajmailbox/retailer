import { Controller, Get } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { RetailerCategoryService } from './retailer-category.service';

@Controller('retailer-category')
export class RetailerCategoryController {
  constructor(
    private readonly retailerCategoryService: RetailerCategoryService,
  ) {}

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.retailerCategoryService.findAll();
  }
}
