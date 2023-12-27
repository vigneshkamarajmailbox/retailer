import { ApiProperty } from '@nestjs/swagger';
import { IsArray, IsNotEmpty, IsString } from 'class-validator';
import { ReturnTableStatusValue } from 'src/shared/enum/table-value.enum';

export class ReturnItem {
  @ApiProperty({ example: 'DIST001' })
  @IsNotEmpty()
  @IsString()
  distCode: string;

  @ApiProperty({ example: 'PROD001' })
  @IsNotEmpty()
  @IsString()
  prodCode: string;

  @ApiProperty({ example: 'PC' })
  @IsNotEmpty()
  @IsString()
  prodUomCode: string;

  // @ApiProperty({ example: '10' })
  // @IsNotEmpty()
  // @IsString()
  // prodQty: number;

  @ApiProperty({ example: '5' })
  @IsNotEmpty()
  @IsString()
  returnQty: number;
}

export class CreateReturnDto {
  @ApiProperty({ example: 'COMP' })
  @IsNotEmpty()
  @IsString()
  cmpCode: string;

  @ApiProperty({ example: 'a1f654ce-ace8-4375-a050-e52334e70c9a' })
  @IsNotEmpty()
  @IsString()
  orderNo: string;

  // @ApiProperty({ example: '2023-11-03' })
  // @IsNotEmpty()
  // @IsString()
  // returnDate: string;

  @ApiProperty({ example: 'Partial or Full' })
  @IsNotEmpty()
  @IsString()
  returnType: string;

  @ApiProperty({ example: 'Damaged' })
  @IsNotEmpty()
  @IsString()
  returnReason: string;

  // @ApiProperty({ example: 'Initiated' })
  // @IsNotEmpty()
  // @IsString()
  returnStatus: ReturnTableStatusValue;

  @ApiProperty({ type: ReturnItem, isArray: true })
  @IsNotEmpty()
  @IsArray()
  returnItem: ReturnItem[];
}
