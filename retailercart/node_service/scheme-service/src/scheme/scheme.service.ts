import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { SchemeDistributor } from 'src/scheme-distributor/entities/scheme-distributor.entity';
import { SchemeHierTypeValue } from 'src/shared/enum/table-value.enum';
import { Brackets, Index, Repository } from 'typeorm';
import {
  FetchAllSchemePayload,
  FetchBySchemeCodePayload,
} from './dto/request-payload.dto';
import { UpdateSchemeDto } from './dto/update-scheme.dto';
import { Scheme } from './entities/scheme.entity';

@Injectable()
export class SchemeService {
  constructor(
    @InjectRepository(Scheme)
    private schemeRepository: Repository<Scheme>,
    @InjectRepository(SchemeDistributor)
    private schemeDistributorRepository: Repository<SchemeDistributor>,
  ) {}

  async findAll(data: FetchAllSchemePayload) {
    const schemeList = await this.schemeRepository
      .createQueryBuilder('scheme')
      .leftJoinAndSelect('scheme.schemeAttribute', 'schemeAttribute')
      .leftJoinAndSelect('scheme.distributorData', 'schemeDistributor')
      .leftJoinAndSelect('schemeAttribute.hierType', 'hierType')
      .leftJoinAndSelect('scheme.slapData', 'slap')
      .leftJoinAndSelect('scheme.schemeRetailerCategory', 'retailerChannel')
      .select([
        'scheme.sch_code as schemeCode',
        'scheme.sch_name as schemeName',
        'scheme.sch_type as schemeType',
        'scheme.apply_before_tax as applyBeforeTax',
        'scheme.valid_from as validFrom',
        'scheme.valid_to as validTo',
        'schemeDistributor.dist_code as distributorCode',
        'schemeDistributor.budget as budget',
        'schemeDistributor.balance_budget as balanceBudget',
        'schemeAttribute.attr_value as atrributeValue',
        'hierType.name as schemeHierTypeName',
        'hierType.code as schemeHierTypeCode',
        'slap.slap_no as slapCode',
        'slap.slap_desc as slapDescription',
        'slap.slap_from as slapFrom',
        'slap.slap_to as slapTo',
        'slap.flat_amount as flatAmount',
        'slap.discount as discount',
        'slap.free_qty as freeQty',
        'slap.prod_code as freeProductCode',
        'slap.batch_code as freeProductBatchCode',
      ])
      .where('scheme.valid_from <= :validFrom', {
        validFrom: data.currentDate,
      })
      .andWhere('scheme.valid_to >= :validDate', {
        validDate: data.currentDate,
      })
      .andWhere('scheme.cmp_code >= :cmpCode', {
        cmpCode: data.cmpCode,
      })
      .andWhere(
        new Brackets((qb) => {
          if (data.distCode.length > 0) {
            qb.where(
              'schemeDistributor.dist_code IN (:distCode) AND schemeDistributor.is_retailer_budget =:retailerBudget AND schemeDistributor.balance_budget > :balanceBudget AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
              {
                distCode: data.distCode,
                retailerBudget: 0,
                balanceBudget: 0,
                productQty: data.productQty,
                unlimited: 0,
              },
            );
          }
          if (
            data.retailerChannel.channelCode != '' &&
            data.retailerChannel.groupCode != '' &&
            data.retailerChannel.classCode != ''
          ) {
            qb.orWhere(
              'retailerChannel.channel_code =:channelCode AND retailerChannel.group_code =:groupCode AND retailerChannel.class_code =:classCode AND schemeDistributor.balance_budget > :balanceBudget AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
              {
                channelCode: data.retailerChannel.channelCode,
                groupCode: data.retailerChannel.groupCode,
                classCode: data.retailerChannel.classCode,
                balanceBudget: 0,
                productQty: data.productQty,
                unlimited: 0,
              },
            );
          }
          if (data.retailerCode.length > 0) {
            qb.orWhere(
              'schemeAttribute.attr_type =:attrType AND schemeAttribute.attr_value IN (:retailerCode) AND schemeDistributor.balance_budget > :balanceBudget AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
              {
                attrType: SchemeHierTypeValue.ALL_RETAILERS,
                retailerCode: data.retailerCode,
                balanceBudget: 0,
                productQty: data.productQty,
                unlimited: 0,
              },
            );
          }
          if (data.productQty > 0) {
            if (
              data.productCode.length > 0 &&
              data.productHier.levelValue.length > 0
            ) {
              qb.orWhere(
                'schemeAttribute.attr_type =:attrType AND (schemeAttribute.attr_value IN (:attrProdValue) OR schemeAttribute.attr_value IN (:attrProdHierValue)) AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
                {
                  attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                  attrProdValue: data.productCode,
                  attrProdHierValue: data.productHier.levelValue,
                  productQty: data.productQty,
                  unlimited: 0,
                },
              );
            } else {
              if (data.productCode.length > 0) {
                qb.orWhere(
                  'schemeAttribute.attr_type =:attrType AND schemeAttribute.attr_value IN (:productCode) AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
                  {
                    attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                    productCode: data.productCode,
                    productQty: data.productQty,
                    unlimited: 0,
                  },
                );
              }
              if (data.productHier.levelValue.length > 0) {
                qb.orWhere(
                  'schemeAttribute.attr_type = :attrType AND schemeAttribute.attr_value IN (:attrValue) AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
                  {
                    attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                    attrValue: data.productHier.levelValue,
                    productQty: data.productQty,
                    unlimited: 0,
                  },
                );
              }
            }
          } else {
            if (
              data.productCode.length > 0 &&
              data.productHier.levelValue.length > 0
            ) {
              qb.orWhere(
                'schemeAttribute.attr_type =:attrType  AND (schemeAttribute.attr_value IN (:attrProdValue) OR schemeAttribute.attr_value IN (:attrProdHierValue))',
                {
                  attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                  attrProdValue: data.productCode,
                  attrProdHierValue: data.productHier.levelValue,
                },
              );
            } else {
              if (data.productCode.length > 0) {
                qb.orWhere(
                  'schemeAttribute.attr_type =:attrType AND schemeAttribute.attr_value IN (:productCode)',
                  {
                    attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                    productCode: data.productCode,
                  },
                );
              } else {
                qb.orWhere('schemeAttribute.attr_type =:attrType', {
                  attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                });
              }
              if (data.productHier.levelValue.length > 0) {
                qb.orWhere(
                  'schemeAttribute.attr_type = :attrType AND schemeAttribute.attr_value IN (:attrValue)',
                  {
                    attrType: SchemeHierTypeValue.ALL_PRODUCTS,
                    attrValue: data.productHier.levelValue,
                  },
                );
              }
            }
          }
        }),
      )
      .orWhere(
        'schemeAttribute.attr_value =:allCategory AND scheme.valid_from <= :validFrom AND scheme.valid_to >= :validDate AND scheme.cmp_code >= :cmpCode AND slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
        {
          allCategory: 'N',
          validFrom: data.currentDate,
          validDate: data.currentDate,
          cmpCode: data.cmpCode,
          productQty: data.productQty,
          unlimited: 0,
        },
      )
      .getRawMany();

    return {
      statusCode: HttpStatus.OK,
      message: 'Scheme List',
      scheme: schemeList,
      length: schemeList.length,
    };
  }

  async updateBalanceBudget(updateData: UpdateSchemeDto) {
    const schemeData = await this.schemeDistributorRepository.findOne({
      where: {
        cmpCode: updateData.cmpCode,
        schemeCode: updateData.schemeCode,
        distCode: updateData.distCode,
      },
    });
    const schemeBalanceBudget =
      Number(schemeData.balanceBudget) - Number(updateData.schemeValue);
    return await this.schemeDistributorRepository.update(
      {
        cmpCode: updateData.cmpCode,
        schemeCode: updateData.schemeCode,
        distCode: updateData.distCode,
      },
      { balanceBudget: String(schemeBalanceBudget) },
    );
  }

  async findAllBySchemeCode(data: FetchBySchemeCodePayload) {
    const schemeList = await this.schemeRepository
      .createQueryBuilder('scheme')
      .leftJoinAndSelect('scheme.schemeAttribute', 'schemeAttribute')
      .leftJoinAndSelect('scheme.distributorData', 'schemeDistributor')
      .leftJoinAndSelect('schemeAttribute.hierType', 'hierType')
      .leftJoinAndSelect('scheme.slapData', 'slap')
      .leftJoinAndSelect('scheme.schemeRetailerCategory', 'retailerChannel')
      .select([
        'scheme.sch_code as schemeCode',
        'scheme.sch_name as schemeName',
        'scheme.sch_type as schemeType',
        'scheme.apply_before_tax as applyBeforeTax',
        'scheme.valid_from as validFrom',
        'scheme.valid_to as validTo',
        'schemeDistributor.dist_code as distributorCode',
        'schemeDistributor.budget as budget',
        'schemeDistributor.balance_budget as balanceBudget',
        'schemeAttribute.attr_value as atrributeValue',
        'hierType.name as schemeHierTypeName',
        'hierType.code as schemeHierTypeCode',
        'slap.slap_no as slapCode',
        'slap.slap_desc as slapDescription',
        'slap.slap_from as slapFrom',
        'slap.slap_to as slapTo',
        'slap.flat_amount as flatAmount',
        'slap.discount as discount',
        'slap.free_qty as freeQty',
        'slap.prod_code as freeProductCode',
        'slap.batch_code as freeProductBatchCode',
      ])
      .where('scheme.valid_from <= :validFrom', {
        validFrom: data.currentDate,
      })
      .andWhere('scheme.valid_to >= :validDate', {
        validDate: data.currentDate,
      })
      .andWhere('scheme.cmp_code >= :cmpCode', {
        cmpCode: data.cmpCode,
      })
      .andWhere(
        'slap.slap_from <= :productQty AND (slap.slap_to >= :productQty OR slap.slap_to =:unlimited)',
        {
          productQty: data.productQty,
          unlimited: 0,
        },
      )
      .andWhere(
        'scheme.sch_code IN (:schemeCode) AND slap.slap_no IN (:slapData)',
        {
          schemeCode: data.schemeCode,
          slapData: data.slapCode,
        },
      )
      .getRawMany();

    if (data.distCode.length > 0) {
      schemeList.forEach(async (element, Index) => {
        if (element.distributorCode != null) {
          if (!data.distCode.includes(element.distributorCode)) {
            schemeList.splice(Index, Index + 1);
          }
        }
      });
    }

    return {
      statusCode: HttpStatus.OK,
      message: 'Scheme List',
      scheme: schemeList,
      length: schemeList.length,
    };
  }
}
