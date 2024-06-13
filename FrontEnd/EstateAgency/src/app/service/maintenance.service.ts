import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const MAINTAENANCE_API = "http://localhost:8080/api/maintenance/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {

  constructor(private http:HttpClient) { }

  getListMaintenance():Observable<any>{
    return this.http.get(MAINTAENANCE_API,httpOptions);
  }

  createMaintenance(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(MAINTAENANCE_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
