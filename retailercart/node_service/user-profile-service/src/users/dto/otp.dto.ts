import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber } from 'class-validator';

export class UserOtpDto {
  @ApiProperty({ example: 7402695090, maxLength: 10 })
  @IsNumber()
  @IsNotEmpty()
  mobileNo: number; // primary key foreign key

  @ApiProperty({ example: 1234, maxLength: 4, minLength: 4 })
  @IsNumber()
  @IsNotEmpty()
  otp: number;

  expired_time: Date;

  mod_dt: Date;
}
