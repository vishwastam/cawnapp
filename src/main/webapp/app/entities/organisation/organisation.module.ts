import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CawnappSharedModule } from '../../shared';
import {
    OrganisationService,
    OrganisationPopupService,
    OrganisationComponent,
    OrganisationDetailComponent,
    OrganisationDialogComponent,
    OrganisationPopupComponent,
    OrganisationDeletePopupComponent,
    OrganisationDeleteDialogComponent,
    organisationRoute,
    organisationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...organisationRoute,
    ...organisationPopupRoute,
];

@NgModule({
    imports: [
        CawnappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrganisationComponent,
        OrganisationDetailComponent,
        OrganisationDialogComponent,
        OrganisationDeleteDialogComponent,
        OrganisationPopupComponent,
        OrganisationDeletePopupComponent,
    ],
    entryComponents: [
        OrganisationComponent,
        OrganisationDialogComponent,
        OrganisationPopupComponent,
        OrganisationDeleteDialogComponent,
        OrganisationDeletePopupComponent,
    ],
    providers: [
        OrganisationService,
        OrganisationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappOrganisationModule {}
