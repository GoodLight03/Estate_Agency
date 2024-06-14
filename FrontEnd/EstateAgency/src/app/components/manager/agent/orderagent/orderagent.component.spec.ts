import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderagentComponent } from './orderagent.component';

describe('OrderagentComponent', () => {
  let component: OrderagentComponent;
  let fixture: ComponentFixture<OrderagentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrderagentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
