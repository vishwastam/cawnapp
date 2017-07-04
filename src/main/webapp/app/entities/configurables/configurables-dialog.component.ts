import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Configurables } from './configurables.model';
import { ConfigurablesPopupService } from './configurables-popup.service';
import { ConfigurablesService } from './configurables.service';

@Component({
    selector: 'jhi-configurables-dialog',
    templateUrl: './configurables-dialog.component.html'
})
export class ConfigurablesDialogComponent implements OnInit {

    configurables: Configurables;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private configurablesService: ConfigurablesService,
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
        if (this.configurables.id !== undefined) {
            this.subscribeToSaveResponse(
                this.configurablesService.update(this.configurables), false);
        } else {
            this.subscribeToSaveResponse(
                this.configurablesService.create(this.configurables), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Configurables>, isCreated: boolean) {
        result.subscribe((res: Configurables) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Configurables, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Configurables is created with identifier ${result.id}`
            : `A Configurables is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'configurablesListModification', content: 'OK'});
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
    selector: 'jhi-configurables-popup',
    template: ''
})
export class ConfigurablesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configurablesPopupService: ConfigurablesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.configurablesPopupService
                    .open(ConfigurablesDialogComponent, params['id']);
            } else {
                this.modalRef = this.configurablesPopupService
                    .open(ConfigurablesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
