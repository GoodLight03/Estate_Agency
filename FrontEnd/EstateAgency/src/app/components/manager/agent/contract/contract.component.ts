import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ContractService } from '../../../../service/contract.service';
import { StorangeService } from '../../../../service/storange.service';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrl: './contract.component.css',
  providers: [MessageService]
})
export class ContractComponent implements OnInit{
  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  categoryForm : any ={
    id: null,
    img : null
  }

  constructor(private messageService : MessageService,private contractService: ContractService,private storageService: StorangeService){}

  ngOnInit(): void {
    this.getListCategory();
  }


  getListCategory(){
    this.contractService.getAgentOrder(this.storageService.getUser().id).subscribe({
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

  onFileSelected(event: any, categoryId: any): void {
    this.categoryForm.img = event.target.files[0];
    this.categoryForm.id=categoryId.id;
    console.log(this.categoryForm);
    this.contractService.upFile(this.categoryForm.id,this.categoryForm.img).subscribe({
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
    this.contractService.getFile(file);
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
