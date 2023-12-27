import {
  Body,
  Controller,
  Get,
  Param,
  Post,
  Request,
  UseGuards,
} from '@nestjs/common';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import { OrderService } from './order.service';
import { PaginationDto, RequestOrderDto } from './dto/order.dto';

@Controller()
export class OrderController {
  constructor(private readonly orderService: OrderService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('place')
  async createOrder(@Request() req: any, @Body() saveOrder: RequestOrderDto) {
    return await this.orderService.placeOrder(req, saveOrder);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('history')
  async findAll(@Request() req: any, @Body() params: PaginationDto) {
    return await this.orderService.findAll(req, params);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('summary/:orderNo')
  async findOne(@Request() req: any, @Param('orderNo') orderNo: string) {
    return await this.orderService.findOrderById(req, orderNo);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('buy/again/:orderNo')
  async buyAgain(@Request() req: any, @Param('orderNo') orderNo: string) {
    return await this.orderService.createReOrder(req, orderNo);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('delivery/tracking/:orderNo')
  async deliveryTracking(
    @Request() req: any,
    @Param('orderNo') orderNo: string,
  ) {
    return await this.orderService.deliveryTracking(req, orderNo);
  }
}
