import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockBalance } from 'app/shared/model/stock-balance.model';
import { StockBalanceService } from './stock-balance.service';

@Component({
  templateUrl: './stock-balance-delete-dialog.component.html'
})
export class StockBalanceDeleteDialogComponent {
  stockBalance?: IStockBalance;

  constructor(
    protected stockBalanceService: StockBalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockBalanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stockBalanceListModification');
      this.activeModal.close();
    });
  }
}
