import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { OrderService } from '../../../../service/order.service';
import { StorangeService } from '../../../../service/storange.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-orderagent',
  templateUrl: './orderagent.component.html',
  styleUrl: './orderagent.component.css',
  providers: [MessageService]
})
export class OrderagentComponent {

  listCategory : any;
  id: number = 0;
  selectedRole="";

  constructor(private messageService : MessageService,private route: ActivatedRoute,private router: Router,private orderService: OrderService,private storageService: StorangeService){
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }


  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getListCategory();
    console.log(this.storageService.getUser().id);
  }

  getListCategory(){
    this.orderService.getRoomOrder(this.id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

  addRole(){
    console.log(this.selectedRole)
  }

  onRoleChange(category: any) {
    // Lấy giá trị select đã được chọn
    this.selectedRole = category.status;
    console.log(this.selectedRole)
    // Thực hiện xử lý tương ứng với giá trị select đã được chọn

    this.orderService.updateBrowse(category.id,this.selectedRole).subscribe({
      next: res =>{
        console.log(res);
        this.showSuccess(res)
        // Tải lại trang hiện tại
        this.getListCategory()
      },error: err =>{
        console.log(err);
      }
    })
    
  }

  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
   this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text : string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }
}
