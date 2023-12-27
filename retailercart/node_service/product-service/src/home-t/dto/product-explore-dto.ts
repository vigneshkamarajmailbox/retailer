import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';

export class ProductExplore {
  @ApiProperty({
    example: 'COMP',
    required: true,
  })
  @IsString()
  @IsNotEmpty()
  cmpCode: string;

  @ApiProperty({
    example: 'Most Recommended Products',
    required: true,
  })
  @IsString()
  @IsNotEmpty()
  tagCode: string;
}
