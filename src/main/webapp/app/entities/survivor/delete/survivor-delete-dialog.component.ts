import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISurvivor } from '../survivor.model';
import { SurvivorService } from '../service/survivor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './survivor-delete-dialog.component.html',
})
export class SurvivorDeleteDialogComponent {
  survivor?: ISurvivor;

  constructor(protected survivorService: SurvivorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.survivorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
