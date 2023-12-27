import { HttpErrorResponse } from "@angular/common/http";
import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { UserService } from "src/app/services/user/user.service";

@Component({
  selector: "app-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"],
  providers: [MessageService],
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.signupForm = this.formBuilder.group({
      userName: ["", Validators.required],
      password: ["", Validators.required],
      mobileNo: ["", Validators.required],
      email: ["", [Validators.required, Validators.email]],
      gst: [
        "",
        [
          Validators.required,
          Validators.pattern(
            "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$"
          ),
        ],
      ],
      pan: [
        "",
        [Validators.required, Validators.pattern("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")],
      ],
      postalCode: ["", [Validators.required]],
    });
  }
  onSubmit(): void {
    if (this.signupForm.valid) {
      const formData = this.signupForm.value;
      console.log("Form submitted:", formData);
      this.userService.signup(formData).subscribe({
        next: (res: any) => {
          console.log(res);
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: res.message,
          });
          this.Login();
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
        },
      });
    } else {
      this.signupForm.markAllAsTouched();
    }
  }

  Login() {
    console.log("working");
    this.signupForm.reset();
    this.router.navigate(["/"]);
  }
}
