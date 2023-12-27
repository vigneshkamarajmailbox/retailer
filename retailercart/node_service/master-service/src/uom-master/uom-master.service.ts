import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { UomMaster } from './entities/uom-master.entity';

@Injectable()
export class UomMasterService {
  constructor(
    @InjectRepository(UomMaster)
    private uomRepository: Repository<UomMaster>,
  ) {}

  async findAll(): Promise<any> {
    const uomData = await this.uomRepository.find({
      select: { code: true, name: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Uom list',
      uom: uomData,
      length: uomData.length,
    };
  }

  async fetchUomByCode(data) {
    const uomData = await this.uomRepository.find({
      where: { code: In([data.code]) },
    });
    return {
      statusCode: HttpStatus.OK,
      message: 'Uom list',
      uom: uomData,
      length: uomData.length,
    };
  }
}
