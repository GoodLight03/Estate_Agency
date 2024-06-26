import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { HistoryService } from '../../../service/history.service';
import { StorangeService } from '../../../service/storange.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css',
  providers: [MessageService]
})
export class HistoryComponent {
  listCategory : any;

  constructor(private messageService : MessageService,private historyService: HistoryService,private storageService: StorangeService){}


  ngOnInit(): void {
    this.getListCategory();
    console.log(this.storageService.getUser().id);
  }

  getListCategory(){
    this.historyService.getAgentOrder(this.storageService.getUser().id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }
}
