import { ApiProperty } from '@nestjs/swagger';
import { IsString } from 'class-validator';

export class ProductIntDto {
  @ApiProperty({ example: 'CMP001' })
  @IsString()
  cmpCode: string;

  @ApiProperty({ example: 'PROD209' })
  @IsString()
  prodCode: string;

  @ApiProperty({ example: 'Batch001' })
  @IsString()
  batchCode: string;
}
