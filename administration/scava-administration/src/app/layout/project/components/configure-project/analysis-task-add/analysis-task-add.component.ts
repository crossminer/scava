import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AnalysisTaskService } from '../../../../../shared/services/analysis-task/analysis-task.service';

import {TableModule} from 'primeng/table';

@Component({
  selector: 'app-analysis-task-add',
  templateUrl: './analysis-task-add.component.html',
  styleUrls: ['./analysis-task-add.component.scss']
})
export class AnalysisTaskAddComponent implements OnInit {

  metricProviders: any[];

  msgs: Message[];

  cars: Car[];

  cols: any[];

  selectedCar1: Car;

  selectedCar2: Car;

  selectedCar3: Car;

  selectedCar4: Car;

  selectedCars1: Car[];

  selectedCars2: Car[];

  selectedCars3: Car[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private analysisTaskService: AnalysisTaskService,
  ) { }

  ngOnInit() {
    this.loadAll();

    this.cars = [
      { "brand": "VW", "year": 2012, "color": "Orange"},
      { "brand": "Audi", "year": 2011, "color": "Black" },
      { "brand": "Renault", "year": 2005, "color": "Gray"},
      { "brand": "BMW", "year": 2003, "color": "Blue" },
      { "brand": "Mercedes", "year": 1995, "color": "Orange" },
      { "brand": "Volvo", "year": 2005, "color": "Black" },
      { "brand": "Honda", "year": 2012, "color": "Yellow"},
      { "brand": "Jaguar", "year": 2013, "color": "Orange" },
      { "brand": "Ford", "year": 2000, "color": "Black" },
      { "brand": "Fiat", "year": 2013, "color": "Red" }
    ];

    this.cols = [
      { field: 'year', header: 'Year' },
      { field: 'brand', header: 'Brand' },
      { field: 'color', header: 'Color' }
    ];
  }

  onRowSelect(event) {
    this.msgs = [];
    this.msgs.push({ severity: 'info', summary: 'Car Selected', detail: event.data.brand });
  }

  onRowUnselect(event) {
    this.msgs = [];
    this.msgs.push({ severity: 'info', summary: 'Car Unselected', detail: event.data.brand });
  }

  loadAll() {
    this.analysisTaskService.getMetricProviders().subscribe(
      (resp) => {
        this.metricProviders = resp as any[];
        console.log(this.metricProviders)
      },
      (error) => {
        this.onError(error);
      })
  }

  save(login) {
  }

  previousState() {
    this.route.paramMap.subscribe(data => {
      console.log(data)
      this.router.navigate(['project/configure/' + data.get('id')]);
    });
  }

  onError(error) {
    console.log(error);
  }

}

export interface Message {
  severity?: string;
  summary?: string;
  detail?: string;
  id?: any;
  key?: string;
  life?: number;
  sticky?: boolean;
  closable?: boolean;
  data?: any;
}

export interface Car {
  year?;
  brand?;
  color?;
  price?;
  saleDate?;
}
