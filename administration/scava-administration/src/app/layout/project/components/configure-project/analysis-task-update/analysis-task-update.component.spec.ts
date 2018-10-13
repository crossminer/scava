import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisTaskUpdateComponent } from './analysis-task-update.component';

describe('AnalysisTaskUpdateComponent', () => {
  let component: AnalysisTaskUpdateComponent;
  let fixture: ComponentFixture<AnalysisTaskUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalysisTaskUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalysisTaskUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
