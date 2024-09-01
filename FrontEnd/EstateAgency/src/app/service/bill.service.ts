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
  constructor(private http: HttpClient) { }

  getBillByContract(id: number): Observable<any> {
    return this.http.get(BILL_API + id, httpOptions);
  }

  payment(id:number,money: number,infor:string,user:string){
    return this.http.get(BILL_API + "payment/"+id+"/"+money+"/"+infor+"/"+user,  { responseType: 'text' });
  }

  createBill(name: string, idcontact: number,start:Date,end:Date): Observable<any> {
    return this.http.post(BILL_API + 'create', { name, idcontact ,start,end}, httpOptions);
  }

  getFile(file: number): void {
    //return this.http.get(CONTRACT_API +'getfile/'+file,httpOptions);

    this.http.get(BILL_API + 'billid/' + file, { responseType: 'arraybuffer' })
      .subscribe((response: ArrayBuffer) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
  }

  downFIle(file: number):void {
    this.http.get(BILL_API + "download/" + file, { responseType: 'arraybuffer' })
      .subscribe((response: ArrayBuffer) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const downloadUrl = URL.createObjectURL(blob);
        window.open(downloadUrl);
      });
    
  }

  getReport(id: number): Observable<any> {
    return this.http.get(BILL_API+"report/" + id, httpOptions);
  }

  getReportAgent(id: number): Observable<any> {
    return this.http.get(BILL_API+"reportagent/" + id, httpOptions);
  }

  getReportAdmin(): Observable<any> {
    return this.http.get(BILL_API+"reportadmin", httpOptions);
  }

  getReportContractMaintain(id: number): Observable<any> {
    return this.http.get(BILL_API+"reportcontractmaintain/" + id, httpOptions);
  }

}
