import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Configurables } from './configurables.model';
import { ConfigurablesService } from './configurables.service';
@Injectable()
export class ConfigurablesPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private configurablesService: ConfigurablesService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.configurablesService.find(id).subscribe((configurables) => {
                this.configurablesModalRef(component, configurables);
            });
        } else {
            return this.configurablesModalRef(component, new Configurables());
        }
    }

    configurablesModalRef(component: Component, configurables: Configurables): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.configurables = configurables;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
