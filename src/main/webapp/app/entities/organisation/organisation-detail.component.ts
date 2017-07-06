import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Organisation } from './organisation.model';
import { OrganisationService } from './organisation.service';

@Component({
    selector: 'jhi-organisation-detail',
    templateUrl: './organisation-detail.component.html'
})
export class OrganisationDetailComponent implements OnInit, OnDestroy {

    organisation: Organisation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private organisationService: OrganisationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrganisations();
    }

    load(id) {
        this.organisationService.find(id).subscribe((organisation) => {
            this.organisation = organisation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrganisations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'organisationListModification',
            (response) => this.load(this.organisation.id)
        );
    }
}
