import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { StorangeService } from './storange.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  isLoggedIn: boolean = false;  

  constructor(private storageService: StorangeService,private router: Router) { }
  


  canActivate():boolean{
    this.isLoggedIn = this.storageService.isLoggedIn();
    if(this.isLoggedIn == false){
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}
