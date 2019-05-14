import { TestBed, inject } from '@angular/core/testing';

import { StacktracesService } from './stacktraces.service';

describe('StacktracesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StacktracesService]
    });
  });

  it('should be created', inject([StacktracesService], (service: StacktracesService) => {
    expect(service).toBeTruthy();
  }));
});
