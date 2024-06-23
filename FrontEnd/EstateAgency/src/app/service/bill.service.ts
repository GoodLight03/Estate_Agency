import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BILL_API = "http://localhost:8080/api/bill/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class BillService {
  constructor(private http:HttpClient) { }

  getBillByContract(id:number):Observable<any>{
    return this.http.get(BILL_API+id,httpOptions);
  }
}
