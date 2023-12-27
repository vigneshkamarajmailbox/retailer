import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Wishlist } from './entities/wishlist.entity';
import { CreateWishlistDto, WishlistDto } from './dto/create-wishlist.dto';

export class WishlistRepository {
  @InjectRepository(Wishlist)
  private wishlistRepo: Repository<Wishlist>;

  //Insert and Delete Wishlist Details
  async create(saveRequest: CreateWishlistDto): Promise<any> {
    const wishList = this.wishlistRepo.create(saveRequest as any);
    return await this.wishlistRepo.save(wishList);
  }

  async deleteWishlist(request): Promise<any> {
    return await this.wishlistRepo.delete(request);
  }

  //Fetch Wishlist Details
  async getWishList(
    userName: string,
    limit: number,
    offset: number,
  ): Promise<any> {
    const perPage = limit;
    const skip = offset; //page = 1 , perPage * page - perPage;

    return await this.wishlistRepo.find({
      where: { userName: userName },
      take: perPage,
      skip,
    });
  }

  async getWishlistById(userName: string, wishlistId: string): Promise<any> {
    return await this.wishlistRepo.findOne({
      where: { userName: userName, wishlistId: wishlistId },
    });
  }

  async getWishlistByProduct(reqParams: WishlistDto): Promise<any> {
    return await this.wishlistRepo.findOneBy(reqParams);
  }

  async getWishlist(cmpCode: string, username: string): Promise<any> {
    return await this.wishlistRepo.find({
      where: {
        cmpCode: cmpCode,
        userName: username,
      },
      select: {
        wishlistId: true,
        cmpCode: true,
        prodCode: true,
      },
    });
  }
}
