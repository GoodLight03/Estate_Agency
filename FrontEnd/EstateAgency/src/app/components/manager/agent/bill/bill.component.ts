import { RoomService } from './../../../../service/room.service';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { StorangeService } from '../../../../service/storange.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BillService } from '../../../../service/bill.service';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrl: './bill.component.css'
})
export class BillComponent implements OnInit {

  id: number = 0;

  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  selectedRole= "";

  role="";

  categoryForm : any ={
    //id: null,
    name : null,
    idcontact: null,
   
  }

  constructor(private router: Router, private route: ActivatedRoute,private messageService : MessageService,private billService: BillService,private storageService: StorangeService){
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.role=this.storageService.getUser().roles[0];
    this.id = this.route.snapshot.params['id'];
    console.log("OK" + this.id);
    this.getListCategory();
    this.categoryForm.idAgent = this.storageService.getUser().id;
    console.log(this.categoryForm.idAgent)
  }

  onFileSelected(event: any): void {
    this.categoryForm.img = event.target.files[0];
  }

  getListCategory(){
    this.billService.getBillByContract(this.id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

  showForm(){
    this.onUpdate = false;
    this.categoryForm ={
      name : null,
      idcontact: null,
      
    }
    this.displayForm = true;
  }

  
 onUpdateForm(id: number,name : string){
      this.onUpdate = true;
      this.displayForm =true;
      this.categoryForm.id = id;
      this.categoryForm.name = name;
  }

  onDelete(id:number,name : string){
    this.deleteForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;
  }

  createCategory(){
    //const {name} = this.categoryForm;
    const {name,idcontact} = this.categoryForm;
    console.log(this.categoryForm);
    this.billService.createBill(name,this.id).subscribe({
      next: res =>{
        this.getListCategory();
        this.showSuccess("Tạo danh mục thành công!");
        this.displayForm = false;
      },error: err=>{
        this.showError(err.message);
      }
    })
  }

  getBill(id:number){
    this.billService.getFile(id);
  }

  down(id:number){
    this.billService.downFIle(id);
  }

  payment(id:number,money:number,infor:string){
    this.billService.payment(id,money,infor,this.storageService.getUser().username).subscribe(
      vnpayUrl => {
        // Xử lý kết quả trả về từ phương thức Spring Boot
        window.location.href = vnpayUrl; // Chuyển hướng đến URL trả về từ phương thức Spring Boot
      },
      error => {
        console.log(error); // Xử lý lỗi nếu có
      }
    );
  }


  updateCategory(){
    const {id,name} = this.categoryForm;
    // this.categoryService.updateCategory(id,name).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Cập nhật danh mục thành công!");
    //     this.displayForm = false;
    //   },error: err =>{
    //     this.showError(err.message);
    //   }
    // })
  }


  enableCategory(id : number){
    // this.categoryService.enableCategory(id).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Cập nhật thành công!!");
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
  }


  deleteCategory(){
    const {id} = this.categoryForm;
    // this.categoryService.deleteCategory(id).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showWarn("Xóa danh mục thành công!!");
    //     this.deleteForm = false;
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
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
