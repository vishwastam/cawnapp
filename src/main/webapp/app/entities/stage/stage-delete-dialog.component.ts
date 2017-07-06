import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Stage } from './stage.model';
import { StagePopupService } from './stage-popup.service';
import { StageService } from './stage.service';

@Component({
    selector: 'jhi-stage-delete-dialog',
    templateUrl: './stage-delete-dialog.component.html'
})
export class StageDeleteDialogComponent {

    stage: Stage;

    constructor(
        private stageService: StageService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stageListModification',
                content: 'Deleted an stage'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Stage is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-stage-delete-popup',
    template: ''
})
export class StageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stagePopupService: StagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.stagePopupService
                .open(StageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
