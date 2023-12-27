import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber, IsOptional, IsString } from 'class-validator';
import { PrimaryColumn } from 'typeorm';

export class CreateWishlistDto {
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

  @ApiProperty({ example: 'PROD0001' })
  @IsString()
  @IsOptional()
  prodCode: string;

  @ApiProperty({ example: 'BATCH001' })
  @IsString()
  @IsOptional()
  batchCode: string;

  @ApiProperty({ example: 'Product Name' })
  @IsString()
  @IsOptional()
  prodName: string;

  @ApiProperty({ example: '0.00' })
  @IsString()
  @IsOptional()
  prodMrp: string;

  @ApiProperty({ example: '0.00' })
  @IsString()
  @IsOptional()
  prodRetailerPrice: string;
}

export class WishlistDto {
  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '01100165' })
  @IsString()
  @IsOptional()
  prodCode: string;

  @ApiProperty({ example: 'TESTQQ667' })
  @IsString()
  @IsOptional()
  batchCode: string;
}

export class WishlistStatusDto {
  @ApiProperty({ example: 'userName' })
  @PrimaryColumn()
  @IsString()
  @IsOptional()
  userName: string;

  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '01100165' })
  @IsString()
  @IsOptional()
  prodCode: string;

  @ApiProperty({ example: 'TESTQQ667' })
  @IsString()
  @IsOptional()
  batchCode: string;
}

export class AddWishlistStatusDto {
  @ApiProperty({ example: 'COMP' })
  @PrimaryColumn()
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({ example: '01100165' })
  @IsString()
  @IsOptional()
  prodCode: string;

  @ApiProperty({ example: 'TESTQQ667' })
  @IsString()
  @IsOptional()
  batchCode: string;
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
