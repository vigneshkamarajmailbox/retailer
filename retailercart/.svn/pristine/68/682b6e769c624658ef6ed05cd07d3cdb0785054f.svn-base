import { ApiProperty } from '@nestjs/swagger';
import { IsCompanyExists } from 'src/common/validation/decorators/company-validation.decorator';

export class UpdateNotificationDto {
  @ApiProperty({ example: 'COMP' })
  @IsCompanyExists()
  cmpCode: string;

  @ApiProperty({ example: true })
  isSeen: boolean;
}
