import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { UserRepository } from './users.repository';
import { SignupDto } from 'src/users/dto/signup.dto';
import * as crypto from 'crypto';
import { InjectRepository } from '@nestjs/typeorm';
import { UserOTPEntity } from './entities/user-otp.entity';
import { Repository } from 'typeorm';

import { UserEntity } from './entities/user.entity';
import { randomNumber, randomString } from 'src/common/helper/utilities';
import { NotificationServiceCommunicator } from 'src/communicators/notification/notification.service';
import { SMS } from 'src/communicators/notification/sms.model.dto';

@Injectable()
export class UsersService {
  @InjectRepository(UserOTPEntity)
  private otpRepo: Repository<UserOTPEntity>;

  @Inject()
  private readonly userRepository: UserRepository;

  @Inject()
  private notification: NotificationServiceCommunicator;

  async create(signupDto: SignupDto) {
    const find = await this.userRepository.findByMobileNo(signupDto.mobileNo, signupDto.userName);
    if (find) {
      throw new HttpException('User Already Exist/Registered', HttpStatus.BAD_REQUEST);
    }
    signupDto.password = this.hashPassword(signupDto.userName, signupDto.password);
    await this.userRepository.create(signupDto);
    return signupDto;
  }

  async validateUser(username: string): Promise<UserEntity> {
    return await this.userRepository.validateUser(username);
  }

  async validateUserOtp(mobile: number): Promise<UserEntity> {
    const user = await this.userRepository.validateOTP(mobile);
    return user;
  }

  hashPassword(username: string, password: string): string {
    return crypto
      .createHash('md5')
      .update(username.toLowerCase() + password)
      .digest('hex');
  }

  async sentOTP(mobileNo: number) {
    const user = await this.userRepository.findByMobileNo(mobileNo);
    if (user) {
      const otp = randomNumber().toString();
      const req: SMS = { mobile: mobileNo.toString(), code: otp, id: randomString(6) };
      await this.otpRepo
        .createQueryBuilder()
        .insert()
        .into(UserOTPEntity)
        .values({ mobileNo: mobileNo, otp: +otp })
        .orUpdate(['otp'], ['mobile_No'])
        .execute();
      console.log(otp);

      const response = await this.notification.sentSMS(req);
      if (response?.isError) {
        throw new HttpException('OTP sent failed', HttpStatus.BAD_REQUEST);
      }
      return { status: HttpStatus.OK, message: 'OTP sent Successfully' };
    } else {
      throw new HttpException('User not found', HttpStatus.BAD_REQUEST);
    }
  }

  async forgotPassword(email: string) {
    const find = await this.userRepository.findByEmail(email);
    if (!find) {
      throw new HttpException('User Not Found', HttpStatus.BAD_REQUEST);
    }
    console.log(find, find.email, find.password, find.userName);
    const newPass = Math.random().toString(36).substring(2, 10);
    find.password = this.hashPassword(find.userName, newPass);
    console.log(newPass, 'soosos');
    await this.userRepository.update(find.userName, find.password);
    const response = await this.notification.sendEmail(find.email, 'Reset Password', 1, newPass);
    if (response?.isError) {
      throw new HttpException('New Password sent failed', HttpStatus.BAD_REQUEST);
    }
    return {
      message: 'New Password Sent  Successfully to your Registered email',
    };
  }
}
