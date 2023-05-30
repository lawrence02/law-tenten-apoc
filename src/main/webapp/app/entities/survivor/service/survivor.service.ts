import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISurvivor, NewSurvivor } from '../survivor.model';

export type PartialUpdateSurvivor = Partial<ISurvivor> & Pick<ISurvivor, 'id'>;

export type EntityResponseType = HttpResponse<ISurvivor>;
export type EntityArrayResponseType = HttpResponse<ISurvivor[]>;

@Injectable({ providedIn: 'root' })
export class SurvivorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/survivors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(survivor: NewSurvivor): Observable<EntityResponseType> {
    return this.http.post<ISurvivor>(this.resourceUrl, survivor, { observe: 'response' });
  }

  update(survivor: ISurvivor): Observable<EntityResponseType> {
    return this.http.put<ISurvivor>(`${this.resourceUrl}/${this.getSurvivorIdentifier(survivor)}`, survivor, { observe: 'response' });
  }

  partialUpdate(survivor: PartialUpdateSurvivor): Observable<EntityResponseType> {
    return this.http.patch<ISurvivor>(`${this.resourceUrl}/${this.getSurvivorIdentifier(survivor)}`, survivor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISurvivor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISurvivor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSurvivorIdentifier(survivor: Pick<ISurvivor, 'id'>): number {
    return survivor.id;
  }

  compareSurvivor(o1: Pick<ISurvivor, 'id'> | null, o2: Pick<ISurvivor, 'id'> | null): boolean {
    return o1 && o2 ? this.getSurvivorIdentifier(o1) === this.getSurvivorIdentifier(o2) : o1 === o2;
  }

  addSurvivorToCollectionIfMissing<Type extends Pick<ISurvivor, 'id'>>(
    survivorCollection: Type[],
    ...survivorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const survivors: Type[] = survivorsToCheck.filter(isPresent);
    if (survivors.length > 0) {
      const survivorCollectionIdentifiers = survivorCollection.map(survivorItem => this.getSurvivorIdentifier(survivorItem)!);
      const survivorsToAdd = survivors.filter(survivorItem => {
        const survivorIdentifier = this.getSurvivorIdentifier(survivorItem);
        if (survivorCollectionIdentifiers.includes(survivorIdentifier)) {
          return false;
        }
        survivorCollectionIdentifiers.push(survivorIdentifier);
        return true;
      });
      return [...survivorsToAdd, ...survivorCollection];
    }
    return survivorCollection;
  }
}
