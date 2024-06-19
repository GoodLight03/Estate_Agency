import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../service/chat.service';
import { Router } from '@angular/router';
import { AcountService } from '../../service/acount.service';
import { FormControl, FormGroup } from '@angular/forms';
import { StorangeService } from '../../service/storange.service';
import { Chat } from '../../models/chat';
import { Message } from '../../models/message';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit {

  chatForm: FormGroup;
  chatObj: Chat = new Chat();
  messageObj: Message = new Message();
  // chatId: number = 22;
  public messageListV: any;
  public chatList: any = [];
  replymessage: String = "checking";
  public chatData: any;
  msg = "Good work";
  chatId: number = 0;
  color = "";
  secondUserName = "";
  secondUserNameV: any;
  public alluser: any = [];
  check = sessionStorage.getItem('username');
  timesRun = 0;
  timesRun2 = 0;

  mess = "";
  lengthMess = 0;

  firstUserName = sessionStorage.getItem('username');
  senderEmail = this.storange.getUser().email;
  senderCheck = this.storange.getUser().username;

  constructor(private chatService: ChatService, private router: Router, private userService: AcountService, private storange: StorangeService) {
    this.chatForm = new FormGroup({
      replymessage: new FormControl()
    });

  }

  ngOnInit(): void {

    this.chatId = this.storange.geChat();

    this.senderCheck = this.storange.getUser().username;
    console.log("Hello" + this.senderCheck);

    console.log("Hello" + this.storange.geChat());
    // Lấy tất cả các khóa trong sessionStorage
    const keys = Object.keys(sessionStorage);

    // Lặp qua từng khóa và lấy giá trị tương ứng
    keys.forEach(key => {
      const value = sessionStorage.getItem(key);
      console.log(`Key: ${key}, Value: ${value}`);
    });
    if (this.storange.geChat() == 0) {
      this.chatService.getChatByFirstUserNameOrSecondUserName(this.storange.getUser().username).subscribe(data => {
        this.chatData = data;
        this.chatList = this.chatData;
        this.storange.saveChat( this.chatList[0].id)

      });
    }
    
      setInterval(() => {



        this.chatService.getChatById(this.storange.geChat()).subscribe(data => {
          this.chatData = data;
          console.log(this.chatData);
          console.log(this.chatData.messageList);
          this.messageListV = this.chatData.messageList;
          this.secondUserName = this.chatData.secondUserName.username;
          this.firstUserName = this.chatData.firstUserName.username;
          this.lengthMess = this.messageListV.length;
          console.log("length" + this.lengthMess);
          this.secondUserNameV = this.chatData.secondUserName;
        });

      }, 1000) ;



    console.log(this.storange.getUser().username)

    setInterval(() => {
      // For getting all the chat list whose ever is logged in.
      this.chatService.getChatByFirstUserNameOrSecondUserName(this.storange.getUser().username).subscribe(data => {
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

    // let all = setInterval(() => {

    //   this.userService.getListUserAll().subscribe((data) => {
    //     console.log(data);

    //     this.alluser = data;
    //   })

    //   this.timesRun += 1;
    //   if (this.timesRun === 2) {
    //     clearInterval(all);
    //   }
    // }, 1000);


  }

  loadChatByEmail(event: string, event1: string) {
    console.log(event, event1);
    // For removing the previous chatId
    //sessionStorage.removeItem("chatId");


    // For checking the chat room by both the emails , if there is present then it will give the chat Id in sessionStorage
    this.chatService.getChatByFirstUserNameAndSecondUserName(event1, event).subscribe(data => {
      // console.log(data);
      this.chatData = data;
      this.chatId = this.chatData[0].id;
      console.log(this.chatId);
      this.storange.saveChat(this.chatId);


      // setInterval(() => {
      this.chatService.getChatById(this.chatId).subscribe(data => {
        this.chatData = data;
        this.messageListV = this.chatData.messageList;
        this.secondUserName = this.chatData.secondUserName;
        this.firstUserName = this.chatData.firstUserName;
        this.secondUserNameV = this.chatData.secondUserName;
      });
      // }, 1000)

    });

  }

  sendMessage() {
    console.log(this.chatForm.value);

    // This will call the update chat method when ever user send the message
    this.messageObj.replymessage = this.chatForm.value.replymessage;
    this.messageObj.senderEmail = this.mess || "";
    console.log(this.messageObj)
    this.chatService.updateChat(this.storange.getUser().username, this.mess, this.storange.geChat()).subscribe(data => {
      console.log(data);
      this.chatForm.reset();
      this.mess = "";

      // for displaying the messageList by the chatId
      this.chatService.getChatById(this.chatId).subscribe(data => {
        console.log(data);
        this.chatData = data;
        // console.log(this.chatData.messageList);console.log(JSON.stringify(this.chatData.messageList));
        this.messageListV = this.chatData.messageList;
        this.secondUserName = this.chatData.secondUserName;
        this.firstUserName = this.chatData.firstUserName;
        this.secondUserNameV = this.chatData.secondUserName;
      })

    })
  }

  routeX() {
    // this.router.navigateByUrl('/navbar/recommendation-service');
    sessionStorage.clear();
    // window.location.reload();
    this.router.navigateByUrl('');
  }

  routeHome() {
    this.router.navigateByUrl('');
  }


  goToChat(username: any) {
    this.chatService.getChatByFirstUserNameAndSecondUserName(username, this.storange.getUser().username).subscribe(
      (data) => {
        this.chatData = data;
        this.chatId = this.chatData.id;
        //sessionStorage.setItem("chatId", this.chatId);
      },
      (error) => {
        if (error.status == 404) {
          // this.chatObj.firstUserName = sessionStorage.getItem("username") as string;
          // this.chatObj.secondUserName = username;
          this.chatService.createChatRoomV(username, this.storange.getUser().id).subscribe(
            (data) => {
              this.chatData = data;
              this.chatId = this.chatData.chatId;
              sessionStorage.setItem("chatId", this.chatData.chatId);
            })
        } else {

        }
      });

  }

}
