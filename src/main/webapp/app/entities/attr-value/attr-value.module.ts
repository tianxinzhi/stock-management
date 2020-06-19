import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { AttrValueComponent } from './attr-value.component';
import { AttrValueDetailComponent } from './attr-value-detail.component';
import { AttrValueUpdateComponent } from './attr-value-update.component';
import { AttrValueDeleteDialogComponent } from './attr-value-delete-dialog.component';
import { attrValueRoute } from './attr-value.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(attrValueRoute)],
  declarations: [AttrValueComponent, AttrValueDetailComponent, AttrValueUpdateComponent, AttrValueDeleteDialogComponent],
  entryComponents: [AttrValueDeleteDialogComponent]
})
export class StockManagementAttrValueModule {}
