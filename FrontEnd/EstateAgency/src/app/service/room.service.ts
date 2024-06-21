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

  createRoom(name:string,price: string,address:string,description: string,state:string,img: File,idAgent:string):Observable<any>{
    const formData = new FormData();
    formData.append('name', name);
    formData.append('price',price);
    formData.append('address',address);
    formData.append('describe',description);
    formData.append('state', state);
    formData.append('img',img);
    formData.append('idAgent', idAgent);
    console.log(formData.get('name') +""+ formData.get('idAgent') +'');
    

    // return this.http.post(ROOM_API +'create',{name,price,address,description,state,img,idAgent},httpOptions);
    return this.http.post(ROOM_API +'create',formData);
  }

  getRoomByAgent(id: number):Observable<any>{
    return this.http.get(ROOM_API +'all/'+ id,httpOptions);
  }

  getRoomByAgentEnable(id: number):Observable<any>{
    return this.http.get(ROOM_API +'allenableAgent/'+ id,httpOptions);
  }

  getAll():Observable<any>{
    return this.http.get(ROOM_API,httpOptions);
  }

  getAllEnable():Observable<any>{
    return this.http.get(ROOM_API+"allenable",httpOptions);
  }

  getRoomId(id: number):Observable<any>{
    return this.http.get(ROOM_API+ 'detail/'+ id,httpOptions);
  }

  enabel(id:number,check:boolean):Observable<any>{
    return this.http.post(ROOM_API+"enable/"+id+"?check="+check,httpOptions);
  }
}
