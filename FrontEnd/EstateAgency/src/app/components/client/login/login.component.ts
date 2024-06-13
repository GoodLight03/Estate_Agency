import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { StorangeService } from '../../../service/storange.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers:[MessageService]
})
export class LoginComponent implements OnInit {
  isSuccessful = false;
  isSignUpFailed = false;
  isLoggedIn = false;
  isLoginFailed = false;
  roles: string[] = [];
  errorMessage = '';

  loginForm : any = {
    username : null,
    password : null
  }

  registerForm : any = {
    username: null,
    email: null,
    password: null,
    role:[],
    img:null
  }

  selectedRole= "";

addRole(): void {
  // if (this.selectedRole && !this.registerForm.role.includes(this.selectedRole)) {
  
  //   this.registerForm.role.push(this.selectedRole);
  // }
  if (this.selectedRole) {
  
    this.registerForm.role = [this.selectedRole];
    console.log(this.registerForm.role);
  }
}

  constructor(private authService:AuthService,private storageService:StorangeService,private messageService:MessageService,private router:Router){}

  ngOnInit(): void {
  }

  login():void{
    const {username,password} = this.loginForm;
    console.log(this.loginForm);
    this.authService.login(username,password).subscribe({
      next: res =>{
        this.storageService.saveUser(res);
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.storageService.getUser().roles;
        this.showSuccess("Đăng nhập thành công!!");
        if(this.roles[0]=="ROLE_AGENT"){
          this.router.navigate(['/agent/report']);
        }else if(this.roles[0]=="ROLE_ADMIN"){
          this.router.navigate(['/admin/report']);
        }else{
          this.router.navigate(['/']);
        }
        // this.router.navigate(['/']);
      },error: err =>{
        console.log(err);
        this.isLoggedIn = false;
        this.isLoginFailed = true;
      }
    })
  }

  register():void{
    const {username,email,password,role,img} = this.registerForm;
    console.log(this.registerForm);
    this.authService.register(username,email,password,role,img).subscribe({
      next: res =>{
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.showSuccess("Đăng ký thành công")
        this.loginForm.username = username;
        this.loginForm.password = password;
        this.login();
      },error: err =>{
        this.showError(err.message);
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    })
  }

  onFileSelected(event: any): void {
    this.registerForm.img = event.target.files[0];
  }

  loginFormChange(){
    document.getElementById('container')?.classList.remove("right-panel-active");
  }
  registerFormChange(){
    document.getElementById('container')?.classList.add("right-panel-active");
  }
  

  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
    this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }
}
