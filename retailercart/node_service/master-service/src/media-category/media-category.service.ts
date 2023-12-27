import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { MediaCategory } from './entities/media-category.entity';
import { MediaSubCategory } from './entities/media-sub-category.entity';

@Injectable()
export class MediaCategoryService {
  constructor(
    @InjectRepository(MediaCategory)
    private mediaCategoryRepository: Repository<MediaCategory>,
    @InjectRepository(MediaSubCategory)
    private mediaSubCategoryRepository: Repository<MediaSubCategory>,
  ) {}

  async findAll(): Promise<any> {
    const mediaData = await this.mediaCategoryRepository.find({
      select: { code: true, name: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Media Category list',
      mediaCategory: mediaData,
      length: mediaData.length,
    };
  }

  async fetchCategoryByCode(data) {
    const mediaData = await this.mediaCategoryRepository.find({
      where: { code: In([data.code]) },
    });
    return {
      statusCode: HttpStatus.OK,
      message: 'Media Category list',
      mediaCategory: mediaData,
      length: mediaData.length,
    };
  }

  async findOne(code: string) {
    const mediaData = await this.mediaCategoryRepository.findOne({
      select: { code: true, name: true },
      where: { code: code },
    });
    return {
      statusCode: HttpStatus.OK,
      message: 'Media Category list',
      mediaCategory: mediaData,
    };
  }

  async findAllSubcategory() {
    const mediaData = await this.mediaSubCategoryRepository.find({
      select: { code: true, name: true, categoryCode: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Media Sub Category list',
      mediaSubCategory: mediaData,
      length: mediaData.length,
    };
  }

  async fetchCSubategoryByCode(data) {
    const mediaData = await this.mediaSubCategoryRepository.find({
      where: { code: In([data.code]) },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Media Sub Category list',
      mediaSubCategory: mediaData,
      length: mediaData.length,
    };
  }

  async findOneSubCatgeory(code: string) {
    const mediaData = await this.mediaSubCategoryRepository.findOne({
      select: { code: true, name: true, categoryCode: true },
      where: { code: code },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Media Sub Category Data',
      mediaSubCategory: mediaData,
    };
  }
}
