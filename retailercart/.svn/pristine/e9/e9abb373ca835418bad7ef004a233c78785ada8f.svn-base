import { Body, Controller, Get, Post, Query, UploadedFile, UseInterceptors, Request, UseGuards } from '@nestjs/common';
import { ApiBearerAuth, ApiBody, ApiConsumes, ApiProperty } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';
import { QueryCompanyInput } from 'src/shared/query-imput';
import { CreateKycDetailDto } from './dto/create-kyc-detail.dto';
import { KycDetailsService } from './kyc-details.service';
import { FileInterceptor } from '@nestjs/platform-express/multer';
import { Public } from 'src/auth/decorators/public.decorator';

@Controller('kyc')
export class KycDetailsController {
  constructor(private readonly kycDetailsService: KycDetailsService) {}

  @ApiBearerAuth('access-token')
  @ApiProperty()
  @UseGuards(AuthGuard)
  @Post()
  create(@Body() createKycDetailDto: CreateKycDetailDto, @Request() req: any) {
    return this.kycDetailsService.create(createKycDetailDto, req?.user?.username);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get()
  findOne(@Request() req: any, @Query() data: QueryCompanyInput) {
    return this.kycDetailsService.findOne(req?.user?.username, data.cmpCode);
  }

  @Public()
  @ApiConsumes('multipart/form-data')
  @ApiBody({
    schema: {
      type: 'object',
      properties: {
        file: {
          type: 'string',
          format: 'binary',
        },
      },
    },
  })
  @Post('upload')
  @UseInterceptors(FileInterceptor('file'))
  async uploadFile(@UploadedFile() file: Express.Multer.File, @Query('filename') filename: string) {
    const { buffer, mimetype } = file;
    return this.kycDetailsService.uploadFile(filename, buffer, mimetype);
  }
}
