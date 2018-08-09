import { PasswordModule } from './password.module';

describe('PasswordModule', () => {
  let passwordModule: PasswordModule;

  beforeEach(() => {
    passwordModule = new PasswordModule();
  });

  it('should create an instance', () => {
    expect(passwordModule).toBeTruthy();
  });
});
