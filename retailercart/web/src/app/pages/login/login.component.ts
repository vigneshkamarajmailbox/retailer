import { HttpErrorResponse } from "@angular/common/http";
import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { RadioButtonModule } from "primeng/radiobutton";
import { UserService } from "src/app/services/user/user.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
  providers: [MessageService],
})
export class LoginComponent {
  loginForm: FormGroup;
  loginWithOtpForm: FormGroup;
  otpVerificationForm: FormGroup;
  otpVerification: boolean = false;

  loginType: string = "Password";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userName: ["", Validators.required],
      password: ["", Validators.required],
    });

    this.loginWithOtpForm = this.formBuilder.group({
      mobileNo: ["", Validators.required],
    });

    this.otpVerificationForm = this.formBuilder.group({
      otp: ["", Validators.required],
    });
  }

  loginTypeChange() {
    if (this.loginType == "Password") {
      this.loginWithOtpForm.reset();
    } else {
      this.loginForm.reset();
    }
  }

  signUP() {
    console.log("working");
    this.loginForm.reset();
    this.loginWithOtpForm.reset();
    this.router.navigate(["/sign-up"]);
  }

  onSubmit() {
    if (this.loginType == "Password") {
      if (this.loginForm.valid) {
        const formData = this.loginForm.value;
        this.userService.loginWithPassword(formData).subscribe({
          next: async (res: any) => {
            console.log(res);
            await this.messageService.add({
              severity: "success",
              summary: "Success",
              detail: "Successfully Loged In",
            });
            sessionStorage.setItem("accessToken", res.accessToken);
            sessionStorage.setItem("loginStatus", "success");
            this.home();
          },
          error: (err: HttpErrorResponse) => {
            console.log(err);
          },
        });
      } else {
        this.loginForm.markAllAsTouched();
      }
    } else {
      if (this.loginWithOtpForm.valid) {
        const formData = this.loginWithOtpForm.value;
        this.userService.loginWithOtp(formData).subscribe({
          next: (res: any) => {
            console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Success",
              detail: res.message,
            });
            this.otpVerification = true;
          },
          error: (err: HttpErrorResponse) => {
            console.log(err);
          },
        });
      } else {
        this.loginWithOtpForm.markAllAsTouched();
      }
    }
  }

  home() {
    this.loginForm.reset();
    this.loginWithOtpForm.reset();
    this.otpVerificationForm.reset();
    this.router.navigate(["/landing"]);
  }

  forgotpassword() {
    this.router.navigate(["/forgot-password"]);
  }

  onOtpSubmit() {
    if (this.otpVerificationForm.valid) {
      const formData = this.otpVerificationForm.value;
      formData.mobileNo = this.loginWithOtpForm.get("mobileNo").value;
      this.userService.otpVerification(formData).subscribe({
        next: (res: any) => {
          console.log(res);
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "OTP Verified Successfully",
          });
          sessionStorage.setItem("accessToken", res.accessToken);
          sessionStorage.setItem("loginStatus", "success");
          this.home();
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
        },
      });
    } else {
      this.otpVerificationForm.markAllAsTouched();
    }
  }

  resentOtp() {
    // const request = {
    //   mobileNo : this.loginWithOtpForm.get("mobileNo").value,
    // }
    this.onSubmit();
  }
}
