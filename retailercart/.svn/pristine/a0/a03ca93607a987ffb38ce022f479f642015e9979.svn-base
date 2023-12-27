import { ApiProperty } from '@nestjs/swagger';
import { IsString } from 'class-validator';

export class ProductDto {
  @ApiProperty({ example: 'CMP001' })
  @IsString()
  cmpCode: string;

  @ApiProperty({ example: 'PROD209' })
  @IsString()
  prodCode: string;

  @ApiProperty({ example: 'CAT001' })
  @IsString()
  catCode: string;

  @ApiProperty({ example: 'SubCat001' })
  @IsString()
  subCatCode: string;

  @ApiProperty({ example: 'Batch001' })
  @IsString()
  batchCode: string;
}
