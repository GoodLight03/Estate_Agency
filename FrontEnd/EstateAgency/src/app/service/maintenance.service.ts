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

  getListMaintenance(id:number):Observable<any>{
    return this.http.get(MAINTAENANCE_API+"all/"+id,httpOptions);
  }

  getListMaintenancebyRoom(id:number):Observable<any>{
    return this.http.get(MAINTAENANCE_API+"allbyroom/"+id,httpOptions);
  }

  createMaintenance(name:string,price: string,idroom:number):Observable<any>{
    return this.http.post(MAINTAENANCE_API +'save',{name,price,idroom},httpOptions);
  }

  upFile(id:number,file:File):Observable<any>{
    const formData = new FormData();
    formData.append('id', id.toString());
    formData.append('file',file);
    return this.http.post(MAINTAENANCE_API +'upFile',formData);

  }

  getFile(file:number):void{
    //return this.http.get(CONTRACT_API +'getfile/'+file,httpOptions);

   this.http.get(MAINTAENANCE_API +'getfile/'+file, { responseType: 'arraybuffer' })
    .subscribe((response: ArrayBuffer) => {
      const file = new Blob([response], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

}
