import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'stock-item',
        loadChildren: () => import('./stock-item/stock-item.module').then(m => m.StockManagementStockItemModule)
      },
      {
        path: 'stock-balance',
        loadChildren: () => import('./stock-balance/stock-balance.module').then(m => m.StockManagementStockBalanceModule)
      },
      {
        path: 'stock-transaction',
        loadChildren: () => import('./stock-transaction/stock-transaction.module').then(m => m.StockManagementStockTransactionModule)
      },
      {
        path: 'attr-value',
        loadChildren: () => import('./attr-value/attr-value.module').then(m => m.StockManagementAttrValueModule)
      },
      {
        path: 'attr',
        loadChildren: () => import('./attr/attr.module').then(m => m.StockManagementAttrModule)
      },
      {
        path: 'spec',
        loadChildren: () => import('./spec/spec.module').then(m => m.StockManagementSpecModule)
      },
      {
        path: 'type',
        loadChildren: () => import('./type/type.module').then(m => m.StockManagementTypeModule)
      },
      {
        path: 'sku',
        loadChildren: () => import('./sku/sku.module').then(m => m.StockManagementSkuModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class StockManagementEntityModule {}
