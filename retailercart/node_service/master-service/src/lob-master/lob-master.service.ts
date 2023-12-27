import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { LobMaster } from './entities/lob-master.entity';

@Injectable()
export class LobMasterService {
  constructor(
    @InjectRepository(LobMaster)
    private lobRepository: Repository<LobMaster>,
  ) {}

  async findAll() {
    const lobData = await this.lobRepository.find({
      select: { cmpCode: true, lobCode: true, lobName: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Lob Details',
      lop: lobData,
    };
  }

  async fetchAllByCode(data) {
    const lobData = await this.lobRepository.find({
      where: { lobCode: In([data.lobCode]) },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Lob Details',
      lob: lobData,
    };
  }
}
