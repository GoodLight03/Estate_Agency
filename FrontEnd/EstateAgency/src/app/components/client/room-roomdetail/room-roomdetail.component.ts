import { Component } from '@angular/core';
import { RoomService } from '../../../service/room.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AcountService } from '../../../service/acount.service';
import { StorangeService } from '../../../service/storange.service';
import { OrderService } from '../../../service/order.service';
import { CommentService } from '../../../service/comment.service';
import { ChatService } from '../../../service/chat.service';
import { Chat } from '../../../models/chat';

@Component({
  selector: 'app-room-roomdetail',
  templateUrl: './room-roomdetail.component.html',
  styleUrl: './room-roomdetail.component.css',
  providers: [MessageService]
})
export class RoomRoomdetailComponent {

  id: number = 0;
  room: any;
  listRelatedProduct: any[] = [];
  agent: any;
  isLoggedIn = false;
  content = "";

  chatId: any = 0;

  public chatData: any = [];

  lisrcommnet: any;

  chatObj: Chat = new Chat();

  constructor(private roomService: RoomService, private router: Router, private route: ActivatedRoute, private messageService: MessageService, private storageService: StorangeService, private acountService: AcountService, private orderSercive: OrderService, private commentService: CommentService, private chatService: ChatService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log("OK" + this.id);
    this.getProduct();
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.getListComment();
  }

  getProduct() {
    this.roomService.getRoomId(this.id).subscribe({
      next: res => {
        this.room = res;
        this.agent = res.user;
        console.log(this.room);
        console.log(this.agent);
        // this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }

  getAgent() {
    console.log(this.room.user.id);
    this.acountService.getUserId(this.room.user.id).subscribe({
      next: res => {
        this.agent = res;
        // this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }

  createOrder() {
    //this.storageService.saveChat(2);
    this.orderSercive.createOrder(this.id, this.storageService.getUser().id).subscribe({
      next: res => {
        this.showSuccess("Đăng ký thành công")

      }, error: err => {
        this.showError(err.message);
      }
    })
    console.log(this.agent.username + "Heloo" + this.storageService.getUser().username);
    this.chatService.getChatByFirstUserNameAndSecondUserName(this.agent.username, this.storageService.getUser().username).subscribe(
      (data: any) => {
        this.chatData = data;
        console.log(this.chatData[0].id);
        this.chatId = this.chatData[0].id;
        this.storageService.saveChat(this.chatId);
        this.router.navigateByUrl('/chat');
      },
      (error: { status: number; }) => {
        if (error.status == 404) {
          this.chatObj.firstUserName = this.storageService.getUser().username;
          this.chatObj.secondUserName = this.agent.username;
          this.chatService.createChatRoomV(this.agent.id, this.storageService.getUser().id).subscribe(
            (data: any) => {
              console.log("Post"+this.chatData); 
              this.chatData = data;
              this.storageService.saveChat(this.chatData[0].id);
              this.router.navigateByUrl('/chat');
              console.log("2")
            })
        } else {
          // this.router.navigateByUrl('/chat');
          console.log("3")
        }
      });

  }


  createComment() {
    this.commentService.createComment(this.content, this.storageService.getUser().id, this.id).subscribe({
      next: res => {
        this.showSuccess("Đăng ký thành công")
        this.getListComment();
      },
      error: err => {
        this.showError(err.message);
      }
    })
  }

  getListComment() {
    this.commentService.getRoomIdComment(this.id).subscribe({
      next: res => {
        this.lisrcommnet = res;
        console.log(res);
      }, error: err => {
        console.log(err);
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
