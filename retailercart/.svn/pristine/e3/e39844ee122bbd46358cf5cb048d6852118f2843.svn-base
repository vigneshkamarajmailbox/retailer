import { BadRequestException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { ContactUsRepository } from './contact-us.repository';
import { CacheService } from 'src/shared/cache.service';

@Injectable()
export class ContactUsService {
  @Inject()
  contactUsRepository: ContactUsRepository;

  @Inject()
  private cacheService: CacheService;

  async findAll(data): Promise<any> {
    const findCompany = await this.contactUsRepository.findCompany(data.cmpCode);

    if (findCompany === null) {
      throw new BadRequestException('Company Code Not Exist');
    }
    const key = this.cacheKeyFormat(data);
    const cache = await this.cacheService.get(key);
    console.log(key, cache, 'CACHED DATA');
    if (cache) {
      console.log('data from Cached');
      return this.validateCache(key, JSON.parse(cache));
      // return {
      //   statusCode: HttpStatus.OK,
      //   message: 'Contact Data',
      //   contactUs: JSON.parse(cache),
      // };
    }
    const contactData = await this.contactUsRepository.findOne(data);
    return this.validateCache(key, contactData);
    // return {
    //   statusCode: HttpStatus.OK,
    //   message: 'Contact Data',
    //   contactUs: contactData,
    // };
  }

  cacheKeyFormat(data) {
    return `contactus-${data.cmpCode}`;
  }

  async validateCache(key: string, response) {
    await this.cacheService.set(key, JSON.stringify(response));
    return {
      statusCode: HttpStatus.OK,
      message: 'Contact Data',
      contactUs: response,
    };
  }
}
