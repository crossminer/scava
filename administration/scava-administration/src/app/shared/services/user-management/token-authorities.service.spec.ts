import { TestBed } from '@angular/core/testing';

import { TokenAuthoritiesService } from './token-authorities.service';

describe('TokenAuthoritiesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TokenAuthoritiesService = TestBed.get(TokenAuthoritiesService);
    expect(service).toBeTruthy();
  });
});
