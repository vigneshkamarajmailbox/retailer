import { HttpStatus, Inject, Injectable } from '@nestjs/common';
import { CreateReturnDto } from './dto/create-return.dto';
import { ReturnRepository } from './return.repository';

@Injectable()
export class ReturnService {
  @Inject()
  private returnRepository: ReturnRepository;

  create(createReturnDto: CreateReturnDto, username: string) {
    const checkReturn = this.returnRepository.findOne(
      createReturnDto.cmpCode,
      createReturnDto.orderNo,
      username,
    );

    if (checkReturn) {
      return {
        statusCode: HttpStatus.AMBIGUOUS,
        message: 'Return Already Initiated..!',
      };
    }
    const saveReturn = this.returnRepository.create(createReturnDto, username);
    if (saveReturn) {
      return {
        statusCode: HttpStatus.CREATED,
        message: 'Return Initiated Successfully..!',
      };
    }
  }
}
