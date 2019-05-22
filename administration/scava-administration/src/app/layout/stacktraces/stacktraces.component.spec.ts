import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StacktracesComponent } from './stacktraces.component';

describe('StacktracesComponent', () => {
  let component: StacktracesComponent;
  let fixture: ComponentFixture<StacktracesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StacktracesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StacktracesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
