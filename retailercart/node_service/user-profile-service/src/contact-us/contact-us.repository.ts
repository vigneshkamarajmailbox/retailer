import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ContactUs } from './entities/contact-us.entity';
import { Company } from 'src/shared/entities/company.entity';

export class ContactUsRepository {
  @InjectRepository(ContactUs)
  private contactRepository: Repository<ContactUs>;

  @InjectRepository(Company)
  private companyRepo: Repository<Company>;

  async findOne(data) {
    return await this.contactRepository.findOne({
      select: {
        cmpCode: true,
        email: true,
        mobileNo: true,
        tollFreeNo: true,
      },
      where: { cmpCode: data.cmpCode },
      cache: 60000,
    });
  }

  async findCompany(cmpCode: string) {
    return await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });
  }
}
