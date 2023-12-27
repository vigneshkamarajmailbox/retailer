import {
  IsArray,
  IsNotEmpty,
  IsNumber,
  IsObject,
  IsOptional,
  IsString,
  Min,
} from 'class-validator';

export class SchemePayloadProductHier {
  @IsString()
  @IsNotEmpty()
  levelType: string;

  @IsArray()
  @IsOptional()
  levelValue: string[];
}

export class SchemePayloadRetailerChannel {
  @IsString()
  @IsNotEmpty()
  channelCode: string;

  @IsString()
  @IsNotEmpty()
  groupCode: string;

  @IsString()
  @IsNotEmpty()
  classCode: string;
}

export class FetchAllSchemePayload {
  @IsString()
  @IsNotEmpty()
  currentDate: string;

  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @IsArray()
  @IsOptional()
  distCode: string[];

  @IsObject()
  @IsOptional()
  productHier: SchemePayloadProductHier;

  @IsObject()
  @IsOptional()
  retailerChannel: SchemePayloadRetailerChannel;

  @IsArray()
  @IsOptional()
  retailerCode: string[];

  @IsArray()
  @IsOptional()
  productCode: string[];

  @IsNumber()
  @IsNotEmpty()
  productQty: number;
}

export class FetchBySchemeCodePayload {
  @IsString()
  @IsNotEmpty()
  currentDate: string;

  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @IsArray()
  @IsNotEmpty()
  schemeCode: string[];

  @IsArray()
  @IsNotEmpty()
  slapCode: string[];

  @IsArray()
  @IsOptional()
  distCode: string[];

  @IsNumber()
  @Min(1)
  productQty: number;
}
