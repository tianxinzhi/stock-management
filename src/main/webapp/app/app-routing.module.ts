import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/shared/constants/authority.constants';
import {NzDemoSelectMultipleComponent} from "app/entities/attr/attrMulti.component";
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {FormsModule} from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {NzSelectModule} from "ng-zorro-antd";

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN]
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule)
        },
        ...LAYOUT_ROUTES
      ],
      {enableTracing: DEBUG_INFO_ENABLED}
    ),
    FormsModule,
    NzSelectModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    BrowserModule
  ],
  exports: [RouterModule],
  declarations: [
    NzDemoSelectMultipleComponent,
  ]
})
export class StockManagementAppRoutingModule {}
