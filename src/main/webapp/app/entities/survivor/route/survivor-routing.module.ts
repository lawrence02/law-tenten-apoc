import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SurvivorComponent } from '../list/survivor.component';
import { SurvivorDetailComponent } from '../detail/survivor-detail.component';
import { SurvivorUpdateComponent } from '../update/survivor-update.component';
import { SurvivorRoutingResolveService } from './survivor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const survivorRoute: Routes = [
  {
    path: '',
    component: SurvivorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SurvivorDetailComponent,
    resolve: {
      survivor: SurvivorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SurvivorUpdateComponent,
    resolve: {
      survivor: SurvivorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SurvivorUpdateComponent,
    resolve: {
      survivor: SurvivorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(survivorRoute)],
  exports: [RouterModule],
})
export class SurvivorRoutingModule {}
