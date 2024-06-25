import { Component, OnInit } from '@angular/core';
import { BillService } from '../../../../service/bill.service';
import { StorangeService } from '../../../../service/storange.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-reportagent',
  templateUrl: './reportagent.component.html',
  styleUrl: './reportagent.component.css',
})


export class ReportagentComponent implements OnInit {


  listCategory: any;

  listCategoryNew: any;

  displayForm: boolean = false;

  deleteForm: boolean = false;

  onUpdate: boolean = false;

  categoryForm: any = {
    id: null,
    name: null
  }
  label:string[] = [];
  dataV:number[] = [];

  labelNew:number[] = [];
  dataNew:number[] = [];

  data: any;

  options: any;

  basicData: any;

  basicOptions: any;

  reportAgent:any;

  constructor(private messageService: MessageService, private billService: BillService, private storageService: StorangeService) { }

  ngOnInit() {
    this.getListCategory();
   
    this.getReportMaintain();

    this.getReportAgent();
    
  }

  getReportAgent(){
    this.billService.getReportAgent(this.storageService.getUser().id).subscribe({
      next: res => {
        this.reportAgent = res;
        console.log(this.reportAgent);

      }, error: err => {
        console.log(err);
      }
    })
  }

  getReportMaintain(){
    this.billService.getReportContractMaintain(this.storageService.getUser().id).subscribe({
      next: res => {
        this.listCategoryNew = res;
        console.log(this.listCategoryNew);
        this.listCategoryNew.forEach((element: number, index: number) => {
         
          this.dataNew.push(element);
        });

        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');

        this.data = {
            labels: ['Total Room', 'Total Maintain'],
            datasets: [
                {
                    data: this.dataNew,
                    backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
                    hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
                }
            ]
        };

        this.options = {
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true,
                        color: textColor
                    }
                }
            }
        };

      }, error: err => {
        console.log(err);
      }
    })
  }


  getListCategory() {
    this.billService.getReport(this.storageService.getUser().id).subscribe({
      next: res => {
        this.listCategory = res;
        console.log("Hello"+this.listCategory);

        this.listCategory.forEach((element: number[], index: number) => {
          this.label.push(element[0]+"/"+element[1]);
          this.dataV.push(element[2]);
        });

        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
        this.basicData = {
          labels: this.label,
          datasets: [
            {
              label: 'Revenue',
              data: this.dataV,
              backgroundColor: ['rgba(255, 159, 64, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(153, 102, 255, 0.2)'],
              borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(153, 102, 255)'],
              borderWidth: 1
            }
          ]
        };
    
        this.basicOptions = {
          plugins: {
            legend: {
              labels: {
                color: textColor
              }
            }
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                color: textColorSecondary
              },
              grid: {
                color: surfaceBorder,
                drawBorder: false
              }
            },
            x: {
              ticks: {
                color: textColorSecondary
              },
              grid: {
                color: surfaceBorder,
                drawBorder: false
              }
            }
          }
        };


      }, error: err => {
        console.log(err);
      }
    })
  }

  showForm() {
    this.onUpdate = false;
    this.categoryForm = {
      id: null,
      name: null
    }
    this.displayForm = true;
  }


  onUpdateForm(id: number, name: string) {
    this.onUpdate = true;
    this.displayForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;
  }

  onDelete(id: number, name: string) {
    this.deleteForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;
  }

  createCategory() {
    const { name } = this.categoryForm;
    // this.categoryService.createCategory(name).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Tạo danh mục thành công!");
    //     this.displayForm = false;
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
  }


  updateCategory() {
    const { id, name } = this.categoryForm;
    // this.categoryService.updateCategory(id,name).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Cập nhật danh mục thành công!");
    //     this.displayForm = false;
    //   },error: err =>{
    //     this.showError(err.message);
    //   }
    // })
  }


  enableCategory(id: number) {
    // this.categoryService.enableCategory(id).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showSuccess("Cập nhật thành công!!");
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
  }


  deleteCategory() {
    const { id } = this.categoryForm;
    // this.categoryService.deleteCategory(id).subscribe({
    //   next: res =>{
    //     this.getListCategory();
    //     this.showWarn("Xóa danh mục thành công!!");
    //     this.deleteForm = false;
    //   },error: err=>{
    //     this.showError(err.message);
    //   }
    // })
  }

  showSuccess(text: string) {
    //this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
    // this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    // this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }

}
