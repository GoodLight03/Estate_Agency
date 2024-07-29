import { Component, OnInit } from '@angular/core';
import { ChatfinalService } from '../../service/chatfinal.service';
import { Router } from '@angular/router';
import { Conversation } from '../../models/conversation';
import { User } from '../../models/user';
import { Contact } from '../../models/contact';
import { StorangeService } from '../../service/storange.service';
import { ChatService } from '../../service/chat.service';

@Component({
  selector: 'app-chatfinal',
  templateUrl: './chatfinal.component.html',
  styleUrl: './chatfinal.component.css'
})
export class ChatfinalComponent implements OnInit{
  text: string = '';
  user!: User;
  contacts:any = [];
  chatConversation!:Conversation;
  conversations:Conversation[] = [];
  mobile:boolean = false;
  desktop:boolean = true;

  //para dispositivos móveis
  chatVisibility:boolean = false;


  constructor(private chatService:ChatfinalService, private router:Router, private storang:StorangeService,private chatlogic:ChatService){

  }

  ngOnInit(): void {
    this.screenSize();
   //primeiro if pra quando o ngOnInit for iniciado pela segunda ou demais vezes
      this.user = this.storang.getUser();
      console.log(this.user);
      if(!this.user){
        this.router.navigate(['/login']);
      }
      console.log(this.storang.getUser().username);

      
    
      this.contacts = this.chatService.getContacts();


      this.conversations = this.chatService.getConversations();
      if(!this.chatConversation){
        this.chatConversation = this.chatService.getChatConversation();
      }
      if(this.conversations.length<=0){
       
        this.chatService.login(this.storang.getUser().username).subscribe({
          next: () => {

          },
          error: (error) => {
            //this.warningComponent.updateValues(error, false, false, true, false);
          }
        });;
      }
      console.log(this.conversations);
      /*this.chatConversation = this.conversations[0];*/
  }

  ngOnDestroy(): void {
    this.contacts = [];
    this.conversations = [];
  }

  sendMessage(friendTelephone:Contact){
    this.text = this.text.replace(/\n/g, '');
    this.chatService.sendMessage(friendTelephone.name, this.text,friendTelephone.id);
    this.text = '';
  }

  selectChatConversation(conversation:Conversation){
    this.chatConversation = conversation;
    this.chatService.selectChatConversation(conversation);
  }

  screenSize() {
    const larguraDaTela = window.innerWidth;
    if (larguraDaTela < 745) {
      this.mobile = true;
      this.desktop = false
    }
    console.log(this.mobile);
  }
  changeToChat(){
    if(this.mobile){
      if(this.chatVisibility==true)
      {
        this.chatVisibility=false;
      }
      else{
        this.chatVisibility=true
      }
    }
  }

}


