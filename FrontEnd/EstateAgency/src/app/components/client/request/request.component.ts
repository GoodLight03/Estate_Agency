import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { RequestService } from '../../../service/request.service';
import { StorangeService } from '../../../service/storange.service';
import { HistoryService } from '../../../service/history.service';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrl: './request.component.css',
  providers: [MessageService]
})
export class RequestComponent implements OnInit{
  listCategory : any;

  listRoom : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  select="";

  selectbyroom:number=0;

  categoryForm : any ={
    iduser : null,
    idroom : null,
    description:null
  }

  constructor(private messageService : MessageService,private requestService: RequestService, private storange: StorangeService,private history:HistoryService){}

  ngOnInit(): void {
    this.getListCategory();
    this.listRoomUser();
  }


  getListCategory(){
    this.requestService.getListrequest(this.storange.getUser().id,"User").subscribe({
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
      iduser : null,
      idroom : null,
      description:null
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
    const {iduser,idroom,description} = this.categoryForm;
    this.requestService.createrequest(this.storange.getUser().id,idroom,description).subscribe({
      next: res =>{
        this.getListCategory();
        this.showSuccess("Tạo danh mục thành công!");
        this.displayForm = false;
      },error: err=>{
        this.showError(err.message);
      }
    })
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


  listRoomUser(){
    this.history.getAgentOrder(this.storange.getUser().id).subscribe({
      next: res =>{
        //this.getListCategory();
        this.listRoom=res;
        console.log(this.listRoom)
        //this.showSuccess("Cập nhật thành công!!");
      },error: err=>{
        this.showError(err.message);
      }
    })
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


  addRoom(){
   
    console.log(this.select);
    //this.select=idRoom;
    
    if(this.select){
      this.categoryForm.idroom=this.select;
      console.log("Hello"+this.categoryForm.idroom)
    }
  }

  selectRoom(){
    if(this.selectbyroom!=-1){
      this.requestService.getListrequest(this.selectbyroom,"UserX").subscribe({
        next: res =>{
          this.listCategory = res;
          console.log(res);
        },error: err =>{
          console.log(err);
        }
      })
    }else{
      this.getListCategory()
    }
    
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
