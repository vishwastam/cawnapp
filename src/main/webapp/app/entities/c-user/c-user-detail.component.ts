import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { C_user } from './c-user.model';
import { C_userService } from './c-user.service';

@Component({
    selector: 'jhi-c-user-detail',
    templateUrl: './c-user-detail.component.html'
})
export class C_userDetailComponent implements OnInit, OnDestroy {

    c_user: C_user;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private c_userService: C_userService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInC_users();
    }

    load(id) {
        this.c_userService.find(id).subscribe((c_user) => {
            this.c_user = c_user;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInC_users() {
        this.eventSubscriber = this.eventManager.subscribe(
            'c_userListModification',
            (response) => this.load(this.c_user.id)
        );
    }
}
