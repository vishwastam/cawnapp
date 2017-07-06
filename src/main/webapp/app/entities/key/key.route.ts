import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { KeyComponent } from './key.component';
import { KeyDetailComponent } from './key-detail.component';
import { KeyPopupComponent } from './key-dialog.component';
import { KeyDeletePopupComponent } from './key-delete-dialog.component';

import { Principal } from '../../shared';

export const keyRoute: Routes = [
    {
        path: 'key',
        component: KeyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Keys'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'key/:id',
        component: KeyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Keys'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const keyPopupRoute: Routes = [
    {
        path: 'key-new',
        component: KeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Keys'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key/:id/edit',
        component: KeyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Keys'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key/:id/delete',
        component: KeyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Keys'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
