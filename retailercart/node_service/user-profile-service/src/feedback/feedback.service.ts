import { BadRequestException, HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateFeedbackDto } from './dto/create-feedback.dto';
import { FeedbackRepository } from './feedback.repository';
import { CacheService } from 'src/shared/cache.service';

@Injectable()
export class FeedbackService {
  @Inject()
  feedbackRepository: FeedbackRepository;

  @Inject()
  private cacheService: CacheService;

  async create(saveFeedBack: CreateFeedbackDto, userName: string) {
    const saveInfo: CreateFeedbackDto = {
      userName: userName,
      cmpCode: saveFeedBack.cmpCode,
      title: saveFeedBack.title,
      comments: saveFeedBack.comments,
      imagePath: saveFeedBack.imagePath,
    };

    const find = await this.feedbackRepository.create(saveInfo);
    if (!find) {
      throw new HttpException('Details not saved', HttpStatus.BAD_REQUEST);
    } else {
      return {
        statusCode: HttpStatus.CREATED,
        message: 'FeedBack Saved Successfully',
      };
    }
  }

  async findAll(userName: string, cmpCode: string): Promise<any> {
    const findCompany = await this.feedbackRepository.findCompany(cmpCode);

    if (findCompany === null) {
      throw new BadRequestException('Company Code Not Exist');
    }

    const key = this.cacheKeyFormat(userName, cmpCode);
    const cache = await this.cacheService.get(key);
    console.log(key, cache, 'CACHED DATA');
    if (cache) {
      console.log('data from Cached');
      this.validateCache(key, JSON.parse(cache));
    }
    const feedbackData = await this.feedbackRepository.findAll(userName, cmpCode);
    return this.validateCache(key, feedbackData);

    // {
    //   statusCode: HttpStatus.OK,
    //   message: 'FeedBack Data',
    //   feedback: feedbackData,
    // };
  }

  cacheKeyFormat(userName: string, cmpCode: string) {
    return `feedback-${userName}-${cmpCode}`;
  }

  async validateCache(key: string, response) {
    await this.cacheService.set(key, JSON.stringify(response));
    return { statusCode: HttpStatus.OK, message: 'FeedBack Data', feedback: response };
  }
}
