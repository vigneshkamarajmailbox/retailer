import { ApiProperty } from '@nestjs/swagger';
import { IsString } from 'class-validator';

export class HighlightsDto {
  @ApiProperty({ example: 'CMP001' })
  @IsString()
  cmpCode: string;

  @ApiProperty({ example: '20% Off' })
  @IsString()
  title: string;

  @ApiProperty({ example: 'Jamun' })
  @IsString()
  content: string;

  @ApiProperty({ example: 'https://s3.buket/img' })
  @IsString()
  image: string;
}
