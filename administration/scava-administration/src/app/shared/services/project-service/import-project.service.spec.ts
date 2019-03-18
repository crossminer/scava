import { TestBed, inject } from '@angular/core/testing';

import { ImportProjectService } from './import-project.service';

describe('ImportProjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImportProjectService]
    });
  });

  it('should be created', inject([ImportProjectService], (service: ImportProjectService) => {
    expect(service).toBeTruthy();
  }));
});
