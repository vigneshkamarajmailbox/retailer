import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { RetailerCategory } from './entities/retailer-category.entity';

@Injectable()
export class RetailerCategoryService {
  constructor(
    @InjectRepository(RetailerCategory)
    private retailerRepository: Repository<RetailerCategory>,
  ) {}

  async findAll() {
    const retailerCat = await this.retailerRepository.find();

    return {
      statusCode: HttpStatus.OK,
      message: 'Retail Category list',
      retailerCategory: retailerCat,
      length: retailerCat.length,
    };
  }
}
