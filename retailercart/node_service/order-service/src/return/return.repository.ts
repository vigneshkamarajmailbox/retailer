import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateReturnDto } from './dto/create-return.dto';
import { ReturnDetail } from './entities/retrun-details.entity';
import { ReturnHeader } from './entities/return-header.entity';

export class ReturnRepository {
  constructor(
    @InjectRepository(ReturnHeader)
    private returnHeaderRepository: Repository<ReturnHeader>,

    @InjectRepository(ReturnDetail)
    private returnDetailRepository: Repository<ReturnDetail>,
  ) {}

  async create(request: CreateReturnDto, username: string) {
    const returnHeaderData = this.returnHeaderRepository.create(request);
    const headerData = await this.returnHeaderRepository.save(returnHeaderData);

    for (const returnDetail of request.returnItem) {
      console.log(returnDetail);
      const data = {
        returnNo: headerData.returnNo,
        cmpCode: headerData.cmpCode,
        distCode: returnDetail.distCode,
        prodCode: returnDetail.prodCode,
        prodUomCode: returnDetail.prodUomCode,
        // prodQty: returnDetail.prodQty,
        returnQty: returnDetail.returnQty,
        username: username,
      };
      const returnDetailsData = this.returnDetailRepository.create(data);
      this.returnDetailRepository.save(returnDetailsData);
    }
    return returnHeaderData;
  }

  async findOne(cmpCode: string, orderNo: string, username: string) {
    const checkReturn = await this.returnHeaderRepository.findOne({
      where: { cmpCode: cmpCode, orderNo: orderNo, userName: username },
    });
    return checkReturn;
  }
}
