import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStockItem, StockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from './stock-item.service';
import { StockItemComponent } from './stock-item.component';
import { StockItemDetailComponent } from './stock-item-detail.component';
import { StockItemUpdateComponent } from './stock-item-update.component';

@Injectable({ providedIn: 'root' })
export class StockItemResolve implements Resolve<IStockItem> {
  constructor(private service: StockItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stockItem: HttpResponse<StockItem>) => {
          if (stockItem.body) {
            return of(stockItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StockItem());
  }
}

export const stockItemRoute: Routes = [
  {
    path: '',
    component: StockItemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'stockManagementApp.stockItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockItemDetailComponent,
    resolve: {
      stockItem: StockItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockItemUpdateComponent,
    resolve: {
      stockItem: StockItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockItemUpdateComponent,
    resolve: {
      stockItem: StockItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
