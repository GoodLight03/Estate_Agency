import { RoomService } from './../../../../service/room.service';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService, PrimeIcons } from 'primeng/api';
import { StorangeService } from '../../../../service/storange.service';
import { CommentService } from '../../../../service/comment.service';

@Component({
  selector: 'app-roomagent',
  templateUrl: './roomagent.component.html',
  styleUrl: './roomagent.component.css',
  providers: [MessageService,ConfirmationService,PrimeIcons],
})
export class RoomagentComponent implements OnInit {

  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  detail:boolean=false;

  selectedRole= "";

  idRoom!:number;

  room:any;

  lisrcommnet: any;

  agent: any;

  categoryForm : any ={
    id: null,
    name : null,
    prive: null,
    add: null,
    decr:null,
    stt:null,
    img:null,
    idAgent: null
  }

  constructor(private commentService:CommentService,private roomService:RoomService,private messageService : MessageService,private RoomService: RoomService,private storageService: StorangeService){}

  ngOnInit(): void {
    this.getListCategory();
    this.categoryForm.idAgent = this.storageService.getUser().id;
    console.log(this.categoryForm.idAgent)
    this.detail=false;

    
    // this.getProduct();

    // this.getListComment()
  }

  onFileSelected(event: any): void {
    this.categoryForm.img = event.target.files[0];
  }

  getListCategory(){
    this.RoomService.getRoomByAgent(this.storageService.getUser().id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
        if(this.listCategory.length>0){
          console.log(this.listCategory[0].id);
          this.idRoom=this.listCategory[0].id;
          console.log(this.idRoom);
          this.getListComment();
          this.getProduct();
          
        }

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

  
 onUpdateForm(category:any){
      this.onUpdate = true;
      this.displayForm =true;
      this.categoryForm.id = category.id;
      this.categoryForm.name = category.name;
      this.categoryForm.price = category.price;
      this.categoryForm.address = category.address;
      this.categoryForm.description = category.description;
      //this.categoryForm.name = category.name;
  }

  onDelete(id:number,name : string){
    this.deleteForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;
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
   // const {id,name} = this.categoryForm;
    const {id,name,price,address,description,state,img,idAgent} = this.categoryForm;
    console.log(this.categoryForm);
    this.RoomService.updateRoom(id,name,price,address,description,state,img,this.storageService.getUser().id).subscribe({
      next: res =>{
        this.getListCategory();
        this.showSuccess("Update thành công!");
        this.displayForm = false;
      },error: err=>{
        this.showError(err.message);
      }
    })
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
    const {id} = this.categoryForm.id;
    this.RoomService.deleteRoom(this.categoryForm.id).subscribe({
      next: res =>{
        this.getListCategory();
        this.showWarn("Xóa danh mục thành công!!");
        this.deleteForm = false;
      },error: err=>{
        this.showError(err.message);
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
