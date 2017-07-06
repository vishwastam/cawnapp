import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ApplicationComponent } from './application.component';
import { ApplicationDetailComponent } from './application-detail.component';
import { ApplicationPopupComponent } from './application-dialog.component';
import { ApplicationDeletePopupComponent } from './application-delete-dialog.component';

import { Principal } from '../../shared';

export const applicationRoute: Routes = [
    {
        path: 'application',
        component: ApplicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'application/:id',
        component: ApplicationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationPopupRoute: Routes = [
    {
        path: 'application-new',
        component: ApplicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application/:id/edit',
        component: ApplicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application/:id/delete',
        component: ApplicationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
