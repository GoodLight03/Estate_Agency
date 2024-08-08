import { Injectable } from '@angular/core';
import { Users } from '../models/users';

const USER_KEY = 'auth-user';

const CHAT_ID = 'chat-id';

@Injectable({
  providedIn: 'root'
})
export class StorangeService {

  constructor() { }

  clean(): void {
    window.sessionStorage.clear();
  }


  saveUser(user: Users): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }
  isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      console.log(user);
      return true;
    }

    return false;
  }

  saveItem(key: string, value: string): void {
    window.sessionStorage.removeItem(key);
    window.sessionStorage.setItem(key, value);
  }
  getItem(key: string): string {
    if (typeof window !== 'undefined') {
      const value = window.sessionStorage.getItem(key);
      if (value) {
        return value;
      }

    }

    return "";
  }

  saveChat(chatid: number): void {
    window.sessionStorage.removeItem(CHAT_ID);
    window.sessionStorage.setItem(CHAT_ID, chatid.toString());
  }

  geChat(): number {
    const user = window.sessionStorage.getItem(CHAT_ID);
    if (user) {
      return Number.parseInt(user,10);
    }

    return 0;
  }

}
