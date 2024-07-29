import { Contact } from "./contact";
import { Message } from "./message";
import { MessageFinal } from "./message copy";

export interface Conversation{
    contact: Contact;
    messages: MessageFinal[];
    lastMessage:string;
    viewed:boolean;
}