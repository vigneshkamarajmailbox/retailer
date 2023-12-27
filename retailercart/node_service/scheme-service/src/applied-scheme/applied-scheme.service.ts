import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { UpdateSchemeDto } from 'src/scheme/dto/update-scheme.dto';
import { SchemeService } from 'src/scheme/scheme.service';
import { Repository } from 'typeorm';
import { CreateAppliedSchemeDto } from './dto/create-applied-scheme.dto';
import { AppliedScheme } from './entities/applied-scheme.entity';

@Injectable()
export class AppliedSchemeService {
  @InjectRepository(AppliedScheme)
  private appliedSchemeRepository: Repository<AppliedScheme>;

  constructor(private readonly schemeService: SchemeService) {}

  async create(createAppliedSchemeDto: CreateAppliedSchemeDto) {
    try {
      const data = this.appliedSchemeRepository.create(createAppliedSchemeDto);
      await this.appliedSchemeRepository.save(data);

      if (!createAppliedSchemeDto.isProductBasedScheme) {
        const updateData: UpdateSchemeDto = {
          cmpCode: data.cmpCode,
          schemeCode: data.schemeCode,
          schemeValue: data.schemeValue,
          distCode: data.distCode,
        };

        await this.schemeService.updateBalanceBudget(updateData);
      }

      return {
        statusCode: HttpStatus.CREATED,
        message: 'Scheme Applied Successfully',
      };
    } catch (error) {
      console.log(error);
      throw new HttpException(
        'Internal Server Error',
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
  }
}
