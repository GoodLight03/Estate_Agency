import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const ROOM_API = "http://localhost:8080/api/room/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  constructor(private http:HttpClient) { }

  getListRoom():Observable<any>{
    return this.http.get(ROOM_API,httpOptions);
  }

  createRoom(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(ROOM_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
