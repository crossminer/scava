import { ActivateModule } from './activate.module';

describe('ActivateModule', () => {
  let activateModule: ActivateModule;

  beforeEach(() => {
    activateModule = new ActivateModule();
  });

  it('should create an instance', () => {
    expect(activateModule).toBeTruthy();
  });
});
