import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISurvivor, NewSurvivor } from '../survivor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISurvivor for edit and NewSurvivorFormGroupInput for create.
 */
type SurvivorFormGroupInput = ISurvivor | PartialWithRequiredKeyOf<NewSurvivor>;

type SurvivorFormDefaults = Pick<NewSurvivor, 'id'>;

type SurvivorFormGroupContent = {
  id: FormControl<ISurvivor['id'] | NewSurvivor['id']>;
  survivorId: FormControl<ISurvivor['survivorId']>;
  name: FormControl<ISurvivor['name']>;
  age: FormControl<ISurvivor['age']>;
  gender: FormControl<ISurvivor['gender']>;
  latitude: FormControl<ISurvivor['latitude']>;
  longitude: FormControl<ISurvivor['longitude']>;
  status: FormControl<ISurvivor['status']>;
};

export type SurvivorFormGroup = FormGroup<SurvivorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SurvivorFormService {
  createSurvivorFormGroup(survivor: SurvivorFormGroupInput = { id: null }): SurvivorFormGroup {
    const survivorRawValue = {
      ...this.getFormDefaults(),
      ...survivor,
    };
    return new FormGroup<SurvivorFormGroupContent>({
      id: new FormControl(
        { value: survivorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      survivorId: new FormControl(survivorRawValue.survivorId),
      name: new FormControl(survivorRawValue.name),
      age: new FormControl(survivorRawValue.age),
      gender: new FormControl(survivorRawValue.gender),
      latitude: new FormControl(survivorRawValue.latitude),
      longitude: new FormControl(survivorRawValue.longitude),
      status: new FormControl(survivorRawValue.status),
    });
  }

  getSurvivor(form: SurvivorFormGroup): ISurvivor | NewSurvivor {
    return form.getRawValue() as ISurvivor | NewSurvivor;
  }

  resetForm(form: SurvivorFormGroup, survivor: SurvivorFormGroupInput): void {
    const survivorRawValue = { ...this.getFormDefaults(), ...survivor };
    form.reset(
      {
        ...survivorRawValue,
        id: { value: survivorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SurvivorFormDefaults {
    return {
      id: null,
    };
  }
}
