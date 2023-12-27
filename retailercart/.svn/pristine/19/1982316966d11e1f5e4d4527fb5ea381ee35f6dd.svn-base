import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class UserService {
  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
    }),
  };
  constructor(private http: HttpClient) {}

  signup(request) {
    return this.http.post(environment.API_URL + "user/auth/sign-up", request);
  }

  forgotPassword(request) {
    return this.http.post(
      environment.API_URL + "user/auth/forgot/password",
      request
    );
  }

  loginWithPassword(request) {
    return this.http.post(
      environment.API_URL + "user/auth/login/password",
      request
    );
  }

  loginWithOtp(request) {
    return this.http.post(environment.API_URL + "user/auth/sent/otp", request);
  }

  otpVerification(request) {
    return this.http.post(environment.API_URL + "user/auth/login/otp", request);
  }
}
