import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Organisation } from './organisation.model';
import { OrganisationPopupService } from './organisation-popup.service';
import { OrganisationService } from './organisation.service';

@Component({
    selector: 'jhi-organisation-dialog',
    templateUrl: './organisation-dialog.component.html'
})
export class OrganisationDialogComponent implements OnInit {

    organisation: Organisation;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private organisationService: OrganisationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.organisation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.organisationService.update(this.organisation), false);
        } else {
            this.subscribeToSaveResponse(
                this.organisationService.create(this.organisation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Organisation>, isCreated: boolean) {
        result.subscribe((res: Organisation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Organisation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Organisation is created with identifier ${result.id}`
            : `A Organisation is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'organisationListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-organisation-popup',
    template: ''
})
export class OrganisationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationPopupService: OrganisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.organisationPopupService
                    .open(OrganisationDialogComponent, params['id']);
            } else {
                this.modalRef = this.organisationPopupService
                    .open(OrganisationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
