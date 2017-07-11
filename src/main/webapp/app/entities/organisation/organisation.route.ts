import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrganisationComponent } from './organisation.component';
import { OrganisationDetailComponent } from './organisation-detail.component';
import { OrganisationPopupComponent } from './organisation-dialog.component';
import { OrganisationDeletePopupComponent } from './organisation-delete-dialog.component';

import { Principal } from '../../shared';

export const organisationRoute: Routes = [
    {
        path: 'organisation',
        component: OrganisationComponent,
        data: {
            authorities: ['ROLE_ORG_ADMIN', 'ROLE_ADMIN'],
            pageTitle: 'Organisations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'organisation/:id',
        component: OrganisationDetailComponent,
        data: {
            authorities: ['ROLE_ORG_ADMIN', 'ROLE_ADMIN'],
            pageTitle: 'Organisations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organisationPopupRoute: Routes = [
    {
        path: 'organisation-new',
        component: OrganisationPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Organisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisation/:id/edit',
        component: OrganisationPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Organisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisation/:id/delete',
        component: OrganisationDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Organisations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
