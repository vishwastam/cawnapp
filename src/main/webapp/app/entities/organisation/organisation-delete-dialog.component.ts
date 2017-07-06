import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Organisation } from './organisation.model';
import { OrganisationPopupService } from './organisation-popup.service';
import { OrganisationService } from './organisation.service';

@Component({
    selector: 'jhi-organisation-delete-dialog',
    templateUrl: './organisation-delete-dialog.component.html'
})
export class OrganisationDeleteDialogComponent {

    organisation: Organisation;

    constructor(
        private organisationService: OrganisationService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organisationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'organisationListModification',
                content: 'Deleted an organisation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Organisation is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-organisation-delete-popup',
    template: ''
})
export class OrganisationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationPopupService: OrganisationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.organisationPopupService
                .open(OrganisationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
