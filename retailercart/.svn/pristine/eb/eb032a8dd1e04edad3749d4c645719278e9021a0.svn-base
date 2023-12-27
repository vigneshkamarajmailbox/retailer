import { HttpException, HttpStatus } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Retailer } from 'src/retailer/entities/retailer.entity';
import { Company } from 'src/shared/entities/company.entity';
import { RetailerQueryInput } from 'src/shared/query-imput';
import { SignupDto } from 'src/users/dto/signup.dto';
import { UserEntity } from 'src/users/entities/user.entity';
import { Repository } from 'typeorm';
import { UserCompanyMapEntity } from './entities/user-company-mapping.entity';

export class UserRepository {
  @InjectRepository(UserEntity)
  private userRepository: Repository<UserEntity>;
  @InjectRepository(UserCompanyMapEntity)
  private userCompanyMapRepository: Repository<UserCompanyMapEntity>;
  @InjectRepository(Company)
  private companyRepository: Repository<Company>;
  @InjectRepository(Retailer)
  private retailerRepository: Repository<Retailer>;

  async findByMobileNo(mobile: number, userName = ''): Promise<UserEntity> {
    return await this.userRepository.findOne({
      where: [{ mobileNo: mobile }, { userName }],
    });
  }

  async create(signupDto: SignupDto): Promise<any> {
    try {
      const data = this.userRepository.create(signupDto);
      await this.userRepository.save(data);
      const companyData = await this.companyRepository.findOne({ where: { cmpCode: undefined } });
      const userComMapData = {
        userName: signupDto.userName,
        cmpCode: companyData.cmpCode,
      };

      await this.userCompanyMapRepository.save(userComMapData);
      const retailerData: any = new RetailerQueryInput(signupDto);

      await this.retailerRepository.save(retailerData);
    } catch (error) {
      console.log(error);
      throw new HttpException('Internal Server Error', HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  async validateUser(username: string): Promise<UserEntity> {
    const user = await this.userRepository
      .createQueryBuilder('user')
      .leftJoinAndSelect('user.company', 't1')
      .leftJoinAndSelect('t1.company', 't2')
      .where('user.user_name = :username', { username })
      .getOne();
    return user;
  }

  async validateOTP(mobile: number): Promise<UserEntity> {
    const user = await this.userRepository
      .createQueryBuilder('user')
      .leftJoinAndSelect('user.company', 't1')
      .leftJoinAndSelect('t1.company', 't2')
      .leftJoinAndSelect('user.userOTP', 'userOTP')
      .where('user.mobile_no = :mobileNo', { mobileNo: mobile })
      .getOne();
    return user;
  }

  async findByEmail(email: string): Promise<UserEntity> {
    return await this.userRepository.findOneBy({ email });
  }
  async update(userName: string, password: string) {
    return await this.userRepository
      .createQueryBuilder()
      .update(UserEntity)
      .set({ password: password })
      .where('userName = :name', { name: userName })
      .execute();
  }
}
