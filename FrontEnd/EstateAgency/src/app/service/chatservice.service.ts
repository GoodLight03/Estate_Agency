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
    id: 0,
    firstUserName: {},
    secondUserName: {},
    messageList: []
  });

  private messageSubjectVL: BehaviorSubject<MessageChat[]> = new BehaviorSubject<MessageChat[]>([]);

  private currentRoomId: string = '';

  constructor() {
    this.initConnenctionSocket();
  }

  initConnenctionSocket() {
    const url = '//localhost:8080/chat-socket';
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket)
  }

  joinRoom(roomId: string) {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
        const messageContent = JSON.parse(messages.body);
        const currentMessage = this.messageSubject.getValue();
        currentMessage.push(messageContent);

        this.messageSubject.next(currentMessage);

      })
    })
  }

  joinRoomV(roomId: string) {
    
    if (roomId !== this.currentRoomId) {
      // Xử lý khi gọi hàm với id khác
      console.log(`Đã gọi hàm với id mới: ${roomId}`);
      this.messageSubjectV.next({
        id: 0,
        firstUserName: {},
        secondUserName: {},
        messageList: []
      });


      this.stompClient.connect({}, () => {

        this.stompClient.unsubscribe(`/topic/${this.currentRoomId}`);


        this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
          const messageContent = JSON.parse(messages.body);

          const currentMessage = this.messageSubjectV.getValue();
          console.log(currentMessage);
          //const firstMessageChat: MessageChat = messageContent[0]; 
          const extendedMessage: MessageChat = {
            id: messageContent.id,
            firstUserName: messageContent.firstUserName,
            secondUserName: messageContent.secondUserName,
            messageList: messageContent.messageList,
            //message: messageContent // Thêm dữ liệu tin nhắn vào đối tượng mới
          };

          //currentMessage.push(extendedMessage);

          // Cập nhật giá trị trong messageSubjectV với đối tượng mới
          this.messageSubjectV.next(extendedMessage);

        })
      })
      // Cập nhật currentRoomId
      this.currentRoomId = roomId;
      // Thực hiện các hành động khác tùy thuộc vào id mới
    } else {
      console.log(`Đã gọi hàm với cùng id: ${roomId}`);
    }

    // this.stompClient.connect({}, () => {

    //   this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
    //     const messageContent = JSON.parse(messages.body);

    //     const currentMessage = this.messageSubjectV.getValue();
    //     console.log(currentMessage);
    //     //const firstMessageChat: MessageChat = messageContent[0]; 
    //     const extendedMessage: MessageChat = {
    //       firstUserName: messageContent.firstUserName,
    //       secondUserName: messageContent.secondUserName,
    //       messageList: messageContent.messageList,
    //       //message: messageContent // Thêm dữ liệu tin nhắn vào đối tượng mới
    //     };

    //     // Cập nhật giá trị trong messageSubjectV với đối tượng mới
    //     this.messageSubjectV.next(extendedMessage);

    //   })
    // })
  }

  sendMessage(roomId: string, chatMessage: ChatMessage) {
    console.log(`/app/chat/${roomId}`);
    this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage))
  }

  sendMessageV(roomId: string, chatMessage: ChatMessage): Observable<any> {
    return this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(chatMessage));
  }

  getMessageSubject() {
    return this.messageSubject.asObservable();
  }

  getMessageSubjectV() {
    const currentMessage = this.messageSubjectV.getValue();
    console.log(currentMessage);
    return this.messageSubjectV.asObservable();
  }
}
