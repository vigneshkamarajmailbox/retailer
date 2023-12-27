import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguagedialogComponent } from './languagedialog.component';

describe('LanguagedialogComponent', () => {
  let component: LanguagedialogComponent;
  let fixture: ComponentFixture<LanguagedialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LanguagedialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LanguagedialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
