import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SurvivorComponent } from './list/survivor.component';
import { SurvivorDetailComponent } from './detail/survivor-detail.component';
import { SurvivorUpdateComponent } from './update/survivor-update.component';
import { SurvivorDeleteDialogComponent } from './delete/survivor-delete-dialog.component';
import { SurvivorRoutingModule } from './route/survivor-routing.module';

@NgModule({
  imports: [SharedModule, SurvivorRoutingModule],
  declarations: [SurvivorComponent, SurvivorDetailComponent, SurvivorUpdateComponent, SurvivorDeleteDialogComponent],
})
export class SurvivorModule {}
