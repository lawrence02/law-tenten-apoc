import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurvivor } from '../survivor.model';

@Component({
  selector: 'jhi-survivor-detail',
  templateUrl: './survivor-detail.component.html',
})
export class SurvivorDetailComponent implements OnInit {
  survivor: ISurvivor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ survivor }) => {
      this.survivor = survivor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
