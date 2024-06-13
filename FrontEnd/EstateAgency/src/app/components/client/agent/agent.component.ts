import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AcountService } from '../../../service/acount.service';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrl: './agent.component.css',
  providers: [MessageService,ConfirmationService]
})
export class AgentComponent implements OnInit{

  listProduct: any;

  constructor(private messageService: MessageService,private productService: AcountService){

  }

  ngOnInit(): void {
    this.getListProduct();
  }
  getListProduct(){
    this.productService.getListUser().subscribe({
      next: res =>{
        this.listProduct =res;
        console.log(this.listProduct)
      },error: err=>{
        console.log(err);
      }
    })
  }

}
