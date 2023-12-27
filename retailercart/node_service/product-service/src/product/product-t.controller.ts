import { Body, Controller, Post, UseGuards, Request } from '@nestjs/common';
import { ApiBearerAuth, ApiExcludeEndpoint } from '@nestjs/swagger';
import { AuthGuard } from 'src/common/helper/auth.guard';
import { ProductDTO, ProductListDTO } from './../home-t/dto/product-list.dto';
import { ProductTService } from './product-t.service';
import { CartDTO } from './../home-t/dto/cart-request-dto';

@Controller('product')
export class ProductTController {
  constructor(private readonly productService: ProductTService) {}

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post()
  async getProduct(@Body() product: ProductDTO, @Request() req: any) {
    return await this.productService.getProduct(product, req.user.username);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('list')
  async getProductList(
    @Body() productListDTO: ProductListDTO,
    @Request() req: any,
  ) {
    return await this.productService.getProductList(
      productListDTO,
      req?.user?.username,
      req.headers.authorization,
    );
  }

  @ApiExcludeEndpoint()
  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('cart')
  async getProductCart(@Body() cartDTO: CartDTO) {
    return await this.productService.getCartRequest(cartDTO);
  }
}
