import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { StockBalanceComponent } from './stock-balance.component';
import { StockBalanceDetailComponent } from './stock-balance-detail.component';
import { StockBalanceUpdateComponent } from './stock-balance-update.component';
import { StockBalanceDeleteDialogComponent } from './stock-balance-delete-dialog.component';
import { stockBalanceRoute } from './stock-balance.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(stockBalanceRoute)],
  declarations: [StockBalanceComponent, StockBalanceDetailComponent, StockBalanceUpdateComponent, StockBalanceDeleteDialogComponent],
  entryComponents: [StockBalanceDeleteDialogComponent]
})
export class StockManagementStockBalanceModule {}
