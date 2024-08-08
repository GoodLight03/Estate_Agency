import { StorangeService } from './../../../../service/storange.service';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { AcountService } from '../../../../service/acount.service';
import { RoomService } from '../../../../service/room.service';
import { BillService } from '../../../../service/bill.service';
import { CommentService } from '../../../../service/comment.service';

@Component({
  selector: 'app-reportadmin',
  templateUrl: './reportadmin.component.html',
  styleUrl: './reportadmin.component.css'
})
export class ReportadminComponent implements OnInit{
  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  detail:boolean=false;

  selectedRole=false;

  categoryForm : any ={
    id: null,
    name : null
  }

  reportAgent:any;

  idRoom!:number;

  room:any;

  lisrcommnet: any;

  agent: any;

  constructor(private commentService:CommentService,private messageService : MessageService,private roomService: RoomService,private billService:BillService){}

  ngOnInit(): void {
    this.getListCategory();

    console.log("OK" + this.idRoom);

    this.getReportAdmin();

    this.detail=false;
  
    // this.getListComment();

    // this.getProduct();

  }

  onDetail(id:number){
    this.detail=true;
    this.idRoom = id;
    console.log("OK" + this.idRoom);
    this.getProduct();

    this.getListComment()
  }

  getListComment() {
    this.commentService.getRoomIdComment(this.idRoom).subscribe({
      next: res => {
        this.lisrcommnet = res;
        console.log(res);
      }, error: err => {
        console.log(err);
      }
    })
  }

  getProduct() {
    this.roomService.getRoomId(this.idRoom).subscribe({
      next: res => {
        this.room = res;
        this.agent = res.user;
        
        // this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }

  getReportAdmin(){
    this.billService.getReportAdmin().subscribe({
      next: res => {
        this.reportAgent = res;
        console.log(this.reportAgent);

      }, error: err => {
        console.log(err);
      }
    })
  }

  getListCategory(){
    this.roomService.getAll().subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(this.listCategory);
        console.log(this.listCategory[0].id);
        this.idRoom=this.listCategory[0].id;
        console.log(this.idRoom);
        this.getProduct();
        this.getListComment();
      },error: err =>{
        console.log(err);
      }
    })
  }

  onRoleChange(category: any) {
    // Lấy giá trị select đã được chọn
    this.selectedRole = category.enabled;
    console.log(this.selectedRole)
    // Thực hiện xử lý tương ứng với giá trị select đã được chọn

    this.roomService.enabel(category.id,this.selectedRole).subscribe({
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

  showForm(){
    this.onUpdate = false;
    this.categoryForm ={
      id : null,
      name : null
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
    const {name} = this.categoryForm;
    // this.categoryService.createCategory(name).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Tạo danh mục thành công!");
    //     this.displayForm = false;
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
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
    //this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
   // this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text : string) {
   // this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }

}
