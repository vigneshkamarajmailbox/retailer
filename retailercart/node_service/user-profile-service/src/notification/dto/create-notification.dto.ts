import { ApiProperty } from '@nestjs/swagger';
import { IsCompanyExists } from 'src/common/validation/decorators/company-validation.decorator';

export class CreateNotificationDto {
  @ApiProperty({ example: 'COMP' })
  @IsCompanyExists()
  readonly cmpCode: string;
  @ApiProperty({ example: 'notificationCode' })
  readonly notificationCode: string;
  @ApiProperty({ example: 'title' })
  readonly title: string;
  @ApiProperty({ example: 'desc' })
  readonly desc: string;
  @ApiProperty({ example: 'time' })
  readonly notificationTime: Date;
}
