import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStockTransaction, StockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from './stock-transaction.service';
import { StockTransactionComponent } from './stock-transaction.component';
import { StockTransactionDetailComponent } from './stock-transaction-detail.component';
import { StockTransactionUpdateComponent } from './stock-transaction-update.component';

@Injectable({ providedIn: 'root' })
export class StockTransactionResolve implements Resolve<IStockTransaction> {
  constructor(private service: StockTransactionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockTransaction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stockTransaction: HttpResponse<StockTransaction>) => {
          if (stockTransaction.body) {
            return of(stockTransaction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StockTransaction());
  }
}

export const stockTransactionRoute: Routes = [
  {
    path: '',
    component: StockTransactionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'stockManagementApp.stockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockTransactionDetailComponent,
    resolve: {
      stockTransaction: StockTransactionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockTransactionUpdateComponent,
    resolve: {
      stockTransaction: StockTransactionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockTransactionUpdateComponent,
    resolve: {
      stockTransaction: StockTransactionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.stockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
