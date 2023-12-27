import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {DataService} from '../service/dataservice';
import {AppComponent} from 'src/app/app.component';
import {User} from '../model/user';
import {StringConstants} from '../service/stringconstants';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {

  changePasswordForm: FormGroup;
  loading = false;
  changePasswordErrMsg = '';
  loginData: User;

  constructor(private fb: FormBuilder, private router: Router, private app: AppComponent,
              private http: HttpClient, private dataService: DataService) {
    app.checkUpdate();

    this.loginData = JSON.parse(localStorage.getItem(StringConstants.CONST_USER));
  }

  ngOnInit(): void {
    this.changePasswordForm = this.fb.group({
      current_password: new FormControl('', Validators.required),
      new_password: new FormControl('', Validators.required),
      confirm_password: new FormControl('', Validators.required)
    });
  }

  onChangePasswordSubmit() {
    this.loading = true;
    const {value: newPassword} = this.changePasswordForm.get('new_password');
    const {value: confirmPassword} = this.changePasswordForm.get('confirm_password');
    const {value: currentPassword} = this.changePasswordForm.get('current_password')
    this.changePasswordErrMsg = '';

    if (currentPassword !== this.loginData.password) {
      this.changePasswordErrMsg = StringConstants.MSG_INVALID_PASSWORD;
      this.loading = false;
    } else if (newPassword !== confirmPassword) {
      this.changePasswordErrMsg = StringConstants.MSG_PASSWORD_MISMATCH;
      this.loading = false;
    } else if (confirmPassword === currentPassword) {
      this.changePasswordErrMsg = StringConstants.MSG_USE_DIFFERENT_PASSWORD;
      this.loading = false;
    } else {
      const user = new User();
      user.loginCode = this.loginData.loginCode;
      user.password = confirmPassword;
      this.changePasswordService(user);
    }
  }

  navigateToHome() {
    const user = new User();
    user.loginCode = this.loginData.loginCode;
    user.password = this.loginData.password;
    this.changePasswordService(user);
  }

  changePasswordService(user: User) {
    const startTime = new Date();
    this.http.post(localStorage.getItem(StringConstants.EXTERNAL_API_URL) + StringConstants.URL_CHANGE_PASSWORD,
      JSON.stringify(user), {
        headers: new HttpHeaders({
          'Content-Type': StringConstants.CONTENT_TYPE_JSON
        }), observe: StringConstants.OBSERVER_RESPONSE
      }).subscribe(resp => {
      if (resp.status === 200) {
        this.app.openSnackBar(StringConstants.MSG_CHANGE_PASSWORD_SUCCESS);
        this.dataService.setUser(this.loginData);
        this.router.navigate([StringConstants.REDIRECT_LANDING_HOME]);
        this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_CHANGE_PASSWORD, startTime, StringConstants.CONST_SUCCESS);
        this.loading = false;
      }
    }, (err: HttpErrorResponse) => {
      console.error(err.message);
      this.changePasswordErrMsg = StringConstants.MSG_CHANGE_PASSWORD_FAILED;
      this.dataService.uploadSyncLog(StringConstants.CONST_PROCESS_CHANGE_PASSWORD, startTime, StringConstants.CONST_FAILURE);
      this.loading = false;
    });
  }

  changePasswordMsg() {
    return this.changePasswordErrMsg !== '';
  }
}
