import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { In, Repository } from 'typeorm';
import { CreateKycTypeDto } from './dto/create-kyc-type.dto';
import { KycType } from './entities/kyc-type.entity';

@Injectable()
export class KycTypeService {
  constructor(
    @InjectRepository(KycType)
    private kycDocTypeRepository: Repository<KycType>,
  ) {}

  async create(createKycTypeDto: CreateKycTypeDto) {
    const find = await this.kycDocTypeRepository.save(createKycTypeDto);
    if (!find) {
      throw new HttpException('Details not saved', HttpStatus.BAD_REQUEST);
    } else {
      return {
        statusCode: HttpStatus.CREATED,
        message: 'KYC types Saved Successfully',
      };
    }
  }

  async findAll(): Promise<any> {
    const kycTypeData = await this.kycDocTypeRepository.find({
      select: { code: true, name: true },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Kyc Types',
      kycTypes: kycTypeData,
    };
  }

  async fetchKycTypeByCode(data) {
    const kycTypeData = await this.kycDocTypeRepository.find({
      where: { code: In([data.code]) },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Kyc Types',
      kycTypes: kycTypeData,
    };
  }

  async findOne(code: string) {
    const kycTypeData = await this.kycDocTypeRepository.findOne({
      select: { code: true, name: true },
      where: { code: code },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Kyc Types',
      kycTypes: kycTypeData,
    };
  }
}
