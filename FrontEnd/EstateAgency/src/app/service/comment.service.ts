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

  createComment(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(COMMENT_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
