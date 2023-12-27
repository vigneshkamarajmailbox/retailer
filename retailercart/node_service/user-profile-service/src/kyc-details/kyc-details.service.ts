import { HttpService } from '@nestjs/axios';
import { HttpException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { MasterCommunicatorService } from 'src/communicators/master/master.service';
import { CreateKycDetailDto } from './dto/create-kyc-detail.dto';
import { KycRepository } from './kyc-details.repository';
import { AwsService } from 'src/common/helper/aws-service';
import { CacheService } from 'src/shared/cache.service';

@Injectable()
export class KycDetailsService {
  constructor(private readonly masterService: MasterCommunicatorService) {}

  @Inject(ConfigService)
  private readonly config: ConfigService;

  @Inject()
  private readonly httpService: HttpService;

  @Inject()
  kycDetailsRepository: KycRepository;

  @Inject()
  private readonly awsService: AwsService;

  @Inject()
  private cacheService: CacheService;

  async create(saveKycDetailDto: CreateKycDetailDto, userName: string) {
    const kycInfo = await this.kycDetailsRepository.findOne(userName, saveKycDetailDto.cmpCode);
    if (kycInfo) {
      return { statusCode: HttpStatus.AMBIGUOUS, message: 'User Already Done Kyc!' };
    }
    const saveKyc: CreateKycDetailDto = {
      userName: userName,
      cmpCode: saveKycDetailDto.cmpCode,
      accountNo: saveKycDetailDto.accountNo,
      accountName: saveKycDetailDto.accountName,
      bankName: saveKycDetailDto.bankName,
      bankBranch: saveKycDetailDto.bankBranch,
      ifsc: saveKycDetailDto.ifsc,
      email: saveKycDetailDto.email,
      documentImage: saveKycDetailDto.documentImage,
      documentType: saveKycDetailDto.documentType,
    };
    const find = await this.kycDetailsRepository.createKycDetail(saveKyc);
    if (!find) {
      throw new HttpException('KYC not saved', HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return {
        statusCode: HttpStatus.CREATED,
        message: 'KYC Saved Successfully',
      };
    }
  }

  async findOne(userName: string, cmpCode: string) {
    const key = this.cacheKeyFormat(userName, cmpCode);
    const cache = await this.cacheService.get(key);
    console.log(key, cache, 'CACHED DATA');
    if (cache) {
      console.log('data from Cached');
      this.validateCache(key, JSON.parse(cache));
      // return {
      //   statusCode: HttpStatus.OK,
      //   message: 'Kyc Details',
      //   kycDetails: JSON.stringify(cache),
      // };
    }
    const kycDetails = await this.kycDetailsRepository.findOne(userName, cmpCode);

    let Details;
    if (kycDetails) {
      const kycDocType = await this.masterService.getDocType(kycDetails.documentType);

      if (kycDocType.isError) {
        return {
          statusCode: HttpStatus.NO_CONTENT,
          message: 'Kyc Type not getting. Try Again!',
        };
      }
      Details = {
        accountNo: kycDetails.accountNo,
        accountName: kycDetails.accountName,
        bankName: kycDetails.bankName,
        bankBranch: kycDetails.bankBranch,
        ifsc: kycDetails.ifsc,
        mailId: kycDetails.email,
        documentImage: kycDetails.documentImage,
        documentType: kycDocType.data.documentType,
      };
      return this.validateCache(key, Details);
      // return {
      //   statusCode: HttpStatus.OK,
      //   message: 'Kyc Details',
      //   kycDetails: {
      //     accountNo: kycDetails.accountNo,
      //     accountName: kycDetails.accountName,
      //     bankName: kycDetails.bankName,
      //     bankBranch: kycDetails.bankBranch,
      //     ifsc: kycDetails.ifsc,
      //     mailId: kycDetails.email,
      //     documentImage: kycDetails.documentImage,
      //     documentType: kycDocType.data.documentType,
      //   },
      // };
    } else {
      Details = kycDetails;
      return this.validateCache(key, Details);
      // return {
      //   statusCode: HttpStatus.OK,
      //   message: 'Kyc Details',
      //   kycDetails: Details,
      // };
    }
  }

  async uploadFile(filename: string, buffer: Buffer, contentType: string) {
    const data = await this.awsService.uploadFile(String(filename), buffer, contentType);
    return {
      statusCode: HttpStatus.CREATED,
      message: 'Upload Successfully',
      data: {
        url: data.Location,
      },
    };
  }

  cacheKeyFormat(userName, cmpCode) {
    return `kycdetails-${userName}-${cmpCode}`;
  }

  async validateCache(key: string, response) {
    await this.cacheService.set(key, JSON.stringify(response));
    return {
      statusCode: HttpStatus.OK,
      message: 'Kyc Details',
      kycDetails: response,
    };
  }
}
