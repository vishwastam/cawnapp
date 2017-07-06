import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CawnappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StageDetailComponent } from '../../../../../../main/webapp/app/entities/stage/stage-detail.component';
import { StageService } from '../../../../../../main/webapp/app/entities/stage/stage.service';
import { Stage } from '../../../../../../main/webapp/app/entities/stage/stage.model';

describe('Component Tests', () => {

    describe('Stage Management Detail Component', () => {
        let comp: StageDetailComponent;
        let fixture: ComponentFixture<StageDetailComponent>;
        let service: StageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CawnappTestModule],
                declarations: [StageDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StageService,
                    EventManager
                ]
            }).overrideTemplate(StageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Stage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.stage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
