import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttr } from 'app/shared/model/attr.model';
import { AttrService } from './attr.service';

@Component({
  templateUrl: './attr-delete-dialog.component.html'
})
export class AttrDeleteDialogComponent {
  attr?: IAttr;

  constructor(protected attrService: AttrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attrService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attrListModification');
      this.activeModal.close();
    });
  }
}
