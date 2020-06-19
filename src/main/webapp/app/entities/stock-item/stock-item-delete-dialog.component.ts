import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from './stock-item.service';

@Component({
  templateUrl: './stock-item-delete-dialog.component.html'
})
export class StockItemDeleteDialogComponent {
  stockItem?: IStockItem;

  constructor(protected stockItemService: StockItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stockItemListModification');
      this.activeModal.close();
    });
  }
}
