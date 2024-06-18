import { Message } from "./message";

export class Chat {

    chatId !: number;
    firstUserName !: string;
    secondUserName !: string;
    messageList!: Message[];

    constructor() {

    }
}