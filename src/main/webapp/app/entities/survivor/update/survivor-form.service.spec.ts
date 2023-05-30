import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../survivor.test-samples';

import { SurvivorFormService } from './survivor-form.service';

describe('Survivor Form Service', () => {
  let service: SurvivorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SurvivorFormService);
  });

  describe('Service methods', () => {
    describe('createSurvivorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSurvivorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            survivorId: expect.any(Object),
            name: expect.any(Object),
            age: expect.any(Object),
            gender: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing ISurvivor should create a new form with FormGroup', () => {
        const formGroup = service.createSurvivorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            survivorId: expect.any(Object),
            name: expect.any(Object),
            age: expect.any(Object),
            gender: expect.any(Object),
            latitude: expect.any(Object),
            longitude: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getSurvivor', () => {
      it('should return NewSurvivor for default Survivor initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSurvivorFormGroup(sampleWithNewData);

        const survivor = service.getSurvivor(formGroup) as any;

        expect(survivor).toMatchObject(sampleWithNewData);
      });

      it('should return NewSurvivor for empty Survivor initial value', () => {
        const formGroup = service.createSurvivorFormGroup();

        const survivor = service.getSurvivor(formGroup) as any;

        expect(survivor).toMatchObject({});
      });

      it('should return ISurvivor', () => {
        const formGroup = service.createSurvivorFormGroup(sampleWithRequiredData);

        const survivor = service.getSurvivor(formGroup) as any;

        expect(survivor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISurvivor should not enable id FormControl', () => {
        const formGroup = service.createSurvivorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSurvivor should disable id FormControl', () => {
        const formGroup = service.createSurvivorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
