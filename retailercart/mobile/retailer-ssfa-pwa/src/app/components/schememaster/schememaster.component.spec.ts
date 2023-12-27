import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SchememasterComponent } from './schememaster.component';

describe('SchememasterComponent', () => {
  let component: SchememasterComponent;
  let fixture: ComponentFixture<SchememasterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SchememasterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SchememasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
