import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { AcountService } from '../../../../service/acount.service';
import { RoomService } from '../../../../service/room.service';

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

  selectedRole=false;

  categoryForm : any ={
    id: null,
    name : null
  }

  constructor(private messageService : MessageService,private roomService: RoomService){}

  ngOnInit(): void {
    this.getListCategory();
  }


  getListCategory(){
    this.roomService.getAll().subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
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
