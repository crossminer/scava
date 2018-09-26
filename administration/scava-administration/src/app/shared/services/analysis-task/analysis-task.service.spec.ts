import { TestBed, inject } from '@angular/core/testing';

import { AnalysisTaskService } from './analysis-task.service';

describe('AnalysisTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AnalysisTaskService]
    });
  });

  it('should be created', inject([AnalysisTaskService], (service: AnalysisTaskService) => {
    expect(service).toBeTruthy();
  }));
});
