import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManagementDeleteComponent } from './user-management-delete.component';

describe('UserManagementDeleteComponent', () => {
  let component: UserManagementDeleteComponent;
  let fixture: ComponentFixture<UserManagementDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserManagementDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManagementDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
