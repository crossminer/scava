import { TestBed, inject } from '@angular/core/testing';

import { CreateProjectService } from './create-project.service';

describe('CreateProjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CreateProjectService]
    });
  });

  it('should be created', inject([CreateProjectService], (service: CreateProjectService) => {
    expect(service).toBeTruthy();
  }));
});
