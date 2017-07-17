import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { C_user } from './c-user.model';
import { C_userPopupService } from './c-user-popup.service';
import { C_userService } from './c-user.service';
import { User, UserService } from '../../shared';
import { Organisation, OrganisationService } from '../organisation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-c-user-dialog',
    templateUrl: './c-user-dialog.component.html'
})
export class C_userDialogComponent implements OnInit {

    c_user: C_user;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    organisations: Organisation[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private c_userService: C_userService,
        private userService: UserService,
        private organisationService: OrganisationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organisationService.query()
            .subscribe((res: ResponseWrapper) => { this.organisations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.c_user.id !== undefined) {
            this.subscribeToSaveResponse(
                this.c_userService.update(this.c_user), false);
        } else {
            this.subscribeToSaveResponse(
                this.c_userService.create(this.c_user), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<C_user>, isCreated: boolean) {
        result.subscribe((res: C_user) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: C_user, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new C User is created with identifier ${result.id}`
            : `A C User is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'c_userListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackOrganisationById(index: number, item: Organisation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-c-user-popup',
    template: ''
})
export class C_userPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private c_userPopupService: C_userPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.c_userPopupService
                    .open(C_userDialogComponent, params['id']);
            } else {
                this.modalRef = this.c_userPopupService
                    .open(C_userDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
