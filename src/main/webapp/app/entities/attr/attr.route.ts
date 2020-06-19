import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttr, Attr } from 'app/shared/model/attr.model';
import { AttrService } from './attr.service';
import { AttrComponent } from './attr.component';
import { AttrDetailComponent } from './attr-detail.component';
import { AttrUpdateComponent } from './attr-update.component';

@Injectable({ providedIn: 'root' })
export class AttrResolve implements Resolve<IAttr> {
  constructor(private service: AttrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attr: HttpResponse<Attr>) => {
          if (attr.body) {
            return of(attr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Attr());
  }
}

export const attrRoute: Routes = [
  {
    path: '',
    component: AttrComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttrDetailComponent,
    resolve: {
      attr: AttrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttrUpdateComponent,
    resolve: {
      attr: AttrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttrUpdateComponent,
    resolve: {
      attr: AttrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'stockManagementApp.attr.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
