import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { StorangeService } from '../../../service/storange.service';
import { RoomService } from '../../../service/room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.css',
  providers: [MessageService,ConfirmationService]
})
export class RoomComponent implements OnInit {

  listCategory : any;
  
  constructor(private messageService : MessageService,private roomService: RoomService,private storageService: StorangeService){}


  ngOnInit(): void {
    this.getListCategory();
  }

  getListCategory(){
    this.roomService.getAllEnable().subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

}
