import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomRoomdetailComponent } from './room-roomdetail.component';

describe('RoomRoomdetailComponent', () => {
  let component: RoomRoomdetailComponent;
  let fixture: ComponentFixture<RoomRoomdetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoomRoomdetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RoomRoomdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
