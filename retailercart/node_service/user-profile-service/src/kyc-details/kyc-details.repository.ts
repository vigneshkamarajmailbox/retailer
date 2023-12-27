import { InjectRepository } from '@nestjs/typeorm';
import { Company } from 'src/shared/entities/company.entity';
import { Repository } from 'typeorm';
import { KycDetail } from './entities/kyc-detail.entity';

export class KycRepository {
  @InjectRepository(KycDetail)
  private kycDetailsRepository: Repository<KycDetail>;

  @InjectRepository(Company)
  private companyRepo: Repository<Company>;

  async createKycDetail(saveKycDetailDto): Promise<any> {
    console.log(saveKycDetailDto);
    const data = this.kycDetailsRepository.create(saveKycDetailDto);
    return await this.kycDetailsRepository.save(data);
  }

  async findAll() {
    return await this.kycDetailsRepository.find({
      select: {
        accountNo: true,
        accountName: true,
        bankName: true,
        bankBranch: true,
        ifsc: true,
        email: true,
        documentImage: true,
        documentType: true,
      },
    });
  }

  findOne(userName: string, cmpCode: string): Promise<KycDetail | null> {
    return this.kycDetailsRepository.findOne({
      where: { userName: userName, cmpCode: cmpCode },
      select: {
        userName: true,
        accountNo: true,
        accountName: true,
        bankName: true,
        bankBranch: true,
        ifsc: true,
        email: true,
        documentImage: true,
        documentType: true,
      },
      cache: 60000,
    });
  }

  async findCompany(cmpCode: string) {
    return await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });
  }
}
