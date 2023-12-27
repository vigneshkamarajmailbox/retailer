import { Body, Controller, Get, Post, Query } from '@nestjs/common';
import { ApiExcludeEndpoint } from '@nestjs/swagger';
import { MediaParams } from 'src/shared/enum/query-params.enum';
import { MediaCategoryService } from './media-category.service';

@Controller('media-category')
export class MediaCategoryController {
  constructor(private readonly mediaCategoryService: MediaCategoryService) {}

  @ApiExcludeEndpoint()
  @Post('by-Code')
  async fetchCategoryByCode(@Body() data) {
    return await this.mediaCategoryService.fetchCategoryByCode(data);
  }

  @ApiExcludeEndpoint()
  @Get()
  async findAll() {
    return await this.mediaCategoryService.findAll();
  }

  @ApiExcludeEndpoint()
  @Get('by-code')
  async findOne(@Query() mediaParam: MediaParams) {
    return await this.mediaCategoryService.findOne(mediaParam.code);
  }

  @ApiExcludeEndpoint()
  @Post('by-subcategory-code')
  async fetchCSubategoryByCode(@Body() data) {
    return await this.mediaCategoryService.fetchCSubategoryByCode(data);
  }

  @ApiExcludeEndpoint()
  @Get('subcategory')
  async findASubCategoryll() {
    return await this.mediaCategoryService.findAllSubcategory();
  }

  @ApiExcludeEndpoint()
  @Get('by-subcategory-code')
  async findOneSubCategory(@Query() mediaSubCategory: MediaParams) {
    return await this.mediaCategoryService.findOneSubCatgeory(
      mediaSubCategory.code,
    );
  }
}
