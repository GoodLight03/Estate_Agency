import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderCustommerComponent } from './order-custommer.component';

describe('OrderCustommerComponent', () => {
  let component: OrderCustommerComponent;
  let fixture: ComponentFixture<OrderCustommerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrderCustommerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrderCustommerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
