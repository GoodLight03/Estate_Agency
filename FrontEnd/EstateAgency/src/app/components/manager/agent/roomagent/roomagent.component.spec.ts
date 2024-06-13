import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomagentComponent } from './roomagent.component';

describe('RoomagentComponent', () => {
  let component: RoomagentComponent;
  let fixture: ComponentFixture<RoomagentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoomagentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RoomagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
