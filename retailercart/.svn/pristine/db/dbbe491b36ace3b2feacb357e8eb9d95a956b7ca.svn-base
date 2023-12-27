import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import { IsEmail, IsNotEmpty, IsNumber, IsOptional, IsString, Max, MaxLength, Min, MinLength } from 'class-validator';
import { IsCompanyExists } from 'src/common/validation/decorators/company-validation.decorator';
import { IsUserNameExists } from 'src/common/validation/decorators/user-validation.decorator';

export class CreateKycDetailDto {
  @IsString()
  @IsOptional()
  @MinLength(3)
  @MaxLength(100)
  @IsUserNameExists()
  userName: string;

  @ApiProperty({ example: 'CMP' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(50)
  @IsCompanyExists()
  cmpCode: string;

  @ApiProperty({ example: '10002345000678', minLength: 9, maxLength: 16 })
  @IsNumber()
  @IsNotEmpty()
  @Min(9)
  @Max(16)
  accountNo: number;

  @ApiProperty({ example: 'Bala Krishna' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(250)
  accountName: string;

  @ApiProperty({ example: 'Indian Bank' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(250)
  bankName: string;

  @ApiProperty({ example: 'Triplicane' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(250)
  bankBranch: string;

  @ApiProperty({ example: 'IDIB00007' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(20)
  ifsc: string;

  @ApiProperty({ example: 'examble@mail.com' })
  @IsEmail()
  @IsString()
  @MinLength(5)
  @MaxLength(200)
  email: string;

  @ApiPropertyOptional({ example: './path/exampleimage.png' })
  @IsString()
  @MaxLength(200)
  documentImage: string;

  @ApiPropertyOptional({ example: 'png, mp4,pdf ..etc' })
  @IsString()
  @MinLength(3)
  @MaxLength(100)
  documentType: string;
}
