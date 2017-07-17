import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CawnappSharedModule } from '../../shared';
import { CawnappAdminModule } from '../../admin/admin.module';
import {
    C_userService,
    C_userPopupService,
    C_userComponent,
    C_userDetailComponent,
    C_userDialogComponent,
    C_userPopupComponent,
    C_userDeletePopupComponent,
    C_userDeleteDialogComponent,
    c_userRoute,
    c_userPopupRoute,
    C_userResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...c_userRoute,
    ...c_userPopupRoute,
];

@NgModule({
    imports: [
        CawnappSharedModule,
        CawnappAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        C_userComponent,
        C_userDetailComponent,
        C_userDialogComponent,
        C_userDeleteDialogComponent,
        C_userPopupComponent,
        C_userDeletePopupComponent,
    ],
    entryComponents: [
        C_userComponent,
        C_userDialogComponent,
        C_userPopupComponent,
        C_userDeleteDialogComponent,
        C_userDeletePopupComponent,
    ],
    providers: [
        C_userService,
        C_userPopupService,
        C_userResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappC_userModule {}
