import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatfinalComponent } from './chatfinal.component';

describe('ChatfinalComponent', () => {
  let component: ChatfinalComponent;
  let fixture: ComponentFixture<ChatfinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChatfinalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ChatfinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
