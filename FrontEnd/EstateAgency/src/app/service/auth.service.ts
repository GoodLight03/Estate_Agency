import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const AUTH_API = "http://localhost:8080/api/auth/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }



  register(username: string, email: string, password: string, role: string[],img:File):Observable<any>{
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password',password);
    formData.append('email',email);
    formData.append('role',role[0]);
    formData.append('img', img);
    console.log(formData.get('role'));
    return this.http.post(AUTH_API + 'register',formData);
    // return this.http.post(AUTH_API + 'register',{username,email,password,role,img},httpOptions);
  }

  login(username: string,password: string):Observable<any>{
    return this.http.post(AUTH_API+ "login",{username,password},httpOptions);
  }

  logout():Observable<any>{
    return this.http.post(AUTH_API + "logout",{},httpOptions);
  }
  
}
