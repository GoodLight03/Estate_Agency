import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const CONTRACT_API = "http://localhost:8080/api/contract/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  constructor(private http:HttpClient) { }

  getListCotract():Observable<any>{
    return this.http.get(CONTRACT_API,httpOptions);
  }

  createCotract(name:string,description: string,price: string,quantity:number,categoryId: number,imageIds: Array<string>):Observable<any>{
    return this.http.post(CONTRACT_API +'create',{name,description,price,quantity,categoryId,imageIds},httpOptions);
  }

  getAgentOrder(id: number):Observable<any>{
    return this.http.get(CONTRACT_API+ 'agent/'+ id,httpOptions);
  }

  upFile(id:number,file:File):Observable<any>{
    const formData = new FormData();
    formData.append('id', id.toString());
    formData.append('file',file);
    return this.http.post(CONTRACT_API +'upFile',formData);

  }

  getFile(file:number):void{
    //return this.http.get(CONTRACT_API +'getfile/'+file,httpOptions);

   this.http.get(CONTRACT_API +'getfile/'+file, { responseType: 'arraybuffer' })
    .subscribe((response: ArrayBuffer) => {
      const file = new Blob([response], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

}
