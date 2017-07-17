import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CawnappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { C_userDetailComponent } from '../../../../../../main/webapp/app/entities/c-user/c-user-detail.component';
import { C_userService } from '../../../../../../main/webapp/app/entities/c-user/c-user.service';
import { C_user } from '../../../../../../main/webapp/app/entities/c-user/c-user.model';

describe('Component Tests', () => {

    describe('C_user Management Detail Component', () => {
        let comp: C_userDetailComponent;
        let fixture: ComponentFixture<C_userDetailComponent>;
        let service: C_userService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CawnappTestModule],
                declarations: [C_userDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    C_userService,
                    EventManager
                ]
            }).overrideTemplate(C_userDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(C_userDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(C_userService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new C_user(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.c_user).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
