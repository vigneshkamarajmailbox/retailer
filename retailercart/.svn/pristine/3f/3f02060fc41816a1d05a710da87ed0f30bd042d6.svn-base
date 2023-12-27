import { Body, Controller, HttpCode, HttpStatus, Post, Request, UseGuards } from '@nestjs/common';
import { LoginOTP } from 'src/users/dto/login-with-otp.dto';
import { UserOtpDto } from 'src/users/dto/otp.dto';
import { SignupDto } from 'src/users/dto/signup.dto';
import { UserDto } from 'src/users/dto/user.dto';
import { AuthService } from './auth.service';
import { Public } from './decorators/public.decorator';
import { ForgotPassDto } from 'src/users/dto/forgot-password.dto';
import { CompanySelection } from './dto/company-selection.dto';
import { ApiBearerAuth } from '@nestjs/swagger';
import { AuthGuard } from 'src/auth/auth.guard';

@Controller('auth')
export class AuthController {
  constructor(private authService: AuthService) {}

  @Public()
  @Post('sign-up')
  async signUp(@Body() signup: SignupDto) {
    return await this.authService.signup(signup);
  }

  @Public()
  @HttpCode(HttpStatus.OK)
  @Post('login/otp')
  async loginWithOTP(@Body() signInDto: UserOtpDto) {
    return await this.authService.loginWithOTP(signInDto.mobileNo, signInDto.otp);
  }

  @Public()
  @HttpCode(HttpStatus.OK)
  @Post('login/password')
  async loginWithPassword(@Body() signInDto: UserDto) {
    return await this.authService.signIn(signInDto.userName, signInDto.password);
  }

  @Public()
  @HttpCode(HttpStatus.OK)
  @Post('sent/otp')
  async sentOTP(@Body() UserOtpDto: LoginOTP) {
    return await this.authService.sentOTP(UserOtpDto.mobileNo);
  }

  @Public()
  @HttpCode(HttpStatus.OK)
  @Post('forgot/password')
  async forgotPassword(@Body() forgotPassDto: ForgotPassDto) {
    return await this.authService.forgotPassword(forgotPassDto.email);
  }

  @ApiBearerAuth('access-token')
  @UseGuards(AuthGuard)
  @Post('company/selection')
  async companySelection(@Body() company: CompanySelection, @Request() req: any) {
    return await this.authService.companySelection(company?.company[0], req?.user?.groupCode);
  }
}
