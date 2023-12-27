import { BadRequestException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Kpi } from './entities/kpi.entity';
import { Company } from 'src/shared/entities/company.entity';

@Injectable()
export class KpiService {
  constructor(
    @InjectRepository(Kpi)
    private kpiRepository: Repository<Kpi>,
    @InjectRepository(Company)
    private companyRepo: Repository<Company>,
  ) {}

  async findAll(userName: string, cmpCode: string) {
    const findCompany = await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });

    if (findCompany === null) {
      throw new BadRequestException('Company Code Not Exist');
    }
    const data = await this.kpiRepository.find({
      where: { userName: userName, cmpCode: cmpCode },
      select: { kpiCode: true, cmpCode: true, name: true, target: true, achieved: true, achievedPercentage: true },
      cache: 60000,
    });
    return {
      statusCode: HttpStatus.OK,
      message: 'Kpi Data',
      kpi: data,
    };
  }
}
