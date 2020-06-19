import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { AttrComponent } from './attr.component';
import { AttrDetailComponent } from './attr-detail.component';
import { AttrUpdateComponent } from './attr-update.component';
import { AttrDeleteDialogComponent } from './attr-delete-dialog.component';
import { attrRoute } from './attr.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(attrRoute)],
  declarations: [AttrComponent, AttrDetailComponent, AttrUpdateComponent, AttrDeleteDialogComponent],
  entryComponents: [AttrDeleteDialogComponent]
})
export class StockManagementAttrModule {}
