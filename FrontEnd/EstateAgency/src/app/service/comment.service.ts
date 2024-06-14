import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const COMMENT_API = "http://localhost:8080/api/comment/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http:HttpClient) { }

  getListComment():Observable<any>{
    return this.http.get(COMMENT_API,httpOptions);
  }

  createComment(content:string,idCustomer:number,idRoom:number):Observable<any>{
    return this.http.post(COMMENT_API +'create',{content,idCustomer,idRoom},httpOptions);
  }

  getRoomIdComment(id: number):Observable<any>{
    return this.http.get(COMMENT_API+ 'room/'+ id,httpOptions);
  }
}
