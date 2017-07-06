import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CawnappSharedModule } from '../../shared';
import {
    KeyService,
    KeyPopupService,
    KeyComponent,
    KeyDetailComponent,
    KeyDialogComponent,
    KeyPopupComponent,
    KeyDeletePopupComponent,
    KeyDeleteDialogComponent,
    keyRoute,
    keyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...keyRoute,
    ...keyPopupRoute,
];

@NgModule({
    imports: [
        CawnappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KeyComponent,
        KeyDetailComponent,
        KeyDialogComponent,
        KeyDeleteDialogComponent,
        KeyPopupComponent,
        KeyDeletePopupComponent,
    ],
    entryComponents: [
        KeyComponent,
        KeyDialogComponent,
        KeyPopupComponent,
        KeyDeleteDialogComponent,
        KeyDeletePopupComponent,
    ],
    providers: [
        KeyService,
        KeyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappKeyModule {}
