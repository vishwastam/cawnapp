import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { C_user } from './c-user.model';
import { C_userPopupService } from './c-user-popup.service';
import { C_userService } from './c-user.service';

@Component({
    selector: 'jhi-c-user-delete-dialog',
    templateUrl: './c-user-delete-dialog.component.html'
})
export class C_userDeleteDialogComponent {

    c_user: C_user;

    constructor(
        private c_userService: C_userService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.c_userService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'c_userListModification',
                content: 'Deleted an c_user'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A C User is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-c-user-delete-popup',
    template: ''
})
export class C_userDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private c_userPopupService: C_userPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.c_userPopupService
                .open(C_userDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
