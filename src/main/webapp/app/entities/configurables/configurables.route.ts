import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ConfigurablesComponent } from './configurables.component';
import { ConfigurablesDetailComponent } from './configurables-detail.component';
import { ConfigurablesPopupComponent } from './configurables-dialog.component';
import { ConfigurablesDeletePopupComponent } from './configurables-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ConfigurablesResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const configurablesRoute: Routes = [
    {
        path: 'configurables',
        component: ConfigurablesComponent,
        resolve: {
            'pagingParams': ConfigurablesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configurables'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'configurables/:id',
        component: ConfigurablesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configurables'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const configurablesPopupRoute: Routes = [
    {
        path: 'configurables-new',
        component: ConfigurablesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configurables'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configurables/:id/edit',
        component: ConfigurablesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configurables'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'configurables/:id/delete',
        component: ConfigurablesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Configurables'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
