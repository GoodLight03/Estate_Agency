import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const ORDER_API = "http://localhost:8080/api/order/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http:HttpClient) { }

  createOrder(idRoom:number,idAgent: number):Observable<any>{
    return this.http.post(ORDER_API +'create',{idRoom,idAgent},httpOptions);
  }

  getRoomOrder(id: number):Observable<any>{
    return this.http.get(ORDER_API+ 'room/'+ id,httpOptions);
  }

  getAgentOrder(id: number):Observable<any>{
    return this.http.get(ORDER_API+ 'customer/'+ id,httpOptions);
  }

  updateBrowse(id: number,browse:string):Observable<any>{
    return this.http.patch(ORDER_API+'id/'+id+'/browse/'+browse,httpOptions)
  }
}
