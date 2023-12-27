import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { GeoHierarchyLevel } from './entities/geo-hierarchy-level.entity';

@Injectable()
export class LocationService {
  constructor(
    @InjectRepository(GeoHierarchyLevel)
    private geoLevelRepository: Repository<GeoHierarchyLevel>,
  ) {}

  async findAllCountry() {
    const countrList = await this.geoLevelRepository
      .createQueryBuilder('geohierLev')
      .leftJoinAndSelect('geohierLev.geoHierValue', 'geoHierVal')
      .select([
        'geoHierVal.value_code as code',
        'geoHierVal.value_name as name',
      ])
      .where('geohierLev.level_name = :levelName', { levelName: 'Country' })
      .getRawMany();

    return {
      statusCode: HttpStatus.OK,
      message: 'Country list',
      countryList: countrList,
    };
  }

  async findAllState(countryCode = '') {
    let stateList = [];
    if (countryCode) {
      stateList = await this.geoLevelRepository
        .createQueryBuilder('geohierLev')
        .leftJoinAndSelect('geohierLev.geoHierValue', 'geoHierVal')
        .select([
          'geoHierVal.value_code as code',
          'geoHierVal.value_name as name',
        ])
        .where('geohierLev.level_name = :levelName', { levelName: 'State' })
        .andWhere('geoHierVal.level_1_code = :countryCode', {
          countryCode: countryCode,
        })
        .getRawMany();
    } else {
      stateList = await this.geoLevelRepository
        .createQueryBuilder('geohierLev')
        .leftJoinAndSelect('geohierLev.geoHierValue', 'geoHierVal')
        .select([
          'geoHierVal.value_code as code',
          'geoHierVal.value_name as name',
        ])
        .where('geohierLev.level_name = :levelName', { levelName: 'State' })
        .getRawMany();
    }

    return {
      statusCode: HttpStatus.OK,
      message: 'State list',
      stateList: stateList,
    };
  }

  async findAllCity(stateCode = '') {
    let cityList = [];
    if (stateCode) {
      cityList = await this.geoLevelRepository
        .createQueryBuilder('geohierLev')
        .leftJoinAndSelect('geohierLev.geoHierValue', 'geoHierVal')
        .select([
          'geoHierVal.value_code as code',
          'geoHierVal.value_name as name',
        ])
        .where('geohierLev.level_name = :levelName', { levelName: 'City' })
        .andWhere('geoHierVal.level_3_code = :stateCode', {
          stateCode: stateCode,
        })
        .getRawMany();
    } else {
      cityList = await this.geoLevelRepository
        .createQueryBuilder('geohierLev')
        .leftJoinAndSelect('geohierLev.geoHierValue', 'geoHierVal')
        .select([
          'geoHierVal.value_code as code',
          'geoHierVal.value_name as name',
        ])
        .where('geohierLev.level_name = :levelName', { levelName: 'City' })
        .getRawMany();
    }

    return {
      statusCode: HttpStatus.OK,
      message: 'City list',
      cityList: cityList,
      length: cityList.length,
    };
  }
}
