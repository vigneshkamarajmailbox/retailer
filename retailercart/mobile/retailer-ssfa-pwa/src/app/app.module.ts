import {BrowserModule, HAMMER_GESTURE_CONFIG, HammerGestureConfig, HammerModule} from '@angular/platform-browser';
/* Routing */
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
/* Angular Material */
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AngularMaterialModule} from './angular-material.module';
import {CUSTOM_ELEMENTS_SCHEMA, Injectable, NgModule} from '@angular/core';
/* FormsModule */
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
/* Angular Flex Layout */
import {FlexLayoutModule} from '@angular/flex-layout';
/* Components */
import {LogInComponent} from './components/log-in/log-in.component';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';
import {MatCardModule} from '@angular/material/card';
import {LandingComponent} from './components/landing/landing.component';
import {FieldErrorDisplayComponent} from './components/field-error-display/field-error-display.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {DataService} from './components/service/dataservice';
import {StringConstants} from './components/service/stringconstants';
import {AuthGuard} from './components/log-in/auth-guard.service';
import {CurrencyPipe, HashLocationStrategy, LocationStrategy} from '@angular/common';
import {HeaderComponent} from './components/header/header.component';
import {HomeComponent} from './components/home/home.component';
import {UpdatetoasterComponent} from './components/updatetoaster/updatetoaster.component';
import {DeviceDetectorModule} from 'ngx-device-detector';
import {MatMenuModule} from '@angular/material/menu';
import {MatTabsModule} from '@angular/material/tabs';
import {OrderbookingComponent} from './components/orderbooking/orderbooking.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {PreviousorderreportComponent} from './components/previousorderreport/previousorderreport.component';
import {MatStepperModule} from '@angular/material/stepper';
import {DistributorinfoComponent} from './components/distributorinfo/distributorinfo.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {ConfirmationdialogComponent} from './components/confirmationdialog/confirmationdialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {AgmCoreModule} from '@agm/core';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {DeleteconfirmationComponent} from './components/deleteconfirmation/deleteconfirmation.component';
import {OrderconfirmdialogComponent} from './components/orderconfirmdialog/orderconfirmdialog.component';
import {NgImageSliderModule} from 'ng-image-slider';
import * as Hammer from 'hammerjs';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {MultiTranslateHttpLoader} from 'ngx-translate-multi-http-loader';
import {LanguagedialogComponent} from './components/languagedialog/languagedialog.component';
import {ChangepasswordComponent} from './components/changepassword/changepassword.component';
import { MyprofileComponent } from './components/myprofile/myprofile.component';
import {SchememasterComponent} from "./components/schememaster/schememaster.component";
import {ReferralsalesmanComponent} from './components/referral-salesman/referral-salesman.component';

@Injectable()
export class MyHammerConfig extends HammerGestureConfig {
  overrides = {
    swipe: {direction: Hammer.DIRECTION_ALL},
  } as any;
}

// AoT requires an exported function for factories
export function HttpLoaderFactory(httpClient: HttpClient) {
  return new MultiTranslateHttpLoader(httpClient, [
    {prefix: './assets/i18n/', suffix: '.json'}
  ]);
}

@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    LandingComponent,
    FieldErrorDisplayComponent,
    HeaderComponent,
    HomeComponent,
    UpdatetoasterComponent,
    OrderbookingComponent,
    PreviousorderreportComponent,
    DistributorinfoComponent,
    ConfirmationdialogComponent,
    DeleteconfirmationComponent,
    OrderconfirmdialogComponent,
    LanguagedialogComponent,
    ChangepasswordComponent,
    MyprofileComponent,
    SchememasterComponent,
    ReferralsalesmanComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    FlexLayoutModule,
    ServiceWorkerModule.register('./ngsw-worker.js', {
      enabled: environment.production,
      registrationStrategy: 'registerImmediately'
    }),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCmxdzYju4g-IhmSYfdWb3haYsWpZIqqaY'
    }),
    MatCardModule,
    HttpClientModule,
    DeviceDetectorModule.forRoot(),
    MatMenuModule,
    MatTabsModule,
    HammerModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatButtonToggleModule,
    MatCheckboxModule,
    MatStepperModule,
    MatIconModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatDialogModule,
    NgImageSliderModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [AuthGuard, DataService, StringConstants, {
    provide: LocationStrategy,
    useClass: HashLocationStrategy
  }, CurrencyPipe, MatSnackBar, {
    provide: STEPPER_GLOBAL_OPTIONS,
    useValue: {displayDefaultIndicatorType: false}
  }, {
    provide: HAMMER_GESTURE_CONFIG,
    useClass: MyHammerConfig
  }],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AppModule {

}
