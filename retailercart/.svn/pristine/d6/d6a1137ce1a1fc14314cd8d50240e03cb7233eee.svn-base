import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Query,
  Request,
  UseGuards,
} from '@nestjs/common';
import { ApiBearerAuth, ApiExcludeEndpoint } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import {
  AddWishlistStatusDto,
  PaginationDto,
  WishlistDto,
} from './dto/create-wishlist.dto';
import { WishlistService } from './wishlist.service';

@Controller('wishlist')
export class WishlistController {
  constructor(private readonly wishlistService: WishlistService) {}

  //Create wishlist or Remove already added
  @Post()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  wishList(@Request() req: any, @Body() createWishlistDto: WishlistDto) {
    return this.wishlistService.add(req, createWishlistDto);
  }

  //Remove wishlist by using ID
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Delete('remove/:wishlistId')
  removeWishlist(@Request() req: any, @Param('wishlistId') wishlistId: string) {
    return this.wishlistService.removeWishlist(req?.user?.username, wishlistId);
  }

  //Get all Wishlist Products
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('list')
  findAll(@Request() req: any, @Body() paginationReq: PaginationDto) {
    return this.wishlistService.getWishlist(req, paginationReq);
  }

  //Internal
  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('status')
  wishlistStatus(
    @Request() req: any,
    @Body() getWishlistStatus: AddWishlistStatusDto,
  ) {
    return this.wishlistService.getWishlistStatus(req, getWishlistStatus);
  }

  //Internal
  @ApiExcludeEndpoint()
  @Get('status/list')
  async wishlist(
    @Query('cmpCode') cmpCode: string,
    @Query('username') username: string,
  ) {
    return await this.wishlistService.getWishlistInternal(username, cmpCode);
  }
}
