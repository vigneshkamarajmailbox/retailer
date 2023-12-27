import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Theme } from './entities/theme.entity';

@Injectable()
export class ThemeService {
  constructor(
    @InjectRepository(Theme)
    private themeRepository: Repository<Theme>,
  ) {}

  async findAll() {
    const themeData = await this.themeRepository.find();
    if (themeData) {
      return {
        statusCode: HttpStatus.OK,
        theme: themeData,
      };
    } else {
      return {
        statusCode: HttpStatus.OK,
        theme: [],
        message: 'Data Not Found',
      };
    }
  }

  async findThemeByScreen(moduleNo, screenNo) {
    const themeData = await this.themeRepository.findOne({
      where: { moduleNo: moduleNo, screenNo: screenNo },
    });

    if (themeData) {
      return {
        statusCode: HttpStatus.OK,
        theme: themeData,
      };
    } else {
      return {
        statusCode: HttpStatus.OK,
        theme: {},
        message: 'Data Not Found',
      };
    }
  }
}
