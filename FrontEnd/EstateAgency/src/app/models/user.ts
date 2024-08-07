import { Contact } from "./contact";

export interface User{
    telephone:number;
    username: string;
    password: string;
    photo: string;
    status: string;
    contacts: Contact[];
}
