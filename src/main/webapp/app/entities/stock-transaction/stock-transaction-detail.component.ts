import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockTransaction } from 'app/shared/model/stock-transaction.model';

@Component({
  selector: 'jhi-stock-transaction-detail',
  templateUrl: './stock-transaction-detail.component.html'
})
export class StockTransactionDetailComponent implements OnInit {
  stockTransaction: IStockTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockTransaction }) => (this.stockTransaction = stockTransaction));
  }

  previousState(): void {
    window.history.back();
  }
}
