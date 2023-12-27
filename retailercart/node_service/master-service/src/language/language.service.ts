import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { Language } from './entities/language.entity';

@Injectable()
export class LanguageService {
  constructor(
    @InjectRepository(Language)
    private languageRepository: Repository<Language>,
  ) {}

  async findAll(): Promise<any> {
    const languageData = await this.languageRepository.find({
      select: { code: true, name: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Language Details',
      languageData: languageData,
    };
  }

  async fetchLanguageByCode(data) {
    const languageData = await this.languageRepository.find({
      where: { code: In([data.code]) },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Language Details',
      languageData: languageData,
    };
  }

  async findOne(code: string) {
    const languageData = await this.languageRepository.findOne({
      select: { code: true, name: true },
      where: { code: code },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Language Details',
      languageData: languageData,
    };
  }
}
