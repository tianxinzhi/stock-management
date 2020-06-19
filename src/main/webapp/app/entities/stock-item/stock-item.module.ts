import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { StockItemComponent } from './stock-item.component';
import { StockItemDetailComponent } from './stock-item-detail.component';
import { StockItemUpdateComponent } from './stock-item-update.component';
import { StockItemDeleteDialogComponent } from './stock-item-delete-dialog.component';
import { stockItemRoute } from './stock-item.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(stockItemRoute)],
  declarations: [StockItemComponent, StockItemDetailComponent, StockItemUpdateComponent, StockItemDeleteDialogComponent],
  entryComponents: [StockItemDeleteDialogComponent]
})
export class StockManagementStockItemModule {}
