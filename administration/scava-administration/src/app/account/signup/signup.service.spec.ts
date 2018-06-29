import { TestBed, inject } from '@angular/core/testing';

import { SignupService } from './signup.service';

describe('SignupService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SignupService]
    });
  });

  it('should be created', inject([SignupService], (service: SignupService) => {
    expect(service).toBeTruthy();
  }));
});
