import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ContractService } from '../../../service/contract.service';
import { StorangeService } from '../../../service/storange.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contractcustomer',
  templateUrl: './contractcustomer.component.html',
  styleUrl: './contractcustomer.component.css'
})
export class ContractcustomerComponent implements OnInit{
  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  categoryForm : any ={
    id: null,
    img : null
  }

  id: number = 0;

  constructor(private router: Router, private route: ActivatedRoute,private messageService : MessageService,private contractService: ContractService,private storageService: StorangeService){
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getListCategory();
  }


  getListCategory(){
    this.contractService.getRoomr(this.id).subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

  getFile(file:number):void{
    this.contractService.getFile(file);
  }
}
