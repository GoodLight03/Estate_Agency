import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { StorangeService } from '../../../service/storange.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { AcountService } from '../../../service/acount.service';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrl: './information.component.css',
  providers: [MessageService]
})
export class InformationComponent implements OnInit {

  registerForm: any = {
    id: null,
    username: null,
    email: null,
    password: null,
    fullname: null,
    address: null,
    phone: null,
    role: [],
    img: null
  }

  categoryForm: any = {
    username: null,
    oldPassword: null,
    newPassword: null,
    confilmPassword: null
  }

  imgg = null;

  displayForm: boolean = false;

  user: any;

  constructor(private authService: AuthService, private storageService: StorangeService, private messageService: MessageService, private router: Router, private acountService: AcountService) { }

  ngOnInit(): void {
    this.user = this.storageService.getUser();
    this.getUser();
  }


  getUser() {
    this.displayForm=false;
    this.acountService.getUserName(this.storageService.getUser().username).subscribe({
      next: res => {
        this.registerForm.username = res.username;
        this.registerForm.fullname = res.fullname;
        this.registerForm.address = res.address;
        this.registerForm.phone = res.phone;
        this.registerForm.email = res.email;
        this.registerForm.img = res.img;
        this.registerForm.id = res.id;
      }, error: err => {
        console.log(err);
      }
    })
  }

  updateProfile() {
    this.registerForm.img = this.imgg;
    const { id, username, fullname, phone, address, email, img } = this.registerForm;
    console.log(this.registerForm);
    this.acountService.updateProfile(id, username, fullname, phone, address, email, img).subscribe({
      next: res => {
        this.getUser();
        this.showSuccess("Update thành công!");

      }, error: err => {
        this.showError(err.message);
      }
    })
  }

  onFileSelected(event: any): void {
    //this.registerForm.img = event.target.files[0];
    this.imgg = event.target.files[0];

    const file: File = event.target.files[0];
    const reader = new FileReader();
    reader.onloadend = () => {
      const base64String: string = reader.result as string;
      this.registerForm.img = base64String.split(',')[1]; // Lưu chuỗi base64 vào thuộc tính img trong registerForm
    };
    reader.readAsDataURL(file);
  }


  showForm() {
   
    this.categoryForm = {
      username: null,
      oldPassword: null,
      newPassword: null,
      confilmPassword: null
    }
    this.categoryForm.username=this.storageService.getUser().username;
    this.displayForm = true;
  }

  changpass(){
    const { username, oldPassword,newPassword,confilmPassword } = this.categoryForm;
    console.log(this.categoryForm);
    this.acountService.changePassword(username, oldPassword,newPassword,confilmPassword ).subscribe({
      next: res => {
        this.getUser();
        this.showSuccess("Đổi Mật khẩu thành công!");

      }, error: err => {
        this.showError(err.message);
      }
    })
  }

  showSuccess(text: string) {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: text });
  }
  showError(text: string) {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: text });
  }

  showWarn(text: string) {
    this.messageService.add({ severity: 'warn', summary: 'Warn', detail: text });
  }

}
