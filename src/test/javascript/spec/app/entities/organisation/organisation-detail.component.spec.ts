import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CawnappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrganisationDetailComponent } from '../../../../../../main/webapp/app/entities/organisation/organisation-detail.component';
import { OrganisationService } from '../../../../../../main/webapp/app/entities/organisation/organisation.service';
import { Organisation } from '../../../../../../main/webapp/app/entities/organisation/organisation.model';

describe('Component Tests', () => {

    describe('Organisation Management Detail Component', () => {
        let comp: OrganisationDetailComponent;
        let fixture: ComponentFixture<OrganisationDetailComponent>;
        let service: OrganisationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CawnappTestModule],
                declarations: [OrganisationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrganisationService,
                    EventManager
                ]
            }).overrideTemplate(OrganisationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrganisationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Organisation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.organisation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
