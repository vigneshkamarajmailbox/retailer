import { ValidatorConstraint, ValidatorConstraintInterface, ValidationArguments } from 'class-validator';
import { Injectable } from '@nestjs/common';
import { Company } from 'src/shared/entities/company.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

@ValidatorConstraint({ name: 'IsCompanyExists', async: true })
@Injectable()
export class IsCompanyExistsValidator implements ValidatorConstraintInterface {
  @InjectRepository(Company)
  private companyRepo: Repository<Company>;

  async validate(cmpCode: string, args: ValidationArguments) {
    const comp = await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });
    if (comp) {
      return true;
    } else {
      return false;
    }
  }

  defaultMessage(args: ValidationArguments) {
    return 'CmpCode not Exist';
  }
}
