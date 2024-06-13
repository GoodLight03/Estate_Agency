import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportagentComponent } from './reportagent.component';

describe('ReportagentComponent', () => {
  let component: ReportagentComponent;
  let fixture: ComponentFixture<ReportagentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReportagentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReportagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
