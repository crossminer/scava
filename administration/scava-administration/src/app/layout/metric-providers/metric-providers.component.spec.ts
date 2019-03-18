import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MetricProvidersComponent } from './metric-providers.component';

describe('MetricProvidersComponent', () => {
  let component: MetricProvidersComponent;
  let fixture: ComponentFixture<MetricProvidersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MetricProvidersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MetricProvidersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
