import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStockItem, StockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from './stock-item.service';

@Component({
  selector: 'jhi-stock-item-update',
  templateUrl: './stock-item-update.component.html'
})
export class StockItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    itemNumber: [null, [Validators.required, Validators.maxLength(20)]],
    itemDescription: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(protected stockItemService: StockItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockItem }) => {
      this.updateForm(stockItem);
    });
  }

  updateForm(stockItem: IStockItem): void {
    this.editForm.patchValue({
      id: stockItem.id,
      itemNumber: stockItem.itemNumber,
      itemDescription: stockItem.itemDescription
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockItem = this.createFromForm();
    if (stockItem.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemService.update(stockItem));
    } else {
      this.subscribeToSaveResponse(this.stockItemService.create(stockItem));
    }
  }

  private createFromForm(): IStockItem {
    return {
      ...new StockItem(),
      id: this.editForm.get(['id'])!.value,
      itemNumber: this.editForm.get(['itemNumber'])!.value,
      itemDescription: this.editForm.get(['itemDescription'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItem>>): void {
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
}
