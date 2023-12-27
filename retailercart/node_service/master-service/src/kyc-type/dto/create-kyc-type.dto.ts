import { IsNotEmpty, IsString } from 'class-validator';

export class CreateKycTypeDto {
  @IsString()
  @IsNotEmpty()
  code: string;

  @IsString()
  @IsNotEmpty()
  name: string;
}
