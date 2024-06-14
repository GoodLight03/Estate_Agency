import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { StorangeService } from '../../../service/storange.service';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-order-custommer',
  templateUrl: './order-custommer.component.html',
  styleUrl: './order-custommer.component.css',
  providers: [MessageService]
})
export class OrderCustommerComponent {

  listCategory : any;

  constructor(private messageService : MessageService,private orderService: OrderService,private storageService: StorangeService){}


  ngOnInit(): void {
    this.getListCategory();
    console.log(this.storageService.getUser().id);
  }

  getListCategory(){
    this.orderService.getAgentOrder(this.storageService.getUser().id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }
}
