import { ApiProperty } from '@nestjs/swagger';
import { IsNumber, IsOptional, IsString } from 'class-validator';

export class ListProductDto {
  @ApiProperty({ example: 'CMP001' })
  @IsString()
  cmpCode: string;

  @ApiProperty({ example: 'Apples' })
  @IsString()
  @IsOptional()
  category: string;

  @ApiProperty({ example: 10 })
  @IsNumber()
  limit: number;

  @ApiProperty({ example: 2 })
  @IsNumber()
  offset: number;
}
