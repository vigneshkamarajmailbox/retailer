import { RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { LoginComponent } from "./pages/login/login.component";
import { HomeComponent } from "./pages/home/home.component";
import { SignupComponent } from "./pages/signup/signup.component";
import { ForgotPasswordComponent } from "./pages/forgot-password/forgot-password.component";
import { AppMainComponent } from "./pages/main/app.main.component";
import { AuthGuard } from "./services/user/auth-guard.service";

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: "",
          component: LoginComponent,
        },
        {
          path: "landing",
          component: AppMainComponent,
          canActivate: [AuthGuard],
          children: [{ path: "", component: HomeComponent }],
        },
        {
          path: "sign-up",
          component: SignupComponent,
        },
        {
          path: "forgot-password",
          component: ForgotPasswordComponent,
        },
      ],
      { scrollPositionRestoration: "enabled" }
    ),
  ],
})
export class AppRoutingModule {}
