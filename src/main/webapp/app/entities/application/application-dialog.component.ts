import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Application } from './application.model';
import { ApplicationPopupService } from './application-popup.service';
import { ApplicationService } from './application.service';
import { Organisation, OrganisationService } from '../organisation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-application-dialog',
    templateUrl: './application-dialog.component.html'
})
export class ApplicationDialogComponent implements OnInit {

    application: Application;
    authorities: any[];
    isSaving: boolean;

    organisations: Organisation[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private applicationService: ApplicationService,
        private organisationService: OrganisationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.organisationService.query()
            .subscribe((res: ResponseWrapper) => { this.organisations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.application.id !== undefined) {
            this.subscribeToSaveResponse(
                this.applicationService.update(this.application), false);
        } else {
            this.subscribeToSaveResponse(
                this.applicationService.create(this.application), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Application>, isCreated: boolean) {
        result.subscribe((res: Application) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Application, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Application is created with identifier ${result.id}`
            : `A Application is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'applicationListModification', content: 'OK'});
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

    trackOrganisationById(index: number, item: Organisation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-application-popup',
    template: ''
})
export class ApplicationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationPopupService: ApplicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.applicationPopupService
                    .open(ApplicationDialogComponent, params['id']);
            } else {
                this.modalRef = this.applicationPopupService
                    .open(ApplicationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
