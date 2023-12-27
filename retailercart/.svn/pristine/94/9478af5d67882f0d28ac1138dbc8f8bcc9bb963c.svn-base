import { IsBoolean, IsNotEmpty, IsString } from 'class-validator';

export class CreateAppliedSchemeDto {
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @IsString()
  @IsNotEmpty()
  schemeCode: string;

  @IsString()
  @IsNotEmpty()
  userCode: string;

  @IsString()
  distCode: string;

  @IsString()
  @IsNotEmpty()
  orderNo: string;

  @IsString()
  @IsNotEmpty()
  schemeValue: string;

  @IsBoolean()
  @IsNotEmpty()
  isProductBasedScheme: boolean;
}
