import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SurvivorFormService } from './survivor-form.service';
import { SurvivorService } from '../service/survivor.service';
import { ISurvivor } from '../survivor.model';

import { SurvivorUpdateComponent } from './survivor-update.component';

describe('Survivor Management Update Component', () => {
  let comp: SurvivorUpdateComponent;
  let fixture: ComponentFixture<SurvivorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let survivorFormService: SurvivorFormService;
  let survivorService: SurvivorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SurvivorUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SurvivorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SurvivorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    survivorFormService = TestBed.inject(SurvivorFormService);
    survivorService = TestBed.inject(SurvivorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const survivor: ISurvivor = { id: 456 };

      activatedRoute.data = of({ survivor });
      comp.ngOnInit();

      expect(comp.survivor).toEqual(survivor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISurvivor>>();
      const survivor = { id: 123 };
      jest.spyOn(survivorFormService, 'getSurvivor').mockReturnValue(survivor);
      jest.spyOn(survivorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ survivor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: survivor }));
      saveSubject.complete();

      // THEN
      expect(survivorFormService.getSurvivor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(survivorService.update).toHaveBeenCalledWith(expect.objectContaining(survivor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISurvivor>>();
      const survivor = { id: 123 };
      jest.spyOn(survivorFormService, 'getSurvivor').mockReturnValue({ id: null });
      jest.spyOn(survivorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ survivor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: survivor }));
      saveSubject.complete();

      // THEN
      expect(survivorFormService.getSurvivor).toHaveBeenCalled();
      expect(survivorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISurvivor>>();
      const survivor = { id: 123 };
      jest.spyOn(survivorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ survivor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(survivorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
