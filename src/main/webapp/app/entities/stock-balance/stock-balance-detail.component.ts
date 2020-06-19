import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockBalance } from 'app/shared/model/stock-balance.model';

@Component({
  selector: 'jhi-stock-balance-detail',
  templateUrl: './stock-balance-detail.component.html'
})
export class StockBalanceDetailComponent implements OnInit {
  stockBalance: IStockBalance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockBalance }) => (this.stockBalance = stockBalance));
  }

  previousState(): void {
    window.history.back();
  }
}
