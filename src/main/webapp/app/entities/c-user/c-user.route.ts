import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { C_userComponent } from './c-user.component';
import { C_userDetailComponent } from './c-user-detail.component';
import { C_userPopupComponent } from './c-user-dialog.component';
import { C_userDeletePopupComponent } from './c-user-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class C_userResolvePagingParams implements Resolve<any> {

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

export const c_userRoute: Routes = [
    {
        path: 'c-user',
        component: C_userComponent,
        resolve: {
            'pagingParams': C_userResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'C_users'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'c-user/:id',
        component: C_userDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'C_users'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const c_userPopupRoute: Routes = [
    {
        path: 'c-user-new',
        component: C_userPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'C_users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'c-user/:id/edit',
        component: C_userPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'C_users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'c-user/:id/delete',
        component: C_userDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'C_users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
