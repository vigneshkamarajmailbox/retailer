import { Body, Controller, Get, Param, Patch, UseGuards, Request, Query } from '@nestjs/common';
import { ApiBearerAuth } from '@nestjs/swagger';
import { UpdateNotificationDto } from './dto/update-notification.dto';
import { NotificationService } from './notification.service';
import { AuthGuard } from 'src/auth/auth.guard';
import { QueryCompanyInput } from 'src/shared/query-imput';

@Controller('notification')
export class NotificationController {
  constructor(private readonly notificationService: NotificationService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get()
  async findAll(@Query() data: QueryCompanyInput, @Request() req: any) {
    return await this.notificationService.findAll(data.cmpCode, req?.user?.username);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Patch(':code')
  update(@Param('code') id: string, @Body() updateNotificationDto: UpdateNotificationDto, @Request() req: any) {
    return this.notificationService.update(id, updateNotificationDto, req?.user?.username);
  }
}
