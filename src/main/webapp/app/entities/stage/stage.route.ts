import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { StageComponent } from './stage.component';
import { StageDetailComponent } from './stage-detail.component';
import { StagePopupComponent } from './stage-dialog.component';
import { StageDeletePopupComponent } from './stage-delete-dialog.component';

import { Principal } from '../../shared';

export const stageRoute: Routes = [
    {
        path: 'stage',
        component: StageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stage/:id',
        component: StageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stagePopupRoute: Routes = [
    {
        path: 'stage-new',
        component: StagePopupComponent,
        data: {
            authorities: ['ROLE_ORG_ADMIN', 'ROLE_ADMIN'],
            pageTitle: 'Stages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stage/:id/edit',
        component: StagePopupComponent,
        data: {
            authorities: ['ROLE_ORG_ADMIN', 'ROLE_ADMIN'],
            pageTitle: 'Stages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stage/:id/delete',
        component: StageDeletePopupComponent,
        data: {
            authorities: ['ROLE_ORG_ADMIN', 'ROLE_ADMIN'],
            pageTitle: 'Stages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
