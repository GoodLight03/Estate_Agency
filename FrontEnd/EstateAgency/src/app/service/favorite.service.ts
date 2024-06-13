import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const FAVOURITE_API = "http://localhost:8080/api/favourite/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  constructor(private http:HttpClient) { }

  getListFavourite():Observable<any>{
    return this.http.get(FAVOURITE_API,httpOptions);
  }

  createFavourite(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(FAVOURITE_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }
}
