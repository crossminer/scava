import { TestBed, inject } from '@angular/core/testing';

import { EditProjectService } from './edit-project.service';

describe('EditProjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EditProjectService]
    });
  });

  it('should be created', inject([EditProjectService], (service: EditProjectService) => {
    expect(service).toBeTruthy();
  }));
});
