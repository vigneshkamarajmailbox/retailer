import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import {
  IsArray,
  IsDefined,
  IsNotEmpty,
  IsNotEmptyObject,
  IsNumber,
  IsObject,
  IsOptional,
  IsString,
  ValidateNested,
} from 'class-validator';
import { PrimaryColumn } from 'typeorm';

export class CreateOrderDto {
  @ApiProperty({ example: 'a1f654ce-ace8-4375-a050-e52334e70c9a' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  orderNo: string;

  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: 'userName' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  userName: string;

  @ApiProperty({ example: 'DIST001' })
  @IsString()
  @IsNotEmpty()
  distCode: string;

  @ApiProperty({ example: 'PROD0001' })
  @IsString()
  @IsOptional()
  prodCode: string;

  @ApiProperty({ example: 'Product Name' })
  @IsString()
  @IsOptional()
  prodName: string;

  @ApiProperty({ example: 'BATCH001' })
  @IsString()
  @IsOptional()
  batchCode: string;

  @ApiProperty({ example: '0.000' })
  @IsString()
  @IsOptional()
  prodMrp: number;

  @ApiProperty({ example: '0.000' })
  @IsString()
  @IsOptional()
  prodRetailerPrice: number;

  @ApiProperty({ example: '10' })
  @IsNumber()
  @IsOptional()
  prodQty: number;

  @ApiProperty({ example: '[UOM details]' })
  @IsArray()
  @IsOptional()
  uomCode: any;

  @ApiProperty({ example: 'Grams' })
  @IsString()
  @IsOptional()
  weightCode: string;

  @ApiProperty({ example: '150g' })
  @IsString()
  @IsOptional()
  weightValue: string;

  @ApiProperty({ example: '0' })
  @IsNumber()
  @IsOptional()
  taxPercentage: number;

  @ApiProperty({ example: '0.000' })
  @IsNumber()
  @IsOptional()
  taxAmount: number;

  @ApiProperty({ example: '0.000' })
  @IsString()
  @IsOptional()
  totalAmount: number;

  @ApiProperty({ example: 'true' })
  @IsString()
  @IsOptional()
  orderStatus: string;
}

export class OrderDto {
  @IsDefined()
  @IsNotEmptyObject()
  @IsObject()
  @ValidateNested()
  @Type(() => CreateOrderDto)
  name!: CreateOrderDto;
}
