import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { AcountService } from '../../../service/acount.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-agent-detail',
  templateUrl: './agent-detail.component.html',
  styleUrl: './agent-detail.component.css',
  providers: [MessageService]
})
export class AgentDetailComponent implements OnInit{
 
  id: number = 0;
  agent : any;
  listRelatedProduct: any[] =[];

  constructor(private acountService: AcountService,private router: Router,private route: ActivatedRoute,private messageService: MessageService){
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProduct();
  }

  getProduct(){
    this.acountService.getUserId(this.id).subscribe({
      next: res =>{
        this.agent = res;
       // this.getListRelatedProduct();
      },error: err=>{
        console.log(err);
      }
    })
  }

}
