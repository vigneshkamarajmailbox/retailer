import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';

export class ForgotPassDto {
  @ApiProperty({ example: 'Api@getMaxListeners.com', maxLength: 10 })
  @IsString()
  @IsNotEmpty()
  email: string;
}
