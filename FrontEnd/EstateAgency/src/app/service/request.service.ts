import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const REQUEST_API = "http://localhost:8080/api/request/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http:HttpClient) { }

  getListrequest():Observable<any>{
    return this.http.get(REQUEST_API,httpOptions);
  }

  createrequest(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(REQUEST_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
