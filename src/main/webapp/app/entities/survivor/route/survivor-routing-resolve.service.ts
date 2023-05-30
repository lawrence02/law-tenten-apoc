import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISurvivor } from '../survivor.model';
import { SurvivorService } from '../service/survivor.service';

@Injectable({ providedIn: 'root' })
export class SurvivorRoutingResolveService implements Resolve<ISurvivor | null> {
  constructor(protected service: SurvivorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISurvivor | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((survivor: HttpResponse<ISurvivor>) => {
          if (survivor.body) {
            return of(survivor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
