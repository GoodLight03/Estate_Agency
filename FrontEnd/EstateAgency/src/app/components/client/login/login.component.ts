import { ChatfinalService } from './../../../service/chatfinal.service';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { StorangeService } from '../../../service/storange.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { Users } from '../../../models/users';
import { getCookie } from 'typescript-cookie';
import { NgForm } from '@angular/forms';
import {jwtDecode} from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  isSuccessful = false;
  isSignUpFailed = false;
  isLoggedIn = false;
  isLoginFailed = false;
  roles: string[] = [];
  errorMessage = '';

  model = new Users();

  loginForm: any = {
    username: null,
    password: null
  }

  registerForm: any = {
    username: null,
    email: null,
    password: null,
    role: [],
    img: null
  }

  selectedRole = "";

  addRole(): void {
    // if (this.selectedRole && !this.registerForm.role.includes(this.selectedRole)) {

    //   this.registerForm.role.push(this.selectedRole);
    // }
    if (this.selectedRole) {

      this.registerForm.role = [this.selectedRole];
      console.log(this.registerForm.role);
    }
  }

  constructor(private authService: AuthService, private storageService: StorangeService, private messageService: MessageService, private router: Router, private chatService: ChatfinalService) { }

  ngOnInit(): void {
  }

  login(): void {
    const { username, password } = this.loginForm;
    console.log(this.loginForm);
    this.authService.login(username, password).subscribe({
      next: res => {

        this.storageService.saveUser(res);
        this.chatService.login(this.storageService.getUser().username).subscribe({
          next: () => {

          },
          error: (error) => {
            //this.warningComponent.updateValues(error, false, false, true, false);
          }
        });;
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.storageService.getUser().roles;
        this.showSuccess("Đăng nhập thành công!!");
        if (this.roles[0] == "ROLE_AGENT") {
          this.router.navigate(['/agent/report']);
        } else if (this.roles[0] == "ROLE_ADMIN") {
          this.router.navigate(['/admin/report']);
        } else {
          this.router.navigate(['/']);
        }
        // this.router.navigate(['/']);
      }, error: err => {
        console.log(err);
        this.isLoggedIn = false;
        this.isLoginFailed = true;
      }
    })
  }

  validateUser(loginForm: NgForm) {
    // const { username, password } = this.loginForm;
    console.log(this.model);
    this.authService.validateLoginDetails(this.model).subscribe(
      responseData => {
        window.sessionStorage.setItem("Authorization", responseData.headers.get('Authorization')!);
        this.model = <any>responseData.body;
        console.log(this.model);
         const decodedToken: Users = jwtDecode( responseData.headers.get('Authorization')!);
        if (!this.model.roles) {
          this.model.roles = [];
        }
        this.model.roles.push(decodedToken.authorities);
        this.model.role=decodedToken.authorities;
        this.model.authStatus = 'AUTH';
        this.storageService.saveUser(this.model);
        window.sessionStorage.setItem("userdetails", JSON.stringify(this.model));
        this.storageService.saveUser(this.model);
        let xsrf = getCookie('XSRF-TOKEN')!;
        window.sessionStorage.setItem("XSRF-TOKEN", xsrf);

        //const authorizationHeader = window.sessionStorage.getItem("Authorization");
        //console.log(authorizationHeader);
       // const decodedToken: any = jwtDecode(authorizationHeader!);
        //console.log(decodedToken);
       

        //this.router.navigate(['/']);

        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.storageService.getUser().roles;
        this.showSuccess("Đăng nhập thành công!!");
        if (this.roles[0] == "ROLE_AGENT") {
          this.router.navigate(['/agent/report']);
        } else if (this.roles[0] == "ROLE_ADMIN") {
          this.router.navigate(['/admin/report']);
        } else {
          this.router.navigate(['/']);
        }
      });

  }

  register(): void {
    const { username, email, password, role, img } = this.registerForm;
    console.log(this.registerForm);
    this.authService.register(username, email, password, role, img).subscribe({
      next: res => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.showSuccess("Đăng ký thành công")
        this.loginForm.username = username;
        this.loginForm.password = password;
        //this.login();
        const loginForms: any = {
          value: { 
            username: username,
            password: password
          },
          submitted: true, // Add other necessary properties as needed
          _directives: {},
          form: {},
          ngSubmit: () => {}
          // Add other required properties as needed
        };

        console.log(loginForms);
        this.model.username=loginForms.value.username;
        this.model.password=loginForms.value.password;
        this.validateUser(loginForms);
      }, error: err => {
        this.showError(err.message);
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    })
  }

  onFileSelected(event: any): void {
    this.registerForm.img = event.target.files[0];
  }

  loginFormChange() {
    document.getElementById('container')?.classList.remove("right-panel-active");
  }
  registerFormChange() {
    document.getElementById('container')?.classList.add("right-panel-active");
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
