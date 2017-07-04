import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CawnappSharedModule } from '../../shared';
import {
    ConfigurablesService,
    ConfigurablesPopupService,
    ConfigurablesComponent,
    ConfigurablesDetailComponent,
    ConfigurablesDialogComponent,
    ConfigurablesPopupComponent,
    ConfigurablesDeletePopupComponent,
    ConfigurablesDeleteDialogComponent,
    configurablesRoute,
    configurablesPopupRoute,
    ConfigurablesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...configurablesRoute,
    ...configurablesPopupRoute,
];

@NgModule({
    imports: [
        CawnappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConfigurablesComponent,
        ConfigurablesDetailComponent,
        ConfigurablesDialogComponent,
        ConfigurablesDeleteDialogComponent,
        ConfigurablesPopupComponent,
        ConfigurablesDeletePopupComponent,
    ],
    entryComponents: [
        ConfigurablesComponent,
        ConfigurablesDialogComponent,
        ConfigurablesPopupComponent,
        ConfigurablesDeleteDialogComponent,
        ConfigurablesDeletePopupComponent,
    ],
    providers: [
        ConfigurablesService,
        ConfigurablesPopupService,
        ConfigurablesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappConfigurablesModule {}
