import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Key } from './key.model';
import { KeyPopupService } from './key-popup.service';
import { KeyService } from './key.service';

@Component({
    selector: 'jhi-key-delete-dialog',
    templateUrl: './key-delete-dialog.component.html'
})
export class KeyDeleteDialogComponent {

    key: Key;

    constructor(
        private keyService: KeyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.keyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'keyListModification',
                content: 'Deleted an key'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Key is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-key-delete-popup',
    template: ''
})
export class KeyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyPopupService: KeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.keyPopupService
                .open(KeyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
