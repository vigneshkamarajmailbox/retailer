import { Inject, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { DigitalContent } from './entities/digital-content.entity';
import { CacheService } from 'src/shared/cache.service';

@Injectable()
export class DigitalContentService {
  constructor(
    @InjectRepository(DigitalContent)
    private digitalContentRepository: Repository<DigitalContent>,
  ) {}

  @Inject()
  private cacheService: CacheService;

  async findAll() {
    const key = this.cacheKeyFormat();
    const cache = await this.cacheService.get(key);
    console.log(key, cache, 'CACHED DATA');
    if (cache) {
      console.log('data from Cached');
      return this.validateCache(key, JSON.parse(cache));
      // return {
      //   statusCode: 200,
      //   message: 'Digital Content',
      //   digitalContent: JSON.parse(cache),
      // };
    }
    const digitalContent = await this.digitalContentRepository.find({
      select: { cmpCode: true, title: true, description: true, mediaType: true, mediaName: true, isDownload: true },
      cache: 60000,
    });
    return this.validateCache(key, digitalContent);
    // return {
    //   statusCode: 200,
    //   message: 'Digital Content',
    //   digitalContent: digitalContent,
    // };
  }

  findOne(id: string) {
    return this.digitalContentRepository.find({
      select: {
        cmpCode: true,
        title: true,
        description: true,
        mediaType: true,
        mediaName: true,
        isDownload: true,
      },
      relations: { company: false },
      where: { brochure_code: id },
      cache: 60000,
    });
  }

  cacheKeyFormat() {
    return `digital-content`;
  }

  async validateCache(key: string, response: any) {
    await this.cacheService.set(key, JSON.stringify(response));
    return {
      statusCode: 200,
      message: 'Digital Content',
      digitalContent: response,
    };
  }
}
