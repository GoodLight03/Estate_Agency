import { Message } from './../models/message';
import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import { BehaviorSubject } from 'rxjs';
import { Observable, throwError } from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http'
import { catchError } from 'rxjs/operators';

import { Router } from '@angular/router';
import { Contact } from '../models/contact';
import { Conversation } from '../models/conversation';
import { User } from '../models/user';
import { MessageFinal } from '../models/message copy';

@Injectable({
  providedIn: 'root'
})
export class ChatfinalService {
  private stompClient: any;
  //private messageSubject: BehaviorSubject<Message[]> = new BehaviorSubject<Message[]>([]);
  private messageSubject: BehaviorSubject<MessageFinal | null> = new BehaviorSubject<MessageFinal | null>(null);

  private apiURL = "http://localhost:8080/";
  private username!:string;
  private contacts: Contact[] = [];
  private conversations:Conversation[] = [];
  private chatConversation!:Conversation;
  private idLastMessgeUser!:string;


  constructor(private httpClient: HttpClient, private router:Router) {
    


  }

  login(user:string): Observable<any> {
    return new Observable((observer) => {
      // this.loadUser(telephone, password).subscribe((data: User) => {
         this.username = user;
      //   console.log(this.user);
        this.loadContacts(user).subscribe((data: Contact[]) => {
          this.contacts = data;
          console.log(data);
          if (this.contacts) {
            
            this.contacts.forEach(contact => {

              this.loadChat(contact.id).subscribe((data:MessageFinal[])=>{
                const conversation: Conversation = {
                  contact: contact,
                  messages: data, 
                  lastMessage: '',
                  viewed: true
                };
                this.conversations.push(conversation);
              })
             
            });
          }
          this.initConnectionSocket();
          this.joinRoom(user);
          this.receivedMessage();
         // this.router.navigate(['']);
          observer.next(); // Emite um valor vazio para indicar sucesso
          observer.complete();
        }, (error) => {
          observer.error(error);
        });
      }
    //   , (error) => {
    //     observer.error(error);
    //   });
    // }
    );
  }

  logout(){
    this.contacts=[];
    this.conversations=[];
  }

  initConnectionSocket(){
    const url = '//localhost:8080/chat-socket'
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
  }

  joinRoom(roomId: string){
    this.stompClient.connect({}, ()=>{
      this.stompClient.subscribe(`/topic/${roomId}`, (messages:any) =>{
        console.log(messages.body);
        const messageContent = JSON.parse(messages.body);
        this.messageSubject.next(messageContent);
      })
    })
  }

    sendMessage(roomId: string, text:string,id:number){
      const message:MessageFinal = {
        id:id,
        time:new Date().toISOString(),
        message: text,
        user: this.username
      }  
      this.chatConversation.messages.push(message);
      this.chatConversation.lastMessage = message.message;
      this.stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify(message))
      if(roomId !== this.idLastMessgeUser){
        const index = this.conversations.findIndex(conv => conv.contact.name === roomId);
        const conversation = this.conversations.splice(index, 1)[0];
        this.conversations.unshift(conversation);
        this.idLastMessgeUser = roomId;
      }
    }

    getMessageSubject(){
      return this.messageSubject.asObservable();
    }

    receivedMessage(){
      this.getMessageSubject().subscribe((message: any) => {
        console.log(message);
        if(this.chatConversation && message.user == this.chatConversation.contact.name){
          this.chatConversation.messages.push(message);
          this.chatConversation.lastMessage = message.message;
        }
        else{
        const index = this.conversations.findIndex(conv => conv.contact.name === message.user);
            if(index != -1) {
              const conversation = this.conversations.splice(index, 1)[0];
              conversation.messages.push(message);
              conversation.lastMessage = message.message;
              this.conversations.unshift(conversation);
              conversation.viewed = false;
            }
            else{
              this.anonymousContact(message.user).subscribe((data: Contact)=>{
                this.addContactInArray(data, true);
                const conversation: Conversation = {
                  contact: data,
                  messages: [message], 
                  lastMessage: message.message,
                  viewed: false
                }
                this.conversations.push(conversation);
                })
            }
          }
      });
    }

   

    httpOptions = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      })
    }
    
    // loadUser(telephone: number, password:string): Observable<any> {
    //   const params = new HttpParams()
    //     .set('telephone', telephone)
    //     .set('password', password);
    //   return this.httpClient.get(this.apiURL + 'user/login', { params })
    //     .pipe(
    //       catchError((error: HttpErrorResponse) => {
    //         if (error.status === 404) {
    //           return throwError('Nenhum usuário encontrado');
    //         } else if(error.status === 400) {
    //           return throwError('Usuário ou senha incorretos');
    //         }
    //         else {
    //           let errorMessage = 'Erro ao carregar usuário';
    //           if (error.error instanceof ErrorEvent) {
    //             errorMessage = `Erro no cliente: ${error.error.message}`;
    //           } else {
    //             errorMessage = `Erro no servidor, tente novamente mais tade.`;
    //             errorMessage = `Erro no servidor, tente novamente mais tade: ${error.status}, `;
    //           }
    //           return throwError(errorMessage);
    //         }
    //       })
    //     );
    // }

  //   signUp(telephone:number, password:string, name: string): Observable<any> {
  //     return this.httpClient.post<any>(this.apiURL + 'user', { telephone, password, name })
  //     .pipe(
  //       catchError((error: HttpErrorResponse) => {
  //         let errorMessage = 'Erro desconhecido';
  //         if (error.error instanceof ErrorEvent) {
  //           // Erro do cliente
  //           errorMessage = `Erro: ${error.error.message}`;
  //         } else {
  //           // Erro do servidor
  //           errorMessage = error.error.message;
  //         }
  //         return throwError(errorMessage);
  //       })
  //     );
  // }

  // editUser(user:User): Observable<any>{
  //   return this.httpClient.put(this.apiURL + 'user', JSON.stringify(user), this.httpOptions)
  //   .pipe(
  //     catchError((error: HttpErrorResponse) => {
  //       if (error.status === 204) {
  //         //a requisição put do back-end spring retorna um código 204, o angular pode encarar como error, então é só verificar se foi o código 204 e retorná-lo indicando que está correto o método
  //         return throwError(204);
  //       } else {
  //         return this.errorHandler(error);
  //       }
  //     })
  //   );
  // }
  

    loadContacts(name:String): Observable<any> {
      return this.httpClient.get<Contact[]>(this.apiURL + 'api/chats/loadcontact/' + name)
        .pipe(
            catchError(this.errorHandler)
        )
    }

    loadChat(id:number): Observable<any> {
      return this.httpClient.get<MessageFinal[]>(this.apiURL + 'api/chats/getChatMessage/' + id)
        .pipe(
            catchError(this.errorHandler)
        )
    }

    errorHandler(error:any) {
      let errorMessage = '';
      if(error.error instanceof ErrorEvent) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
      return throwError(errorMessage);
    }

    // getUser(){
    //   return this.user;
    // }
    getContacts(){
      return this.contacts;
    }
    getContact(telephone:number):Contact| undefined {
      return this.contacts.find(contact => contact.telephone == telephone);
    }

    getConversations(){
      return this.conversations
    }

    // addContact(nickname: string, userAddedId: number): Observable<any> {
    //   const userId = this.user.telephone;
    //   return this.httpClient.post<any>(this.apiURL + 'contact', { userId, userAddedId, nickname });
    // }

    addContactInArray(contact:Contact,anonymous:boolean){
      if(!this.contacts){
        this.contacts = [];
      }
      if(anonymous==false){
      this.contacts.push(contact);
      const conversation: Conversation = {
        contact: contact,
        messages: [], 
        lastMessage: '',
        viewed: true
      }
        this.conversations.push(conversation);
      }
      else{
        this.contacts.push(contact);
      }
    }

    // editContact(contact:Contact): Observable<any>{
    //   return this.httpClient.put(this.apiURL + 'contact', JSON.stringify(contact), this.httpOptions)
    //   .pipe(
    //     catchError((error: HttpErrorResponse) => {
    //       if (error.status === 204) {
    //         //a requisição put do back-end spring retorna um código 204, o angular pode encarar como error, então é só verificar se foi o código 204 e retorná-lo indicando que está correto o método
    //         return throwError(204);
    //       } else {
    //         return this.errorHandler(error);
    //       }
    //     })
    //   );
    // }
    // editContactInArray(contact:Contact){
    //   const index = this.contacts.findIndex(item => item.id === contact.id);
    //   if (index !== -1) {
    //     this.contacts[index] = contact;
    //   }
    // }

    // deleteContact(id: number): Observable<any> {
    //   return this.httpClient.delete(this.apiURL + 'contact/' + id)
    //     .pipe(
    //         catchError(this.errorHandler)
    //     );
    // }

    deleteContactinArray(id:number){
      const index = this.contacts.findIndex(contact => contact.id === id);

      if (index !== -1) {
        this.contacts.splice(index, 1);
      }

      const indexConversation = this.conversations.findIndex(conversation => conversation.contact.telephone === id);

      if (indexConversation !== -1) {
        this.contacts.splice(indexConversation, 1);
      }

    }

    anonymousContact(anonymousTelephone: string): Observable<any> {
      return this.httpClient.get<Contact>(this.apiURL + 'api/chats/anonymous/' + anonymousTelephone)
        .pipe(
            catchError(this.errorHandler)
        );
    }

    selectChatConversation(conversation:Conversation){
      this.chatConversation = conversation;
      console.log(this.chatConversation);
    }
    getChatConversation(){
      return this.chatConversation;
    }

}
