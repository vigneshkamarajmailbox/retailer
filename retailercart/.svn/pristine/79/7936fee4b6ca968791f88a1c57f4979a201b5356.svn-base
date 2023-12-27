import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber } from 'class-validator';

export class SendOtpDto {
  @ApiProperty({ example: 9876543210, maxLength: 10 })
  @IsNumber()
  @IsNotEmpty()
  mobileNo: number;
}
