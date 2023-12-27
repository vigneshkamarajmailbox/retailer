import {
  Body,
  Controller,
  Post,
  UseGuards,
  Request,
  Patch,
  Get,
  Param,
  Delete,
  ParseArrayPipe,
} from '@nestjs/common';
import { CartDetailsService } from './cart-details.service';
import { ApiBearerAuth, ApiBody, ApiExcludeEndpoint } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import {
  AddCartDetailsDto,
  PaginationDto,
  UpdateCartDetailsDto,
  CartBillDto,
  DeleteCartDto,
} from './dto/create-cart-detail.dto';

@Controller()
export class CartDetailsController {
  constructor(private readonly cartDetailsService: CartDetailsService) {}

  //Insert
  @ApiBody({
    type: AddCartDetailsDto,
    isArray: true,
    required: true,
    description: 'Cart add and Update in single api call ',
  })
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('add')
  addToCart(
    @Request() req: any,
    @Body(new ParseArrayPipe({ items: AddCartDetailsDto }))
    createCartDto: AddCartDetailsDto[],
  ) {
    return this.cartDetailsService.addToCart(req, createCartDto);
  }
  //Update
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Patch('update')
  updateQuantity(
    @Request() req: any,
    @Body() updateCartDto: UpdateCartDetailsDto,
  ) {
    return this.cartDetailsService.updateCartDetail(
      req,
      updateCartDto.id,
      updateCartDto,
    );
  }

  //Delete
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Delete('remove')
  remove(@Request() req: any, @Body() cartListId: DeleteCartDto) {
    return this.cartDetailsService.delete(req, cartListId);
  }

  //Fetch Details List
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('list')
  findAll(@Request() req: any, @Body() paginationReq: PaginationDto) {
    return this.cartDetailsService.getCartList(req, paginationReq);
  }

  //calculate Chosen cart items total
  // @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('choose-scheme')
  cartTotal(@Request() req: any, @Body() Request: CartBillDto) {
    return this.cartDetailsService.getFinalPrice(req, Request);
  }

  //Internal API Calls

  //Fetch Detail By Id
  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get(':cartId')
  getCartDetail(@Request() req: any, @Param('cartId') cartListId: string) {
    return this.cartDetailsService.getCartDetailByID(req, cartListId);
  }

  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('cart-header-info/:userName')
  cartHeaderInfo(@Request() req: any, @Param('userName') userName: string) {
    return this.cartDetailsService.getCartHeaderDetails(userName);
  }

  //change cart details status
  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('change-cartStatus')
  updateCartStatus(@Body() cartListIds: any) {
    return this.cartDetailsService.updateCartStatus(cartListIds);
  }

  //Change cart header status
  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Get('change-cartHeaderStatus/:cartId')
  updateCartHeaderStatus(@Param('cartId') cartId: string) {
    return this.cartDetailsService.updateCartHeaderStatus(cartId);
  }
}
