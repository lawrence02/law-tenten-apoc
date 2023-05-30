import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResourceFormService } from './resource-form.service';
import { ResourceService } from '../service/resource.service';
import { IResource } from '../resource.model';
import { ISurvivor } from 'app/entities/survivor/survivor.model';
import { SurvivorService } from 'app/entities/survivor/service/survivor.service';

import { ResourceUpdateComponent } from './resource-update.component';

describe('Resource Management Update Component', () => {
  let comp: ResourceUpdateComponent;
  let fixture: ComponentFixture<ResourceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resourceFormService: ResourceFormService;
  let resourceService: ResourceService;
  let survivorService: SurvivorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResourceUpdateComponent],
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
      .overrideTemplate(ResourceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResourceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resourceFormService = TestBed.inject(ResourceFormService);
    resourceService = TestBed.inject(ResourceService);
    survivorService = TestBed.inject(SurvivorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Survivor query and add missing value', () => {
      const resource: IResource = { id: 456 };
      const survivor: ISurvivor = { id: 89417 };
      resource.survivor = survivor;

      const survivorCollection: ISurvivor[] = [{ id: 34289 }];
      jest.spyOn(survivorService, 'query').mockReturnValue(of(new HttpResponse({ body: survivorCollection })));
      const additionalSurvivors = [survivor];
      const expectedCollection: ISurvivor[] = [...additionalSurvivors, ...survivorCollection];
      jest.spyOn(survivorService, 'addSurvivorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resource });
      comp.ngOnInit();

      expect(survivorService.query).toHaveBeenCalled();
      expect(survivorService.addSurvivorToCollectionIfMissing).toHaveBeenCalledWith(
        survivorCollection,
        ...additionalSurvivors.map(expect.objectContaining)
      );
      expect(comp.survivorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resource: IResource = { id: 456 };
      const survivor: ISurvivor = { id: 36173 };
      resource.survivor = survivor;

      activatedRoute.data = of({ resource });
      comp.ngOnInit();

      expect(comp.survivorsSharedCollection).toContain(survivor);
      expect(comp.resource).toEqual(resource);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResource>>();
      const resource = { id: 123 };
      jest.spyOn(resourceFormService, 'getResource').mockReturnValue(resource);
      jest.spyOn(resourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resource });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resource }));
      saveSubject.complete();

      // THEN
      expect(resourceFormService.getResource).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resourceService.update).toHaveBeenCalledWith(expect.objectContaining(resource));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResource>>();
      const resource = { id: 123 };
      jest.spyOn(resourceFormService, 'getResource').mockReturnValue({ id: null });
      jest.spyOn(resourceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resource: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resource }));
      saveSubject.complete();

      // THEN
      expect(resourceFormService.getResource).toHaveBeenCalled();
      expect(resourceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResource>>();
      const resource = { id: 123 };
      jest.spyOn(resourceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resource });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resourceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSurvivor', () => {
      it('Should forward to survivorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(survivorService, 'compareSurvivor');
        comp.compareSurvivor(entity, entity2);
        expect(survivorService.compareSurvivor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
