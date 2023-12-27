import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber, IsOptional, IsString, Matches, Max, MaxLength, Min, MinLength } from 'class-validator';
import { Pattern } from '../../shared/enum/pattern.enum';

export class SignupDto {
  @ApiProperty({ example: 'john' })
  @IsString()
  @IsNotEmpty()
  @MinLength(3)
  @MaxLength(100)
  userName: string;

  @ApiProperty({ example: 7402695090 })
  @IsNumber()
  @IsNotEmpty()
  @Min(1000000000)
  @Max(9999999999)
  mobileNo: number;

  @ApiProperty({ example: 'p@ssWoRD' })
  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  password: string;

  @ApiProperty({ example: '29GGGGG1314R9Z6' })
  @MaxLength(15)
  @MinLength(15)
  @IsString()
  @Matches(Pattern.GST)
  @IsOptional()
  gst: string;

  @ApiProperty({ example: 'ABCTY1234D' })
  @Matches(Pattern.PAN)
  @MaxLength(10)
  @MinLength(10)
  @IsString()
  @IsOptional()
  pan: string;

  @ApiProperty({ example: '600002' })
  @Max(999999)
  @Min(100000)
  @IsNotEmpty()
  postalCode: number;

  @ApiProperty({ example: 'john@gmail.com' })
  @IsNotEmpty()
  @MinLength(5)
  @MaxLength(200)
  email: string;
}
