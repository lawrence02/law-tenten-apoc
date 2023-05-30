import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SurvivorFormService, SurvivorFormGroup } from './survivor-form.service';
import { ISurvivor } from '../survivor.model';
import { SurvivorService } from '../service/survivor.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { InfectionStatus } from 'app/entities/enumerations/infection-status.model';

@Component({
  selector: 'jhi-survivor-update',
  templateUrl: './survivor-update.component.html',
})
export class SurvivorUpdateComponent implements OnInit {
  isSaving = false;
  survivor: ISurvivor | null = null;
  genderValues = Object.keys(Gender);
  infectionStatusValues = Object.keys(InfectionStatus);

  editForm: SurvivorFormGroup = this.survivorFormService.createSurvivorFormGroup();

  constructor(
    protected survivorService: SurvivorService,
    protected survivorFormService: SurvivorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ survivor }) => {
      this.survivor = survivor;
      if (survivor) {
        this.updateForm(survivor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const survivor = this.survivorFormService.getSurvivor(this.editForm);
    if (survivor.id !== null) {
      this.subscribeToSaveResponse(this.survivorService.update(survivor));
    } else {
      this.subscribeToSaveResponse(this.survivorService.create(survivor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISurvivor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(survivor: ISurvivor): void {
    this.survivor = survivor;
    this.survivorFormService.resetForm(this.editForm, survivor);
  }
}
