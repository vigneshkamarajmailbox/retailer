import { BadRequestException, HttpStatus, Inject, Injectable } from '@nestjs/common';
import { Retailer } from './entities/retailer.entity';
import { RetailerRepository } from './retailer.repository';

@Injectable()
export class RetailerService {
  @Inject()
  retailerRepository: RetailerRepository;

  async findOne(userName: string, cmpCode: string): Promise<any> {
    const profileData = await this.retailerRepository.findOne(userName, cmpCode);
    if (profileData === null) {
      throw new BadRequestException('Retailer Not Exist');
    }
    let credit = {};
    if (profileData?.credit?.length) {
      credit = profileData?.credit[0];
    }
    const returnData = {
      cmpCode: profileData.cmpCode,
      retailerName: profileData.retailerName,
      mobileNo: profileData.mobileNo,
      altMobileNo: profileData.altMobileNo,
      email: profileData.email,
      gstNo: profileData.gstNo,
      panNo: profileData.panNo,
      credit: credit,
      address: profileData.address,
    };
    return {
      statusCode: HttpStatus.OK,
      message: 'Profile Data',
      profile: returnData,
    };
  }

  async retailerInfo(username: any, cmpCode: string) {
    const findCompany = await this.retailerRepository.findCompany(cmpCode);

    if (findCompany === null) {
      throw new BadRequestException('Company Code Not Exist');
    }

    const retailerInfo: Retailer = await this.retailerRepository.retailerDetails(username, cmpCode);
    console.log(retailerInfo);
    const distributorList: string[] = [];
    const distrSalesHier: string[] = [];
    const geoList: string[] = [];
    retailerInfo?.retailerDistributorMapping?.forEach((distributor) => {
      distributorList.push(distributor.distrCode);
      distributor.distributor.distributorSalesHier.forEach((dsh) => {
        distrSalesHier.push(...dsh.salesHierPath.split('/'));
        distrSalesHier.push(dsh.salesForceCode);
      });
    });
    retailerInfo?.retailerRouteMapping.forEach((route) => {
      geoList.push(route.geoCode);
    });

    if (retailerInfo?.retailerCode) {
      return {
        salesHierCodeList: distrSalesHier,
        distributorList,
        retailerCode: retailerInfo?.retailerCode,
        retailerCategory: retailerInfo?.retailerClass,
        geoList,
      };
    } else {
      return { statusCode: 200, message: 'Data not found' };
    }
  }
}
