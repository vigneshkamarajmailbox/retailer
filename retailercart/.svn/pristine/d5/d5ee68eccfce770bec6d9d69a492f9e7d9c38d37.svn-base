import { Inject, Injectable } from '@nestjs/common';
import { Cache } from 'cache-manager';
import { CACHE_MANAGER } from '@nestjs/cache-manager';

@Injectable()
export class CacheService {
  constructor(@Inject(CACHE_MANAGER) private cacheManager: Cache) {}

  async set(key: string, value: any): Promise<any> {
    await this.cacheManager
      .set(key, value, 600000)
      .then(() => {
        console.log('Setted Value!');
        return true;
      })
      .catch((error) => {
        console.error('Setted Reject!', error);
        return false;
      });
  }

  async get(key: string): Promise<any> {
    const result = await this.cacheManager.get(key);
    return result;
  }

  async expire(key: string): Promise<boolean> {
    try {
      await this.cacheManager.del(key);
      return true;
    } catch (error) {
      return false;
    }
  }
}
