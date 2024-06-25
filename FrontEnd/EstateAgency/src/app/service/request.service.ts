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

  getListrequest(id:number,filter:string):Observable<any>{
    return this.http.get(REQUEST_API+"all/"+id+"?filter="+filter,httpOptions);
  }

  createrequest(iduser:number,idroom: number,description: string):Observable<any>{
    return this.http.post(REQUEST_API +'save',{iduser,idroom,description},httpOptions);
  }

  updateStatus(id: number,status:string):Observable<any>{
    return this.http.patch(REQUEST_API+'status/id/'+id+'/status/'+status,httpOptions)
  }
}
