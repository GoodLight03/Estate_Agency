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
    return this.http.get(CONTACT_API+"all",httpOptions);
  }

  createContact(title:string,content: string,iduser:number):Observable<any>{
    return this.http.post(CONTACT_API +'save',{title,content,iduser},httpOptions);
  }

  update(reply:string,id:number):Observable<any>{
    return this.http.post(CONTACT_API +'update/'+id+"?reply="+reply,httpOptions);
  }
}
