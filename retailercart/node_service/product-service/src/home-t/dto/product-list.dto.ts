import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import { IsEnum, IsNotEmpty, IsOptional, IsString } from 'class-validator';
import { ORDER_BY } from 'src/shared/enum/order-by.enum';

export class Price {
  @ApiPropertyOptional({ example: 100, required: false })
  min: number;
  @ApiPropertyOptional({ example: 1000, required: false })
  max: number;
}

export class ProductDTO {
  @ApiProperty({
    example: 'COMP',
    required: true,
  })
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({
    example: '01700057',
    required: true,
  })
  @IsString()
  @IsNotEmpty()
  groupCode: string;
}
export class ProductListDTO {
  @ApiProperty({
    example: 'COMP',
    required: true,
  })
  @IsNotEmpty()
  @IsString()
  cmpCode: string;

  @ApiProperty({
    example: 'WGW AYURVEDIC',
    required: false,
  })
  name?: string;
  @ApiProperty({
    example: 'home category filter option',
    required: false,
    description: 'optional field ',
  })
  category?: string;

  @ApiProperty({
    example: 'sub category filter option from product list',
    required: false,
    description: 'optional field ',
  })
  subCategory?: string;

  @ApiProperty({
    example: 'COMP',
    required: false,
    description: 'optional field ',
  })
  brand?: string;

  @ApiProperty({ type: Price, required: false, description: 'optional field ' })
  price?: Price;

  @ApiPropertyOptional({
    example: 'Price',
    required: false,
    description: 'optional field ',
  })
  @IsOptional()
  sortBy?: string;

  @ApiPropertyOptional({
    example: 'ASC',
    required: false,
    enum: ORDER_BY,
  })
  @IsOptional()
  @IsEnum(ORDER_BY)
  sortOrder?: ORDER_BY;

  @ApiProperty({ example: 10, required: true })
  limit: number;
  @ApiProperty({ example: 1, required: true })
  offset: number;
}

export interface ProductList {
  categoryList: CategoryList[];
  productList: Product[];
  url: string;
  currentPage: number;
  limit: number;
  offset: number;
  totalPage: number;
}

export interface CategoryList {
  cmpCode: string;
  categoryCode: string;
  name: string;
  image: string;
}

export interface Product {
  cmpCode: string;
  prodCode: string;
  batchCode: string;
  name: string;
  desc: string;
  shortName: string;
  shortDesc: string;
  isWishlist: boolean;
  currencySymbol: string;
  weightType: string;
  weightValue: string;
  mrp: number;
  priceToRetailer: number;
  suggestedQty: number;
  minimumOrderQty: number;
  priceAfterDiscount: number;
  uom: Uom[];
  appliedScheme: AppliedScheme;
  scheme: Scheme[];
  productMedia: ProductMediaModel[];
}
export interface ProductMediaModel {
  mediaType: string;
  mediaUrl: string;
}

export interface Uom {
  code: string;
  name: string;
  conversionFactor: number;
}

export interface Weight {
  code: string;
  name: string;
  mrp: number;
  priceToRetailer: number;
  suggestedQty?: number;
  minimumOrderQty?: number;
  priceAfterDiscount?: number;
}

export interface AppliedScheme {
  code: string;
  name: string;
  desc: string;
}

export interface Scheme {
  code: string;
  name: string;
  desc: string;
}
