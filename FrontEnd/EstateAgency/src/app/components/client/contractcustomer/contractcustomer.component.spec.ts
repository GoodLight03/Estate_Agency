import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractcustomerComponent } from './contractcustomer.component';

describe('ContractcustomerComponent', () => {
  let component: ContractcustomerComponent;
  let fixture: ComponentFixture<ContractcustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ContractcustomerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ContractcustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
