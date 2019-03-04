import { UserManagementModule } from './user-management.module';

describe('UserManagementModule', () => {
  let userManagementModule: UserManagementModule;

  beforeEach(() => {
    userManagementModule = new UserManagementModule();
  });

  it('should create an instance', () => {
    expect(userManagementModule).toBeTruthy();
  });
});
