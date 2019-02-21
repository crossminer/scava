import { MetricProvidersModule } from './metric-providers.module';

describe('MetricProvidersModule', () => {
  let metricProvidersModule: MetricProvidersModule;

  beforeEach(() => {
    metricProvidersModule = new MetricProvidersModule();
  });

  it('should create an instance', () => {
    expect(metricProvidersModule).toBeTruthy();
  });
});
