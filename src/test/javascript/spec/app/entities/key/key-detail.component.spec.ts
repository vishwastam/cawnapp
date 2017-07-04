import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CawnappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { KeyDetailComponent } from '../../../../../../main/webapp/app/entities/key/key-detail.component';
import { KeyService } from '../../../../../../main/webapp/app/entities/key/key.service';
import { Key } from '../../../../../../main/webapp/app/entities/key/key.model';

describe('Component Tests', () => {

    describe('Key Management Detail Component', () => {
        let comp: KeyDetailComponent;
        let fixture: ComponentFixture<KeyDetailComponent>;
        let service: KeyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CawnappTestModule],
                declarations: [KeyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    KeyService,
                    EventManager
                ]
            }).overrideTemplate(KeyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Key(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.key).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
