import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import { BehaviorSubject, Observable } from 'rxjs';
import SockJS from 'sockjs-client';
import { ChatMessage } from '../models/chat-message';
import { MessageChat } from '../models/message-chat';

@Injectable({
  providedIn: 'root'
})
export class ChatserviceService {
  private stompClient: any
  private messageSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);

  private messageSubjectV: BehaviorSubject<MessageChat> = new BehaviorSubject<MessageChat>({
    firstUserName: {},
    secondUserName: {},
    messageList: []
});

  constructor() { 
    this.initConnenctionSocket();
  }

  initConnenctionSocket() {
    const url = '//localhost:8080/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
  }

  joinRoom(roomId: string) {
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const currentMessage = this.messageSubject.getValue();
        currentMessage.push(messageContent);

        this.messageSubject.next(currentMessage);

      })
    })
  }

  joinRoomV(roomId: string) {
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);

        const currentMessage = this.messageSubjectV.getValue();
        //const firstMessageChat: MessageChat = messageContent[0]; 
        const extendedMessage: MessageChat = {
          firstUserName: messageContent.firstUserName,
          secondUserName: messageContent.secondUserName,
          messageList: messageContent.messageList,
          //message: messageContent // Thêm dữ liệu tin nhắn vào đối tượng mới
      };
      
      // Cập nhật giá trị trong messageSubjectV với đối tượng mới
      this.messageSubjectV.next(extendedMessage);

      })
    })
  }

  sendMessage(roomId: string, chatMessage: ChatMessage) {
    this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage))
  }

  sendMessageV(roomId: string, chatMessage: ChatMessage): Observable<any> {
    return this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage));
  }

  getMessageSubject(){
    return this.messageSubject.asObservable();
  }

  getMessageSubjectV(){
    return this.messageSubjectV.asObservable();
  }
}
