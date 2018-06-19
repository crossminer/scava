import { TestBed, inject } from '@angular/core/testing';

import { JwtAuthenticationService } from './jwt-authentication.service';

describe('JwtAuthenticationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JwtAuthenticationService]
    });
  });

  it('should be created', inject([JwtAuthenticationService], (service: JwtAuthenticationService) => {
    expect(service).toBeTruthy();
  }));
});
