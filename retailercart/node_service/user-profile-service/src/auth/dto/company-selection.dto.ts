import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty } from 'class-validator';

export class CompanySelection {
  @ApiProperty({ examples: ['company'] })
  @IsNotEmpty()
  company: string[];
}
