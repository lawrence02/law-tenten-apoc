import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'survivor',
        data: { pageTitle: 'tenTenApocLawApp.survivor.home.title' },
        loadChildren: () => import('./survivor/survivor.module').then(m => m.SurvivorModule),
      },
      {
        path: 'resource',
        data: { pageTitle: 'tenTenApocLawApp.resource.home.title' },
        loadChildren: () => import('./resource/resource.module').then(m => m.ResourceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
