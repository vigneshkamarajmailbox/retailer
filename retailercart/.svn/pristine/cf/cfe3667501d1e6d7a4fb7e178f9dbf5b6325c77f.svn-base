import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import {
  IsArray,
  IsBoolean,
  IsDate,
  IsNotEmpty,
  IsNumber,
  IsOptional,
  IsString,
} from 'class-validator';
import { PrimaryColumn } from 'typeorm';

//

/////////////////////////////////////////////////////////////////////////////

//Get Order Info
export class OrderSchemeDto {
  @ApiProperty({ example: 'SCH002' })
  @IsString()
  @IsOptional()
  schemeCode: string;

  @ApiProperty({ example: 'SLAP003' })
  @IsString()
  @IsOptional()
  slapCode: string;
}

export class SaveOrderDto {
  @ApiProperty({ example: '["a1f654ce-ace8-4375-a050-e52334e70c9a"]' })
  @IsArray()
  @IsNotEmpty()
  cartItems: [];

  @ApiProperty({ type: OrderSchemeDto, isArray: true })
  @IsArray()
  @IsNotEmpty()
  schemes: OrderSchemeDto[];
}

//Oder header Dto
export class CreateOrderHeaderDto {
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

  @ApiProperty({ example: '01-01-2023' })
  @Type(() => Date)
  @IsDate()
  @IsOptional()
  readonly orderDate: Date;

  @ApiProperty({ example: '01-01-2023' })
  @Type(() => Date)
  @IsDate()
  @IsOptional()
  readonly billDate: Date;

  @ApiProperty({ example: '01-01-2023' })
  @Type(() => Date)
  @IsDate()
  @IsOptional()
  readonly deliveryDate: Date;

  @ApiProperty({ example: '"No.21, Raja Street' })
  @IsString()
  @IsOptional()
  address1: string;

  @ApiProperty({ example: '"Nungambakkam, Chennai' })
  @IsString()
  @IsOptional()
  address2: string;

  @ApiProperty({ example: '90.00' })
  @IsNumber()
  @IsOptional()
  netAmount: number;

  @ApiProperty({ example: '100.00' })
  @IsNumber()
  @IsOptional()
  grossAmount: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  taxAmount: number;

  @ApiProperty({ example: 'false' })
  @IsBoolean()
  @IsOptional()
  isApplyScheme: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  discountAmount: number;

  @ApiProperty({ example: 'true' })
  @IsString()
  @IsOptional()
  orderStatus: string;
}
