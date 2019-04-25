import { StacktracesModule } from './stacktraces.module';

describe('StacktracesModule', () => {
  let stacktracesModule: StacktracesModule;

  beforeEach(() => {
    stacktracesModule = new StacktracesModule();
  });

  it('should create an instance', () => {
    expect(stacktracesModule).toBeTruthy();
  });
});
