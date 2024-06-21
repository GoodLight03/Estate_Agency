import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ContactService } from '../../../service/contact.service';
import { StorangeService } from '../../../service/storange.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css',
  providers: [MessageService]
})
export class ContactComponent implements OnInit {
  displayForm: boolean = false;
  
  categoryForm : any ={
    iduser : null,
    title : null,
    content:null
  }

  constructor(private messageService : MessageService,private contactService: ContactService, private storange: StorangeService){}


  ngOnInit(): void {
   
  }

  createContact(){
    const {iduser,title,content} = this.categoryForm;
    console.log(this.categoryForm);
    this.contactService.createContact(title,content,this.storange.getUser().id).subscribe({
      next: res =>{
        
        this.showSuccess("Tạo danh mục thành công!");
        this.displayForm = false;
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
