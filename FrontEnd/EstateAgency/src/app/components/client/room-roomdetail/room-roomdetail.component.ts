import { Component } from '@angular/core';
import { RoomService } from '../../../service/room.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';

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

  constructor(private roomService: RoomService, private router: Router, private route: ActivatedRoute, private messageService: MessageService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProduct();
  }

  getProduct() {
    this.roomService.getRoomId(this.id).subscribe({
      next: res => {
        this.room = res;
        console.log(this.room);
        // this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }
}
