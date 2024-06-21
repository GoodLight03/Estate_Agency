import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MaintenanceService } from '../../../../service/maintenance.service';
import { StorangeService } from '../../../../service/storange.service';
import { RoomService } from '../../../../service/room.service';

@Component({
  selector: 'app-maintenace',
  templateUrl: './maintenace.component.html',
  styleUrl: './maintenace.component.css',
  providers: [MessageService]
})
export class MaintenaceComponent implements OnInit{
  listCategory : any;

  listRoom : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  select="";

  categoryForm : any ={
    name : null,
    price : null,
    idroom:null
  }

  constructor(private messageService : MessageService,private maintainService: MaintenanceService, private storange: StorangeService,private roomService:RoomService){}

  ngOnInit(): void {
    this.getListCategory();
    this.listRoomUser();
  }


  getListCategory(){
    this.maintainService.getListMaintenance(this.storange.getUser().id).subscribe({
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
      price : null,
      idroom:null
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
    const {name,price,idroom} = this.categoryForm;
    this.maintainService.createMaintenance(name,price,idroom).subscribe({
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
    this.roomService.getRoomByAgent(this.storange.getUser().id).subscribe({
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

  onFileSelected(event: any, categoryId: any): void {
    this.categoryForm.img = event.target.files[0];
    this.categoryForm.id=categoryId.id;
    console.log(this.categoryForm);
    this.maintainService.upFile(this.categoryForm.id,this.categoryForm.img).subscribe({
      next: res =>{
       this.showSuccess("Upload OK");
       this.getListCategory();
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

  getFile(file:number):void{
    this.maintainService.getFile(file);
  }


  addRoom(){
   
    console.log(this.select);
    //this.select=idRoom;
    
    if(this.select){
      this.categoryForm.idroom=this.select;
      console.log("Hello"+this.categoryForm.idroom)
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
