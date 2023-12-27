import { HashLocationStrategy, LocationStrategy } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AppRoutingModule } from "./app-routing.module";
import { RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
import { HomeComponent } from "./pages/home/home.component";
import { SignupComponent } from "./pages/signup/signup.component";
import { ForgotPasswordComponent } from "./pages/forgot-password/forgot-password.component";
import { LoginComponent } from "./pages/login/login.component";
import { AppMainComponent } from "./pages/main/app.main.component";
import { MenuService } from "./service/app.menu.service";
import { CommonSharedModule } from "./shared/common-shared.module";
import { AppFooterComponent } from "./view/footer/app.footer.component";
import { AppTopBarComponent } from "./view/top-bar/app.topbar.component";
import { AuthGuard } from "./services/user/auth-guard.service";

@NgModule({
  imports: [
    RouterModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CommonSharedModule,
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    AppMainComponent,
    AppFooterComponent,
    AppTopBarComponent,
    HomeComponent,
    SignupComponent,
    ForgotPasswordComponent,
  ],
  providers: [
    AuthGuard,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    MenuService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
