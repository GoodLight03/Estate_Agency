import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const USER_API = "http://localhost:8080/api/user/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AcountService {

  constructor(private http: HttpClient) { }

  getListUser():Observable<any>{
    return this.http.get(USER_API+"agent",httpOptions);
  }

  getListUserAll():Observable<any>{
    return this.http.get(USER_API+"all",httpOptions);
  }

  getUserName(username: string):Observable<any>{
    let params = new HttpParams();
    params = params.append('username',username);
    return this.http.get(USER_API+"username",{params: params})
  }

  updateProfile(id:string,username: string,fullname: string,phone:string,address:string,email:string,img:File):Observable<any>{
    const formData = new FormData();
    formData.append('id', id);
    formData.append('username', username);
    formData.append('fullname',fullname);
    formData.append('phone',phone);
    formData.append('address', address);
    formData.append('email',email);
    formData.append('img', img);
    
    return this.http.put(USER_API + 'update',formData);
    
  }

  changePassword(username: string, oldPassword: string,newPassword: string,confilmPassword:string):Observable<any>{
    return this.http.put(USER_API + 'password',{username,oldPassword,newPassword,confilmPassword},httpOptions);
  }

  getUserId(id: number):Observable<any>{
    return this.http.get(USER_API + id,httpOptions);
  }
}
