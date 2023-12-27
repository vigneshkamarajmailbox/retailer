import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Feedback } from './entities/feedback.entity';
import { CreateFeedbackDto } from './dto/create-feedback.dto';
import { Company } from 'src/shared/entities/company.entity';

export class FeedbackRepository {
  @InjectRepository(Feedback)
  private feedbackRepository: Repository<Feedback>;

  @InjectRepository(Company)
  private companyRepo: Repository<Company>;

  async create(saveFeedback: CreateFeedbackDto): Promise<any> {
    const data = this.feedbackRepository.create(saveFeedback);
    return await this.feedbackRepository.save(data);
  }

  async findAll(userName: string, cmpCode: string) {
    return await this.feedbackRepository.find({
      select: { userName: true, title: true, comments: true, imagePath: true },
      where: { userName: userName, cmpCode: cmpCode },
      cache: 60000,
      relations: { user: false },
    });
  }

  async findCompany(cmpCode: string) {
    return await this.companyRepo.findOne({
      where: { cmpCode: cmpCode },
    });
  }
}
