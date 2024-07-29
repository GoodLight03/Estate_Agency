import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { StorangeService } from '../../../service/storange.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { ChatfinalService } from '../../../service/chatfinal.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrl: './index.component.css',
  providers: [MessageService]
})
export class IndexComponent implements OnInit{

  authModal : boolean = true;
  isLoggedIn = false;
  name="";
  role="";

  constructor(
    // public cartService:CartService,
    // public wishlistService: WishlistService,
    private authService: AuthService,
    private storageService: StorangeService,
    private messageService:MessageService,
    private chatService: ChatfinalService,
    // private categoryService: CategoryService,
    private router: Router){
    

  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    console.log("Hellot"+this.isLoggedIn);
    this.name=this.storageService.getUser().username;
    console.log(this.name);
    this.role=this.storageService.getUser().roles[0];
    console.log(this.role);
    if(this.role=="ROLE_AGENT"){
      this.router.navigate(['/agent/report']);
    }else if(this.role=="ROLE_ADMIN"){
      this.router.navigate(['/admin/report']);
    }

    setInterval(()=>{
      this.isLoggedIn = this.storageService.isLoggedIn();
    },1000);
  }
  
  logout():void{
    this.chatService.logout();
    this.authService.logout().subscribe({
      next:res =>{
        this.storageService.clean();
        this.isLoggedIn = false;
        this.authModal = false;
        this.showSuccess("Bạn đã đăng xuất!!");
        this.showSuccess("Bạn đã đăng xuất!!");
        this.router.navigate(['/']);
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

  showWarn(text: string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }


}
