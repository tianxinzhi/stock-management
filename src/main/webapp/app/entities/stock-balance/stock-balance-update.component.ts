import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStockBalance, StockBalance } from 'app/shared/model/stock-balance.model';
import { StockBalanceService } from './stock-balance.service';
import { IStockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from 'app/entities/stock-item/stock-item.service';

@Component({
  selector: 'jhi-stock-balance-update',
  templateUrl: './stock-balance-update.component.html'
})
export class StockBalanceUpdateComponent implements OnInit {
  isSaving = false;
  stockitems: IStockItem[] = [];

  editForm = this.fb.group({
    id: [],
    subInventory: [null, [Validators.required]],
    quantityOnhand: [null, [Validators.required]],
    quantityReserved: [null, [Validators.required]],
    stockItem: [null, Validators.required]
  });

  constructor(
    protected stockBalanceService: StockBalanceService,
    protected stockItemService: StockItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockBalance }) => {
      this.updateForm(stockBalance);

      this.stockItemService.query().subscribe((res: HttpResponse<IStockItem[]>) => (this.stockitems = res.body || []));
    });
  }

  updateForm(stockBalance: IStockBalance): void {
    this.editForm.patchValue({
      id: stockBalance.id,
      subInventory: stockBalance.subInventory,
      quantityOnhand: stockBalance.quantityOnhand,
      quantityReserved: stockBalance.quantityReserved,
      stockItem: stockBalance.stockItem
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockBalance = this.createFromForm();
    if (stockBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.stockBalanceService.update(stockBalance));
    } else {
      this.subscribeToSaveResponse(this.stockBalanceService.create(stockBalance));
    }
  }

  private createFromForm(): IStockBalance {
    return {
      ...new StockBalance(),
      id: this.editForm.get(['id'])!.value,
      subInventory: this.editForm.get(['subInventory'])!.value,
      quantityOnhand: this.editForm.get(['quantityOnhand'])!.value,
      quantityReserved: this.editForm.get(['quantityReserved'])!.value,
      stockItem: this.editForm.get(['stockItem'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockBalance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStockItem): any {
    return item.id;
  }
}
