import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Distributor } from './entities/distributor.entities';
import { Repository } from 'typeorm';

@Injectable()
export class DistributorService {
  @InjectRepository(Distributor)
  private distrRepo: Repository<Distributor>;

  async getDistributorInfo() {
    const distributorList = await this.distrRepo.find({
      select: {
        cmpCode: true,
        distrCode: true,
        distrName: true,
        addressLine1: true,
        addressLine2: true,
        mobileNo: true,
        email: true,
        lob: true,
      },
      cache: 60000,
    });
    return {
      statusCode: 200,
      message: 'Distributor List',
      distributorList,
    };
  }
}
