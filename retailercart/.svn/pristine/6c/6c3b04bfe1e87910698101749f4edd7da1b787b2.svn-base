import { HttpErrorResponse } from "@angular/common/http";
import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { UserService } from "src/app/services/user/user.service";

@Component({
  selector: "app-forgot-password",
  templateUrl: "./forgot-password.component.html",
  styleUrls: ["./forgot-password.component.scss"],
  providers: [MessageService],
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
    });
  }
  onSubmit(): void {
    if (this.forgotPasswordForm.valid) {
      const formData = this.forgotPasswordForm.value;
      this.userService.forgotPassword(formData).subscribe({
        next: (res: any) => {
          console.log(res);
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "New Password Sent  Successfully to your Registered email",
          });
          this.Login();
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
        },
      });
    } else {
      this.forgotPasswordForm.markAllAsTouched();
    }
  }

  Login() {
    console.log("working");
    this.forgotPasswordForm.reset();
    this.router.navigate(["/"]);
  }
}
