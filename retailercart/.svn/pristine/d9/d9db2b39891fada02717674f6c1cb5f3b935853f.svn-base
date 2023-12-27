import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  CanActivate,
} from "@angular/router";

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    return this.checkLoggedIn(state.url);
  }

  checkLoggedIn(url: string): boolean {
    const status = sessionStorage.getItem("loginStatus");
    const ext = url.substring(url.lastIndexOf("/") + 1);
    if (status !== "success" && url.includes("/landing")) {
      this.router.navigate(["/"]);
      return false;
    }
    return true;
  }
}
