import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class BannerDto {
  @ApiProperty({ example: 'Most Wanted' })
  @IsString()
  @IsNotEmpty()
  name: string;

  @ApiProperty({ example: 'CMP001' })
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: 1 })
  @IsNumber()
  @IsNotEmpty()
  sequence: number;

  @ApiProperty({ example: 'https://s3.buket/img' })
  @IsString()
  url: string;

  @ApiProperty({ example: 'png' })
  @IsString()
  @IsNotEmpty()
  format: string;
}
