import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../models/message';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Chat } from '../models/chat';

const CHAT_API = "http://localhost:8080/api/chats/";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  baseUrl = "http://localhost:8080";

  constructor(private httpClient: HttpClient) { }


  // updateChat(message: Message, chatId: any): Observable<Object> {
  //   return this.httpClient.put(this.baseUrl + "/chats/message/" + `${chatId}`, message);
  // }

  updateChat(acount:string,message: string, chatId: number): Observable<Object> {
    return this.httpClient.put(CHAT_API + "message/" + chatId+"?acount="+acount+"&message="+message,httpOptions );
  }

  getChatById(chatId: number) {
    return this.httpClient.get(CHAT_API  + chatId)
  }

  // createChatRoom(chat: Chat): Observable<Object> {
  //   return this.httpClient.post(this.baseUrl + "/chats/add", chat);
  // }

  createChatRoomV(idfirstUserName:number, idsecondUserName:number): Observable<any> {
    return this.httpClient.post(CHAT_API + "create?idfirstUserName="+idfirstUserName+"&idsecondUserName="+idsecondUserName,httpOptions);
  }

  getChatByFirstUserNameAndSecondUserName(firstUserName: String, secondUserName: String) {
    return this.httpClient.get(CHAT_API + "getChatByFirstUserNameAndSecondUserName" + '?firstUserName=' + firstUserName + '&secondUserName=' + secondUserName);
  }

  getChatByFirstUserNameOrSecondUserName(username: any) {
    return this.httpClient.get(CHAT_API + "getChatByFirstUserNameOrSecondUserName/" + username);
  }
}
