import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Retailer } from './entities/retailer.entity';
import { Company } from 'src/shared/entities/company.entity';

export class RetailerRepository {
  @InjectRepository(Retailer)
  private retailerRepo: Repository<Retailer>;

  @InjectRepository(Company)
  private companyRepo: Repository<Company>;

  async findOne(userName: string, cmpCode: string) {
    return await this.retailerRepo.findOne({
      where: { userName: userName, cmpCode: cmpCode },
      cache: 60000,
    });
  }

  async retailerDetails(userName: string, cmpCode: string) {
    return await this.retailerRepo
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.retailerClass', 't2')
      .innerJoinAndSelect('t1.retailerDistributorMapping', 't3')
      .innerJoinAndSelect('t3.distributor', 't4')
      .innerJoinAndSelect('t4.distributorSalesHier', 't5')
      .innerJoinAndSelect('t1.retailerRouteMapping', 't6')
      .where('t1.cmp_code = :cmpCode AND t1.user_name = :userName', { cmpCode: cmpCode, userName: userName })
      .cache(60000)
      .getOne();
  }

  async findCompany(cmpCode: string) {
    return await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });
  }
}
