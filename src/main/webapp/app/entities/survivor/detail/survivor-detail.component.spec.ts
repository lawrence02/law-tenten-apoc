import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SurvivorDetailComponent } from './survivor-detail.component';

describe('Survivor Management Detail Component', () => {
  let comp: SurvivorDetailComponent;
  let fixture: ComponentFixture<SurvivorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SurvivorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ survivor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SurvivorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SurvivorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load survivor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.survivor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
