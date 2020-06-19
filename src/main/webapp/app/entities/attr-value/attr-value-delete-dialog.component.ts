import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttrValue } from 'app/shared/model/attr-value.model';
import { AttrValueService } from './attr-value.service';

@Component({
  templateUrl: './attr-value-delete-dialog.component.html'
})
export class AttrValueDeleteDialogComponent {
  attrValue?: IAttrValue;

  constructor(protected attrValueService: AttrValueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attrValueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attrValueListModification');
      this.activeModal.close();
    });
  }
}
