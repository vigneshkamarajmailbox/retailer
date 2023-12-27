import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty } from 'class-validator';
import { IsCompanyExists } from 'src/common/validation/decorators/company-validation.decorator';
import { SignupDto } from 'src/users/dto/signup.dto';

export class RetailerQueryInput {
  cmpCode: string;
  userName: string;
  retailerCode: string;
  retailerName: string;
  mobileNo: number;
  altMobileNo: string;
  email: string;
  gstNo: string;
  panNo: string;
  groupCode: string;

  constructor(signupDto: SignupDto) {
    this.cmpCode = 'COMP';
    this.userName = signupDto.userName;
    this.retailerCode = 'RETAIL001';
    this.retailerName = 'Test';
    this.mobileNo = signupDto.mobileNo;
    this.altMobileNo = '';
    this.email = signupDto.email;
    this.gstNo = 'ABSSSESSSS';
    this.panNo = 'KSHUDOJD';
    this.groupCode = 'UGROUP001';
  }
}

export class QueryCompanyInput {
  @ApiProperty({ example: 'COMP', required: true })
  @IsNotEmpty()
  @IsCompanyExists()
  cmpCode: string;
}
