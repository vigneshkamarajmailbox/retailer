import { Body, Controller, Post, Request, UseGuards } from '@nestjs/common';
import { ApiBearerAuth, ApiProperty } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import { CreateReturnDto } from './dto/create-return.dto';
import { ReturnService } from './return.service';

@Controller('return')
export class ReturnController {
  constructor(private readonly returnService: ReturnService) {}

  @ApiBearerAuth('access-token')
  @ApiProperty()
  @UseGuards(AuthGuard)
  @Post()
  create(@Body() createReturnDto: CreateReturnDto, @Request() req) {
    return this.returnService.create(createReturnDto, req.user.username);
  }
}
