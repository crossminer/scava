import { TestBed, inject } from '@angular/core/testing';

import { RoleGuard } from './role.guard';

describe('RoleGuardServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RoleGuard]
    });
  });

  it('should be created', inject([RoleGuard], (guard: RoleGuard) => {
    expect(guard).toBeTruthy();
  }));
});
