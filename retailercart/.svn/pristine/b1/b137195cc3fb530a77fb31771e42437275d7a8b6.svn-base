import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import {
  IsArray,
  IsBoolean,
  IsNotEmpty,
  IsNumber,
  IsOptional,
  IsString,
  ValidateNested,
} from 'class-validator';
import { PrimaryColumn } from 'typeorm';

//Add Cart DTO
export class QtyDetail {
  @ApiProperty({ example: 'Piece' })
  @IsString()
  @IsOptional()
  code: string;

  @ApiProperty({ example: 'PC' })
  @IsString()
  @IsOptional()
  name: string;

  @ApiProperty({ example: '1' })
  @IsString()
  @IsOptional()
  conversionFactor: string;

  @ApiProperty({ example: '4' })
  @IsNumber()
  @IsOptional()
  orderQty: number;
}

export class AddCartDetailsDto {
  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '01100165' })
  @IsString()
  @IsNotEmpty()
  prodCode: string;

  @ApiProperty({ example: 'TESTQQ667' })
  @IsString()
  @IsNotEmpty()
  batchCode: string;

  @ApiProperty({ type: QtyDetail, isArray: true, required: true })
  @IsArray()
  @IsNotEmpty()
  @ValidateNested({ each: true })
  @Type(() => QtyDetail)
  uom: QtyDetail[];
}

//Update Cart DTO
export class UpdateCartDetailsDto {
  @ApiProperty({ example: 'a1f654ce-ace8-4375-a050-e52334e70c9a' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  id: string;

  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '01100165' })
  @IsString()
  @IsNotEmpty()
  prodCode: string;

  @ApiProperty({ example: 'TESTQQ667' })
  @IsString()
  @IsNotEmpty()
  batchCode: string;

  @ApiProperty({ type: QtyDetail, isArray: true, required: true })
  @IsArray()
  @IsNotEmpty()
  @ValidateNested({ each: true })
  @Type(() => QtyDetail)
  uom: QtyDetail[];
}

//Pagination
export class PaginationDto {
  @ApiProperty({ example: '10' })
  @IsNumber()
  @IsNotEmpty()
  limit: number;

  @ApiProperty({ example: '0' })
  @IsNumber()
  @IsNotEmpty()
  offset: number;
}

//Delete Cart DTO
export class DeleteCartDto {
  @ApiProperty({ example: '["a1f654ce-ace8-4375-a050-e52334e70c9a"]' })
  @IsArray()
  @IsNotEmpty()
  cartIds: [];
}

//Get Cart bill
export class CartSchemeDto {
  @ApiProperty({ example: 'SCH002' })
  @IsString()
  @IsOptional()
  schemeCode: string;

  @ApiProperty({ example: 'SLAP003' })
  @IsString()
  @IsOptional()
  slapCode: string;

  @ApiProperty({ example: 'DIST001' })
  @IsString()
  @IsOptional()
  distCode: string;
}

export class CartBillDto {
  @ApiProperty({ example: '["a1f654ce-ace8-4375-a050-e52334e70c9a"]' })
  @IsArray()
  @IsNotEmpty()
  cartItems: [];

  @ApiProperty({ type: CartSchemeDto, isArray: true })
  @IsArray()
  @IsNotEmpty()
  schemes: CartSchemeDto[];
}

//Other Dto's
export class CreateCartDetailsDto {
  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: 'userName' })
  @PrimaryColumn()
  @IsString()
  @IsOptional()
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

  //   @IsString()
  //   @IsOptional()
  //   prodMrp: string;

  //   @IsString()
  //   @IsOptional()
  //   prodRetailerPrice: string;

  //   @Exclude()
  //   @IsArray()
  //   @IsOptional()
  //   prodImage: any;

  @ApiProperty({ example: '10' })
  @IsNumber()
  @IsOptional()
  prodQty: number;

  @ApiProperty({ example: '5' })
  @IsNumber()
  @IsOptional()
  minQty: number;

  //   @IsString()
  //   @IsOptional()
  //   currencySymbol: string;
  @ApiProperty({ example: '[Order Qty Details]' })
  @IsArray()
  @IsOptional()
  cartQtyDetails: QtyDetail[];

  @ApiProperty({ example: '[]' })
  @IsArray()
  @IsOptional()
  cartUomDetails: string;

  @ApiProperty({ example: 'Grams' })
  @IsString()
  @IsOptional()
  weightCode: string;

  @ApiProperty({ example: '150g' })
  @IsString()
  @IsOptional()
  weightValue: string;

  @ApiProperty({ example: 'false' })
  @IsOptional()
  isApplyScheme: number;

  @ApiProperty({ example: '0' })
  @IsString()
  @IsOptional()
  schemePercentage: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  schemeAmount: number;

  @ApiProperty({ example: '0' })
  @IsNumber()
  @IsOptional()
  taxPercentage: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  taxAmount: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  totalAmount: number;

  @ApiProperty({ example: 'false' })
  @IsNumber()
  @IsOptional()
  isFreeProd: number;

  @ApiProperty({ example: 'true' })
  @IsBoolean()
  @IsOptional()
  isActive: boolean;
}

export class UpdateCartDetailDto {
  @ApiProperty({ example: '10' })
  @IsNumber()
  @IsOptional()
  prodQty: number;

  @ApiProperty({ example: '[Order Qty Details]' })
  @IsArray()
  @IsOptional()
  cartQtyDetails: QtyDetail[];

  @ApiProperty({ example: '[]' })
  @IsArray()
  @IsOptional()
  cartUomDetails: string;

  @ApiProperty({ example: '0' })
  @IsNumber()
  @IsOptional()
  taxPercentage: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  taxAmount: number;

  @ApiProperty({ example: '0.00' })
  @IsNumber()
  @IsOptional()
  totalAmount: number;
}

// export class CartDetailsDto {
//   @IsDefined()
//   @IsNotEmptyObject()
//   @IsObject()
//   @ValidateNested()
//   @Type(() => CreateCartDetailsDto)
//   name!: CreateCartDetailsDto;
// }

export class CreateCartHeader {
  @ApiProperty({ example: 'userName' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  userName: string;

  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '[cartIds]' })
  @IsArray()
  @IsOptional()
  CartItems: string;

  @ApiProperty({ example: '[schemesCode]' })
  @IsArray()
  @IsOptional()
  schemeCodes: string;

  @ApiProperty({ example: '100.00' })
  @IsString()
  @IsOptional()
  overAllAmount: number;

  @ApiProperty({ example: '0.00' })
  @IsString()
  @IsOptional()
  offerAmount: number;

  @ApiProperty({ example: '0.00' })
  @IsString()
  @IsOptional()
  discountAmount: number;

  @ApiProperty({ example: '[]' })
  @IsArray()
  @IsOptional()
  freeProduct: string;

  @ApiProperty({ example: '100.00' })
  @IsString()
  @IsOptional()
  payableAmount: number;

  @ApiProperty({ example: 'true' })
  @IsNumber()
  @IsOptional()
  isActiveSts: number;
}

export class GetCartDetailsByProdDto {
  @IsString()
  @IsNotEmpty()
  userName: string;

  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @IsString()
  @IsNotEmpty()
  prodCode: string;

  @IsBoolean()
  @IsNotEmpty()
  isActive: boolean;
}
