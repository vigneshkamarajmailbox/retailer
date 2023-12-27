import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistributorinfoComponent } from './distributorinfo.component';

describe('DistributorinfoComponent', () => {
  let component: DistributorinfoComponent;
  let fixture: ComponentFixture<DistributorinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistributorinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistributorinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
