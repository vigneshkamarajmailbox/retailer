import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber } from 'class-validator';

export class LoginOTP {
  @ApiProperty({ example: '7402695090' })
  @IsNumber()
  @IsNotEmpty()
  mobileNo: number;
}
