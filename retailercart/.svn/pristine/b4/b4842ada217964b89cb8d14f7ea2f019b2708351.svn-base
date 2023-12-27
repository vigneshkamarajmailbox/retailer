import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { plainToInstance } from 'class-transformer';
import { Repository } from 'typeorm';
import { Notification } from './entities/notification.entity';
import { UpdateNotificationDto } from './dto/update-notification.dto';

@Injectable()
export class NotificationService {
  constructor(
    @InjectRepository(Notification)
    private notificationRepository: Repository<Notification>,
  ) {}

  async findAll(cmpCode: string, username: string) {
    const data = await this.notificationRepository.find({
      select: {
        notificationCode: true,
        cmpCode: true,
        title: true,
        desc: true,
        notificationTime: true,
        isSeen: true,
      },
      where: {
        cmpCode: cmpCode,
        username: username,
      },
    });
    const newData = plainToInstance(Notification, data);
    return {
      notification: newData,
    };
  }

  async update(id: string, updateNotificationDto: UpdateNotificationDto, username: string) {
    const response = await this.notificationRepository
      .createQueryBuilder()
      .update(Notification)
      .set({ isSeen: updateNotificationDto.isSeen })
      .where('user_name = :name', { name: username })
      .andWhere(' cmp_code = :cmpCode ', { cmpCode: updateNotificationDto.cmpCode })
      .andWhere('notification_code = :id', { id })
      .execute();
    if (response.affected == 0) {
      return { statusCode: 200, message: 'Notification code not found' };
    }
    return { statusCode: 200, message: 'Notification updated successfully' };
  }
}
