import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Key } from './key.model';
import { KeyService } from './key.service';

@Component({
    selector: 'jhi-key-detail',
    templateUrl: './key-detail.component.html'
})
export class KeyDetailComponent implements OnInit, OnDestroy {

    key: Key;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private keyService: KeyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKeys();
    }

    load(id) {
        this.keyService.find(id).subscribe((key) => {
            this.key = key;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKeys() {
        this.eventSubscriber = this.eventManager.subscribe(
            'keyListModification',
            (response) => this.load(this.key.id)
        );
    }
}
