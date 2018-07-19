import { ProjectModule } from './project.module';

describe('ProjectsModule', () => {
  let projectsModule: ProjectModule;

  beforeEach(() => {
    projectsModule = new ProjectModule();
  });

  it('should create an instance', () => {
    expect(projectsModule).toBeTruthy();
  });
});
