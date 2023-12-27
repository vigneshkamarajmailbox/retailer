import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import * as moment from 'moment';
import { getLoginExpireTime } from 'src/common/helper/utilities';
import { MasterCommunicatorService } from 'src/communicators/master/master.service';
import { SignupDto } from 'src/users/dto/signup.dto';
import { UserEntity } from 'src/users/entities/user.entity';
import { UsersService } from '../users/users.service';
import { UserCompanyMapEntity } from 'src/users/entities/user-company-mapping.entity';

@Injectable()
export class AuthService {
  constructor(
    private readonly usersService: UsersService,
    private readonly jwtService: JwtService,
    private readonly masterService: MasterCommunicatorService,
  ) {}

  async signup(signupDto: SignupDto) {
    await this.usersService.create(signupDto);
    return {
      statusCode: HttpStatus.OK,
      message: 'User Created Successfully',
    };
  }

  async signIn(username: string, pass: string) {
    const user = await this.usersService.validateUser(username);
    console.log(JSON.stringify(user));
    const psw = this.usersService.hashPassword(username, pass);
    if (!user) {
      throw new HttpException('User not found', HttpStatus.BAD_REQUEST);
    } else if (user.company && user.company.length == 0) {
      throw new HttpException('User Not Mapped with company', HttpStatus.BAD_REQUEST);
    } else if (user.password != psw) {
      throw new HttpException('Password Not matched', HttpStatus.BAD_REQUEST);
    }
    return this.formatLoginResponse(user);
  }

  async loginWithOTP(mobile: number, otp: number) {
    const user: UserEntity = await this.usersService.validateUserOtp(mobile);

    console.log('user ', user);
    if (!user) {
      throw new HttpException('User not found', HttpStatus.BAD_REQUEST);
    } else if (user.company && user.company.length == 0) {
      throw new HttpException('User Not Mapped with company', HttpStatus.BAD_REQUEST);
    } else if (user.userOTP?.otp != otp) {
      throw new HttpException('OTP Not matched', HttpStatus.BAD_REQUEST);
    } else if (moment().diff(user.userOTP?.expiredTime, 'seconds') < 0) {
      throw new HttpException('OTP Expired', HttpStatus.BAD_REQUEST);
    }
    return this.formatLoginResponse(user);
  }

  async sentOTP(mobileNo: number) {
    return await this.usersService.sentOTP(mobileNo);
  }

  async formatLoginResponse(user: UserEntity) {
    const companyList = user.company.map((cmp: UserCompanyMapEntity) => {
      return { cmpCode: cmp.company.cmpCode, cmpName: cmp.company.cmpName, image: cmp.company.cmpImageName };
    });
    const languageList: any = await this.masterService.getLanguage();
    console.log(languageList.languageData);

    return {
      accessToken: await this.jwtService.signAsync({
        username: user.userName,
        mobile: user.mobileNo,
        groupCode: user.groupCode,
      }),
      tokenType: 'Bearer',
      expiresIn: getLoginExpireTime(),
      username: user.userName,
      company: companyList,
      language: languageList.languageData,
    };
  }

  async forgotPassword(email: string) {
    return await this.usersService.forgotPassword(email);
  }

  async companySelection(company: string, groupCode: string) {
    return await this.masterService.getCompanySelection(company, groupCode);
  }
}
