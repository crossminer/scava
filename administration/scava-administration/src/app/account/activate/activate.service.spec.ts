import { TestBed, inject } from '@angular/core/testing';

import { ActivateService } from './activate.service';

describe('ActivateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ActivateService]
    });
  });

  it('should be created', inject([ActivateService], (service: ActivateService) => {
    expect(service).toBeTruthy();
  }));
});
