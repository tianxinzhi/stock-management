import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStockTransaction, StockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from './stock-transaction.service';
import { IStockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from 'app/entities/stock-item/stock-item.service';

@Component({
  selector: 'jhi-stock-transaction-update',
  templateUrl: './stock-transaction-update.component.html'
})
export class StockTransactionUpdateComponent implements OnInit {
  isSaving = false;
  stockitems: IStockItem[] = [];

  editForm = this.fb.group({
    id: [],
    subinventoryFrom: [],
    subinventoryTo: [],
    transactionType: [null, [Validators.required]],
    transactionQuantity: [null, [Validators.required]],
    reference: [null, [Validators.maxLength(20)]],
    stockItem: [null, Validators.required]
  });

  constructor(
    protected stockTransactionService: StockTransactionService,
    protected stockItemService: StockItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockTransaction }) => {
      this.updateForm(stockTransaction);

      this.stockItemService.query().subscribe((res: HttpResponse<IStockItem[]>) => (this.stockitems = res.body || []));
    });
  }

  updateForm(stockTransaction: IStockTransaction): void {
    this.editForm.patchValue({
      id: stockTransaction.id,
      subinventoryFrom: stockTransaction.subinventoryFrom,
      subinventoryTo: stockTransaction.subinventoryTo,
      transactionType: stockTransaction.transactionType,
      transactionQuantity: stockTransaction.transactionQuantity,
      reference: stockTransaction.reference,
      stockItem: stockTransaction.stockItem
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockTransaction = this.createFromForm();
    if (stockTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.stockTransactionService.update(stockTransaction));
    } else {
      this.subscribeToSaveResponse(this.stockTransactionService.create(stockTransaction));
    }
  }

  private createFromForm(): IStockTransaction {
    return {
      ...new StockTransaction(),
      id: this.editForm.get(['id'])!.value,
      subinventoryFrom: this.editForm.get(['subinventoryFrom'])!.value,
      subinventoryTo: this.editForm.get(['subinventoryTo'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      transactionQuantity: this.editForm.get(['transactionQuantity'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      stockItem: this.editForm.get(['stockItem'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockTransaction>>): void {
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
