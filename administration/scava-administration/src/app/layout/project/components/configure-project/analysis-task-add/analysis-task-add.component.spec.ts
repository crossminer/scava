import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisTaskAddComponent } from './analysis-task-add.component';

describe('AnalysisTaskAddComponent', () => {
  let component: AnalysisTaskAddComponent;
  let fixture: ComponentFixture<AnalysisTaskAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalysisTaskAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalysisTaskAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
