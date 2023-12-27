import { InjectRepository } from '@nestjs/typeorm';
import { Repository, In } from 'typeorm';
import { CartDetails } from './entities/cart-detail.entity';
import {
  CreateCartDetailsDto,
  GetCartDetailsByProdDto,
  CreateCartHeader,
  UpdateCartDetailDto,
} from './dto/create-cart-detail.dto';
import { UpdateCartHeaderDto } from './dto/update-cart-detail.dto';
import { CartHeader } from './entities/cart-header.entity';

export class CartRepository {
  @InjectRepository(CartDetails)
  private cartRepo: Repository<CartDetails>;

  @InjectRepository(CartHeader)
  private cartHeaderRepo: Repository<CartHeader>;

  //Insert, Update and Delete cart Details
  async create(saveRequest: CreateCartDetailsDto[]): Promise<any> {
    const cart = this.cartRepo.create(saveRequest as any);
    return await this.cartRepo.save(cart);
  }

  async update(
    userName: string,
    cartListId: string,
    updateRequest: UpdateCartDetailDto,
  ): Promise<CartDetails | null> {
    await this.cartRepo.update({ cartListId: cartListId }, updateRequest);
    // Return
    return await this.cartRepo.findOneOrFail({
      select: {
        cartUomDetails: false,
        isFreeProd: false,
        isActive: false,
        created_at: false,
        mod_dt: false,
      },
      where: { cartListId: cartListId, userName: userName, isActive: true },
    });
  }

  async deleteCartDetail(userName: string, cartListIds: any): Promise<any> {
    return await this.cartRepo
      .createQueryBuilder('cart')
      .delete()
      .from(CartDetails)
      .where('user_name = :userName AND cart_list_id IN(:cartListId)', {
        userName: userName,
        cartListId: cartListIds,
      })
      .execute();
  }

  //Fetch Cart Details
  async getCartList(
    userName: string,
    limit: number,
    offset: number,
  ): Promise<any> {
    const perPage = limit;
    const skip = offset; //page = 1 , perPage * page - perPage;

    return await this.cartRepo.find({
      select: {
        cartUomDetails: false,
        isFreeProd: false,
        isActive: false,
        created_at: false,
        mod_dt: false,
      },
      where: { userName: userName, isActive: true },
      take: perPage,
      skip,
    });
  }

  async getCartDetailById(userName: string, cartListId: string): Promise<any> {
    return await this.cartRepo.findOne({
      select: {
        cartUomDetails: false,
        isFreeProd: false,
        isActive: false,
        created_at: false,
        mod_dt: false,
      },
      where: { userName: userName, cartListId: cartListId, isActive: true },
    });
  }

  async getCartDetailByProduct(
    reqParams: GetCartDetailsByProdDto,
  ): Promise<any> {
    return await this.cartRepo.findOneBy(reqParams);
  }

  async getCartDetailsMultipleIds(userName: string, cartItemIds: any) {
    return await this.cartRepo.find({
      select: {
        cartUomDetails: false,
        isFreeProd: false,
        isActive: false,
        created_at: false,
        mod_dt: false,
      },
      where: {
        userName: userName,
        isActive: true,
        cartListId: In(cartItemIds),
      },
    });
  }

  //Cart Header
  async getCartHeader(userName: string) {
    return await this.cartHeaderRepo.findOneBy({
      userName: userName,
      isActiveSts: 1,
    });
  }

  async createCartHeader(CartHeaderData: CreateCartHeader) {
    return await this.cartHeaderRepo.save(CartHeaderData);
  }

  async updateCartHeader(cartId: string, CartHeaderData: UpdateCartHeaderDto) {
    return await this.cartHeaderRepo.update({ cartId: cartId }, CartHeaderData);
  }

  //Internal api
  async updateCartDetailStatus(cartListIds: any) {
    return await this.cartRepo.update(
      { cartListId: In(cartListIds) },
      { isActive: false },
    );
  }

  async updateCartHeaderStatus(cartId: any) {
    return await this.cartHeaderRepo.update(
      { cartId: cartId },
      { isActiveSts: 0 },
    );
  }
}
