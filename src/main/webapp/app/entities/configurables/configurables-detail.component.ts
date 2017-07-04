import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Configurables } from './configurables.model';
import { ConfigurablesService } from './configurables.service';

@Component({
    selector: 'jhi-configurables-detail',
    templateUrl: './configurables-detail.component.html'
})
export class ConfigurablesDetailComponent implements OnInit, OnDestroy {

    configurables: Configurables;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private configurablesService: ConfigurablesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConfigurables();
    }

    load(id) {
        this.configurablesService.find(id).subscribe((configurables) => {
            this.configurables = configurables;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConfigurables() {
        this.eventSubscriber = this.eventManager.subscribe(
            'configurablesListModification',
            (response) => this.load(this.configurables.id)
        );
    }
}
