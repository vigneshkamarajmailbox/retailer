import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';
import { IsUserNameExists } from 'src/common/validation/decorators/user-validation.decorator';

export class UserDto {
  @ApiProperty({ example: 'john' })
  @IsNotEmpty()
  @IsUserNameExists()
  userName: any;

  @ApiProperty({ example: 'p@ssWoRD' })
  @IsString()
  @IsNotEmpty()
  password: string;
}
