import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestagentComponent } from './requestagent.component';

describe('RequestagentComponent', () => {
  let component: RequestagentComponent;
  let fixture: ComponentFixture<RequestagentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RequestagentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequestagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
