import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Key } from './key.model';
import { KeyPopupService } from './key-popup.service';
import { KeyService } from './key.service';
import { Stage, StageService } from '../stage';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-key-dialog',
    templateUrl: './key-dialog.component.html'
})
export class KeyDialogComponent implements OnInit {

    key: Key;
    authorities: any[];
    isSaving: boolean;

    stages: Stage[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private keyService: KeyService,
        private stageService: StageService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.stageService.query()
            .subscribe((res: ResponseWrapper) => { this.stages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.key.id !== undefined) {
            this.subscribeToSaveResponse(
                this.keyService.update(this.key), false);
        } else {
            this.subscribeToSaveResponse(
                this.keyService.create(this.key), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Key>, isCreated: boolean) {
        result.subscribe((res: Key) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Key, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Key is created with identifier ${result.id}`
            : `A Key is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'keyListModification', content: 'OK'});
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

    trackStageById(index: number, item: Stage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-key-popup',
    template: ''
})
export class KeyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyPopupService: KeyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.keyPopupService
                    .open(KeyDialogComponent, params['id']);
            } else {
                this.modalRef = this.keyPopupService
                    .open(KeyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
