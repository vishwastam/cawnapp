import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Configurables } from './configurables.model';
import { ConfigurablesPopupService } from './configurables-popup.service';
import { ConfigurablesService } from './configurables.service';

@Component({
    selector: 'jhi-configurables-delete-dialog',
    templateUrl: './configurables-delete-dialog.component.html'
})
export class ConfigurablesDeleteDialogComponent {

    configurables: Configurables;

    constructor(
        private configurablesService: ConfigurablesService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configurablesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'configurablesListModification',
                content: 'Deleted an configurables'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Configurables is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-configurables-delete-popup',
    template: ''
})
export class ConfigurablesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configurablesPopupService: ConfigurablesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.configurablesPopupService
                .open(ConfigurablesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
