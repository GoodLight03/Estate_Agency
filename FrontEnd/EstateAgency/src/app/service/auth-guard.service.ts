import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { StorangeService } from './storange.service';
import { Users } from '../models/users';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  isLoggedIn: boolean = false;  

  user = new Users();

  constructor(private storageService: StorangeService,private router: Router) { }
  


  canActivate():boolean{
    this.isLoggedIn = this.storageService.isLoggedIn();
    if(this.isLoggedIn == false){
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

//   canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot){
//     if(sessionStorage.getItem('userdetails')){
//         this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
//     }
//     if(!this.user){
//         this.router.navigate(['login']);
//     }
//     return this.user?true:false;
// }
}
