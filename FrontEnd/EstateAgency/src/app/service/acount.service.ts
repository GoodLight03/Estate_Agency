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

  getUserName(username: string):Observable<any>{
    let params = new HttpParams();
    params = params.append('username',username);
    return this.http.get(USER_API,{params: params})
  }

  updateProfile(username: string,firstname: string,lastname:string,email:string,country:string,state:string,address: string,phone: string):Observable<any>{
    return this.http.put(USER_API +'update',{username,firstname,lastname,email,country,state,address,phone},httpOptions);
  }

  changePassword(username: string, oldPassword: string,newPassword: string):Observable<any>{
    return this.http.put(USER_API + 'password',{username,oldPassword,newPassword},httpOptions);
  }

  getUserId(id: number):Observable<any>{
    return this.http.get(USER_API + id,httpOptions);
  }
}
