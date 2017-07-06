import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CawnappConfigurablesModule } from './configurables/configurables.module';
import { CawnappKeyModule } from './key/key.module';
import { CawnappOrganisationModule } from './organisation/organisation.module';
import { CawnappApplicationModule } from './application/application.module';
import { CawnappStageModule } from './stage/stage.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CawnappConfigurablesModule,
        CawnappKeyModule,
        CawnappOrganisationModule,
        CawnappApplicationModule,
        CawnappStageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CawnappEntityModule {}
