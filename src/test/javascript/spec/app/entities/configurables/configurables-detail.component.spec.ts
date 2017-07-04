import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CawnappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConfigurablesDetailComponent } from '../../../../../../main/webapp/app/entities/configurables/configurables-detail.component';
import { ConfigurablesService } from '../../../../../../main/webapp/app/entities/configurables/configurables.service';
import { Configurables } from '../../../../../../main/webapp/app/entities/configurables/configurables.model';

describe('Component Tests', () => {

    describe('Configurables Management Detail Component', () => {
        let comp: ConfigurablesDetailComponent;
        let fixture: ComponentFixture<ConfigurablesDetailComponent>;
        let service: ConfigurablesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CawnappTestModule],
                declarations: [ConfigurablesDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConfigurablesService,
                    EventManager
                ]
            }).overrideTemplate(ConfigurablesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfigurablesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigurablesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Configurables(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.configurables).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
