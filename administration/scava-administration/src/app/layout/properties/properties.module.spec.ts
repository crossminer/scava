import { PropertiesModule } from './properties.module';

describe('PropertiesModule', () => {
  let propertiesModule: PropertiesModule;

  beforeEach(() => {
    propertiesModule = new PropertiesModule();
  });

  it('should create an instance', () => {
    expect(propertiesModule).toBeTruthy();
  });
});
