import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import { IsNotEmpty, IsOptional, IsString, MaxLength, MinLength } from 'class-validator';
import { IsCompanyExists } from 'src/common/validation/decorators/company-validation.decorator';
import { IsUserNameExists } from 'src/common/validation/decorators/user-validation.decorator';

export class CreateFeedbackDto {
  @ApiProperty({ example: 'COMP' })
  @IsString()
  @IsNotEmpty()
  @MinLength(4)
  @MaxLength(50)
  @IsCompanyExists()
  cmpCode: string;

  @IsString()
  @IsOptional()
  @MinLength(3)
  @MaxLength(100)
  @IsUserNameExists()
  userName: string;

  @ApiProperty({ example: 'title' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(100)
  title: string;

  @ApiProperty({ example: 'comments' })
  @IsString()
  @IsNotEmpty()
  @MaxLength(250)
  comments: string;

  @ApiPropertyOptional({ example: 'http://s3bucket/path' })
  @IsString()
  @IsOptional()
  @MaxLength(250)
  imagePath: string;
}
