import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { StorangeService } from './storange.service';


@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  isloggedIn : boolean = false;

  roles: any[] = [];

  constructor(private storageService:StorangeService,private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot):boolean{
    const expectedRole = route.data['expectedRole'];
    this.isloggedIn = this.storageService.isLoggedIn();
    this.roles = this.storageService.getUser().roles
    console.log(this.roles);
    if( this.isloggedIn == false || !this.roles.includes(expectedRole)){
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
