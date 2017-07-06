import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CawnappSharedModule } from '../../shared';
import {
    StageService,
    StagePopupService,
    StageComponent,
    StageDetailComponent,
    StageDialogComponent,
    StagePopupComponent,
    StageDeletePopupComponent,
    StageDeleteDialogComponent,
    stageRoute,
    stagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...stageRoute,
    ...stagePopupRoute,
];

@NgModule({
    imports: [
        CawnappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StageComponent,
        StageDetailComponent,
        StageDialogComponent,
        StageDeleteDialogComponent,
        StagePopupComponent,
        StageDeletePopupComponent,
    ],
    entryComponents: [
        StageComponent,
        StageDialogComponent,
        StagePopupComponent,
        StageDeleteDialogComponent,
        StageDeletePopupComponent,
    ],
    providers: [
        StageService,
        StagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappStageModule {}
