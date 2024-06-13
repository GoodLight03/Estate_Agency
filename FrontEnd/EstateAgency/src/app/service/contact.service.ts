import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const CONTACT_API = "http://localhost:8080/api/contact/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http:HttpClient) { }

  getListContact():Observable<any>{
    return this.http.get(CONTACT_API,httpOptions);
  }

  createContact(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(CONTACT_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
