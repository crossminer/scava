import { WorkerModule } from './worker.module';

describe('WorkertModule', () => {
  let workersModule: WorkerModule;

  beforeEach(() => {
    workersModule = new WorkerModule();
  });

  it('should create an instance', () => {
    expect(workersModule).toBeTruthy();
  });
});
