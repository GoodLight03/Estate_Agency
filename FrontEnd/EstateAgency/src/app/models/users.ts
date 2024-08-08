export class Users {
    public id: number;
    public username: string;
    public mobileNumber: string;
    public email : string;
    public password: string;
    public roles : string[];
    public role: string;
    public authorities: string;
    public statusMsg : string;
    public authStatus : string;
    
  
    constructor(id?: number,username?: string, mobileNumber?: string, email?: string,  password?: string,roles?: [],role?: string, 
        authorities?:string,statusMsg?:string, authStatus?:string){
          this.id = id || 0;
          this.username = username || '';
          this.mobileNumber = mobileNumber || '';
          this.email = email || '';
          this.password = password || '';
          this.roles = roles || [];
          this.role=role||'';
          this.authorities = authorities || '';
          this.statusMsg = statusMsg || '';
          this.authStatus = authStatus || '';
    }
  
}
