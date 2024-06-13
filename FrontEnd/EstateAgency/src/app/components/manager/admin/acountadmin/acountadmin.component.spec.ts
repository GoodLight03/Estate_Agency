import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcountadminComponent } from './acountadmin.component';

describe('AcountadminComponent', () => {
  let component: AcountadminComponent;
  let fixture: ComponentFixture<AcountadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AcountadminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AcountadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
