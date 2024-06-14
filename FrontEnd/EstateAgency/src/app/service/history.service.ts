import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const HISTORY_API = "http://localhost:8080/api/history/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  constructor(private http:HttpClient) { }

  getListHistory():Observable<any>{
    return this.http.get(HISTORY_API,httpOptions);
  }

  createHistory(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(HISTORY_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }

  getAgentOrder(id: number):Observable<any>{
    return this.http.get(HISTORY_API+ 'customer/'+ id,httpOptions);
  }
}
