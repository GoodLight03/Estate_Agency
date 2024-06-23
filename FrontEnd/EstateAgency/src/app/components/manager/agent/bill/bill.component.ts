import { RoomService } from './../../../../service/room.service';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { StorangeService } from '../../../../service/storange.service';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrl: './bill.component.css'
})
export class BillComponent implements OnInit {

  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  selectedRole= "";

  categoryForm : any ={
    //id: null,
    name : null,
    prive: null,
    add: null,
    decr:null,
    stt:null,
    img:null,
    idAgent: null
  }

  constructor(private messageService : MessageService,private RoomService: RoomService,private storageService: StorangeService){}

  ngOnInit(): void {
    this.getListCategory();
    this.categoryForm.idAgent = this.storageService.getUser().id;
    console.log(this.categoryForm.idAgent)
  }

  onFileSelected(event: any): void {
    this.categoryForm.img = event.target.files[0];
  }

  getListCategory(){
    this.RoomService.getRoomByAgent(this.storageService.getUser().id).subscribe({
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
      price: null,
      address: null,
      description:null,
      stt:null,
      state:null,
      idAgent: null
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
    const {name,price,address,description,state,img,idAgent} = this.categoryForm;
    console.log(this.categoryForm);
    this.RoomService.createRoom(name,price,address,description,state,img,this.storageService.getUser().id).subscribe({
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
