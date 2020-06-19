import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttrValue, AttrValue } from 'app/shared/model/attr-value.model';
import { AttrValueService } from './attr-value.service';
import { AttrValueComponent } from './attr-value.component';
import { AttrValueDetailComponent } from './attr-value-detail.component';
import { AttrValueUpdateComponent } from './attr-value-update.component';

@Injectable({ providedIn: 'root' })
export class AttrValueResolve implements Resolve<IAttrValue> {
  constructor(private service: AttrValueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttrValue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attrValue: HttpResponse<AttrValue>) => {
          if (attrValue.body) {
            return of(attrValue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttrValue());
  }
}

export const attrValueRoute: Routes = [
  {
    path: '',
    component: AttrValueComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attrValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttrValueDetailComponent,
    resolve: {
      attrValue: AttrValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attrValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttrValueUpdateComponent,
    resolve: {
      attrValue: AttrValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attrValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttrValueUpdateComponent,
    resolve: {
      attrValue: AttrValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attrValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
