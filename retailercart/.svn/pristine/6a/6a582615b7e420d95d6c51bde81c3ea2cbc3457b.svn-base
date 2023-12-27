import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { CreateSchemeHistoryDto } from './dto/create-scheme-history.dto';
import { SchemeHistory } from './entities/scheme-history.entity';

@Injectable()
export class SchemeHistoryService {
  constructor(
    @InjectRepository(SchemeHistory)
    private historyRepository: Repository<SchemeHistory>,
  ) {}

  async create(createSchemeHistoryDto: CreateSchemeHistoryDto) {
    try {
      const data = this.historyRepository.create(createSchemeHistoryDto);
      await this.historyRepository.save(data);
      return {
        statusCode: HttpStatus.CREATED,
        message: 'Scheme History Created Successfully',
      };
    } catch (error) {
      console.log(error);
      throw new HttpException(
        'Internal Server Error',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
  }

  async findAll(data) {
    const schemeHistory = await this.historyRepository.find({
      where: { schemeCode: In([data.schemeCode]) },
    });
    return {
      statusCode: HttpStatus.OK,
      message: 'Scheme History list',
      schemeHistory: schemeHistory,
      length: schemeHistory.length,
    };
  }
}
