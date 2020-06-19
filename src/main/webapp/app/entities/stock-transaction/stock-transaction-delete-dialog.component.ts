import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from './stock-transaction.service';

@Component({
  templateUrl: './stock-transaction-delete-dialog.component.html'
})
export class StockTransactionDeleteDialogComponent {
  stockTransaction?: IStockTransaction;

  constructor(
    protected stockTransactionService: StockTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockTransactionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stockTransactionListModification');
      this.activeModal.close();
    });
  }
}
