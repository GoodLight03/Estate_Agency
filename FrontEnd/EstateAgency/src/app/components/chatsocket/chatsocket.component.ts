import { StorangeService } from './../../service/storange.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChatserviceService } from '../../service/chatservice.service';
import { ChatMessage } from '../../models/chat-message';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-chatsocket',
  templateUrl: './chatsocket.component.html',
  styleUrl: './chatsocket.component.css'
})
export class ChatsocketComponent implements OnInit {

  messageInput: string = '';
  userId: string="";
  messageList: any[] = [];
  roomchatId:string="";

  public chatList: any = [];
  public chatData: any;

  firstUserName = sessionStorage.getItem('username');
  senderEmail = this.sStorangeService.getUser().email;
  senderCheck = this.sStorangeService.getUser().username;

  constructor(private chatService: ChatserviceService, private chatServices: ChatService,
    private route: ActivatedRoute, private sStorangeService: StorangeService, private router:Router
    ){
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params["idRoomChat"];
    console.log("Id User: "+this.userId);
    this.roomchatId = this.sStorangeService.geChat().toString();
    console.log(this.roomchatId);
    //this.userId=this.sStorangeService.getUser().id as string;
    console.log("Id User: "+this.userId);
    this.chatService.joinRoom(this.roomchatId);
    this.lisenerMessage();

    setInterval(() => {
      // For getting all the chat list whose ever is logged in.
      this.chatServices.getChatByFirstUserNameOrSecondUserName(this.sStorangeService.getUser().username).subscribe(data => {
        console.log(data);
        this.chatData = data;
        this.chatList = this.chatData;

        this.chatList.sort((a: any, b: any) => a.firstUserName.id - b.firstUserName.id);

      });


      // this.timesRun2 += 1;
      // if (this.timesRun2 === 2) {
      //   clearInterval(getByname);
      // }
    }, 1000);
  }

  sendMessage() {
    const chatMessage = {
      message: this.messageInput,
      user: this.userId
    }as ChatMessage
    this.chatService.sendMessage(this.roomchatId, chatMessage);
    this.messageInput = '';
  }

  lisenerMessage() {
    console.log(this.userId+"Submit");
    this.chatService.getMessageSubject().subscribe((messages: any) => {
      console.log(messages);
      this.messageList = messages.map((item: any)=> ({
        ...item,
        message_side: item.user === this.userId ? 'sender': 'receiver'
      }))
    });
  }

  loadChatByEmail(event: string, event1: string) {
    console.log(event, event1);
    // For removing the previous chatId
    //sessionStorage.removeItem("chatId");


    // For checking the chat room by both the emails , if there is present then it will give the chat Id in sessionStorage
    this.chatServices.getChatByFirstUserNameAndSecondUserName(event1, event).subscribe(data => {
      // console.log(data);
      this.chatData = data;
      this.roomchatId = this.chatData[0].id;
      console.log(this.roomchatId);
      this.sStorangeService.saveChat(Number.parseInt(this.roomchatId));

    });

  }
}
