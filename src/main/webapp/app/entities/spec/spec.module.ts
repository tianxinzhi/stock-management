import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockManagementSharedModule } from 'app/shared/shared.module';
import { SpecComponent } from './spec.component';
import { SpecDetailComponent } from './spec-detail.component';
import { SpecUpdateComponent } from './spec-update.component';
import { SpecDeleteDialogComponent } from './spec-delete-dialog.component';
import { specRoute } from './spec.route';

@NgModule({
  imports: [StockManagementSharedModule, RouterModule.forChild(specRoute)],
  declarations: [SpecComponent, SpecDetailComponent, SpecUpdateComponent, SpecDeleteDialogComponent],
  entryComponents: [SpecDeleteDialogComponent]
})
export class StockManagementSpecModule {}
