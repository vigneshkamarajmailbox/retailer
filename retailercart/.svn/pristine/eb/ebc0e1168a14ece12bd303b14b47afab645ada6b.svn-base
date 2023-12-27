import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {StringConstants} from '../service/stringconstants';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.checkLoggedIn(state.url);
  }

  checkLoggedIn(url: string): boolean {
    const status = localStorage.getItem(StringConstants.CONST_LOGIN_STATUS);
    if (status !== StringConstants.CONST_SUCCESS && url.includes('/landing')
      && !url.includes(StringConstants.ASSETS_SESSION_EXPIRED)
      && !url.includes(StringConstants.ASSETS_ACCESS)) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
