import { Component } from '@angular/core';
import { RoomService } from '../../../service/room.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AcountService } from '../../../service/acount.service';

@Component({
  selector: 'app-room-roomdetail',
  templateUrl: './room-roomdetail.component.html',
  styleUrl: './room-roomdetail.component.css',
  providers: [MessageService]
})
export class RoomRoomdetailComponent {

  id: number = 0;
  room: any;
  listRelatedProduct: any[] = [];
  agent : any;


  constructor(private roomService: RoomService, private router: Router, private route: ActivatedRoute, private messageService: MessageService,private acountService: AcountService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProduct();
    //this.getAgent();
  }

  getProduct() {
    this.roomService.getRoomId(this.id).subscribe({
      next: res => {
        this.room = res;
        this.agent=res.user;
        console.log(this.room);
        // this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }

  getAgent(){
    console.log(this.room.user.id);
    this.acountService.getUserId(this.room.user.id).subscribe({
      next: res =>{
        this.agent = res;
       // this.getListRelatedProduct();
      },error: err=>{
        console.log(err);
      }
    })
  }
  
}
