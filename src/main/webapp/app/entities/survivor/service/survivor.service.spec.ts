import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISurvivor } from '../survivor.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../survivor.test-samples';

import { SurvivorService } from './survivor.service';

const requireRestSample: ISurvivor = {
  ...sampleWithRequiredData,
};

describe('Survivor Service', () => {
  let service: SurvivorService;
  let httpMock: HttpTestingController;
  let expectedResult: ISurvivor | ISurvivor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SurvivorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Survivor', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const survivor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(survivor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Survivor', () => {
      const survivor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(survivor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Survivor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Survivor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Survivor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSurvivorToCollectionIfMissing', () => {
      it('should add a Survivor to an empty array', () => {
        const survivor: ISurvivor = sampleWithRequiredData;
        expectedResult = service.addSurvivorToCollectionIfMissing([], survivor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(survivor);
      });

      it('should not add a Survivor to an array that contains it', () => {
        const survivor: ISurvivor = sampleWithRequiredData;
        const survivorCollection: ISurvivor[] = [
          {
            ...survivor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSurvivorToCollectionIfMissing(survivorCollection, survivor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Survivor to an array that doesn't contain it", () => {
        const survivor: ISurvivor = sampleWithRequiredData;
        const survivorCollection: ISurvivor[] = [sampleWithPartialData];
        expectedResult = service.addSurvivorToCollectionIfMissing(survivorCollection, survivor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(survivor);
      });

      it('should add only unique Survivor to an array', () => {
        const survivorArray: ISurvivor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const survivorCollection: ISurvivor[] = [sampleWithRequiredData];
        expectedResult = service.addSurvivorToCollectionIfMissing(survivorCollection, ...survivorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const survivor: ISurvivor = sampleWithRequiredData;
        const survivor2: ISurvivor = sampleWithPartialData;
        expectedResult = service.addSurvivorToCollectionIfMissing([], survivor, survivor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(survivor);
        expect(expectedResult).toContain(survivor2);
      });

      it('should accept null and undefined values', () => {
        const survivor: ISurvivor = sampleWithRequiredData;
        expectedResult = service.addSurvivorToCollectionIfMissing([], null, survivor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(survivor);
      });

      it('should return initial array if no Survivor is added', () => {
        const survivorCollection: ISurvivor[] = [sampleWithRequiredData];
        expectedResult = service.addSurvivorToCollectionIfMissing(survivorCollection, undefined, null);
        expect(expectedResult).toEqual(survivorCollection);
      });
    });

    describe('compareSurvivor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSurvivor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSurvivor(entity1, entity2);
        const compareResult2 = service.compareSurvivor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSurvivor(entity1, entity2);
        const compareResult2 = service.compareSurvivor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSurvivor(entity1, entity2);
        const compareResult2 = service.compareSurvivor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
