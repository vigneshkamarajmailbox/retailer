import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LogInComponent} from './components/log-in/log-in.component';
import {LandingComponent} from './components/landing/landing.component';
import {AuthGuard} from './components/log-in/auth-guard.service';
import {HomeComponent} from './components/home/home.component';
import {OrderbookingComponent} from './components/orderbooking/orderbooking.component';
import {PreviousorderreportComponent} from './components/previousorderreport/previousorderreport.component';
import {DistributorinfoComponent} from './components/distributorinfo/distributorinfo.component';
import {ChangepasswordComponent} from './components/changepassword/changepassword.component';
import {MyprofileComponent} from './components/myprofile/myprofile.component';
import {SchememasterComponent} from "./components/schememaster/schememaster.component";
import {ReferralsalesmanComponent} from './components/referral-salesman/referral-salesman.component';

const routes: Routes = [
  {
    path: '',
    component: LogInComponent
  }, {
    path: 'landing',
    component: LandingComponent,
    canActivate: [AuthGuard],
    children: [{
      path: 'home',
      component: HomeComponent,
      pathMatch: 'full'
    }, {
      path: 'order',
      component: OrderbookingComponent,
      pathMatch: 'full'
    }, {
      path: 'previousorder',
      component: PreviousorderreportComponent,
      pathMatch: 'full'
    }, {
      path: 'distributor',
      component: DistributorinfoComponent,
      pathMatch: 'full'
    }, {
      path: 'changepassword',
      component: ChangepasswordComponent,
      pathMatch: 'full'
    }, {
      path: 'myprofile',
      component: MyprofileComponent,
      pathMatch: 'full'
    },
    {
      path: 'scheme',
      component: SchememasterComponent,
      pathMatch: 'full'
    },
    {
      path: 'referral-salesman',
      component: ReferralsalesmanComponent,
      pathMatch: 'full'
    }]
  }, {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
