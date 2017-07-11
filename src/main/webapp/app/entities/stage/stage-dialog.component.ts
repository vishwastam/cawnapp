import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Stage } from './stage.model';
import { StagePopupService } from './stage-popup.service';
import { StageService } from './stage.service';
import { Application, ApplicationService } from '../application';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-stage-dialog',
    templateUrl: './stage-dialog.component.html'
})
export class StageDialogComponent implements OnInit {

    stage: Stage;
    authorities: any[];
    isSaving: boolean;

    applications: Application[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private stageService: StageService,
        private applicationService: ApplicationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ORG_ADMIN', 'ROLE_ADMIN'];
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stageService.update(this.stage), false);
        } else {
            this.subscribeToSaveResponse(
                this.stageService.create(this.stage), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Stage>, isCreated: boolean) {
        result.subscribe((res: Stage) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Stage, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Stage is created with identifier ${result.id}`
            : `A Stage is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'stageListModification', content: 'OK'});
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

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stage-popup',
    template: ''
})
export class StagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stagePopupService: StagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.stagePopupService
                    .open(StageDialogComponent, params['id']);
            } else {
                this.modalRef = this.stagePopupService
                    .open(StageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
