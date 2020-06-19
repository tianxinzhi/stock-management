import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { StockTransactionComponent } from './stock-transaction.component';
import { StockTransactionDetailComponent } from './stock-transaction-detail.component';
import { StockTransactionUpdateComponent } from './stock-transaction-update.component';
import { StockTransactionDeleteDialogComponent } from './stock-transaction-delete-dialog.component';
import { stockTransactionRoute } from './stock-transaction.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(stockTransactionRoute)],
  declarations: [
    StockTransactionComponent,
    StockTransactionDetailComponent,
    StockTransactionUpdateComponent,
    StockTransactionDeleteDialogComponent
  ],
  entryComponents: [StockTransactionDeleteDialogComponent]
})
export class StockManagementStockTransactionModule {}
