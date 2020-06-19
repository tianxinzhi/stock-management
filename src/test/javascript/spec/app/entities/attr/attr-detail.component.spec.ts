import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { AttrDetailComponent } from 'app/entities/attr/attr-detail.component';
import { Attr } from 'app/shared/model/attr.model';

describe('Component Tests', () => {
  describe('Attr Management Detail Component', () => {
    let comp: AttrDetailComponent;
    let fixture: ComponentFixture<AttrDetailComponent>;
    const route = ({ data: of({ attr: new Attr(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attr on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attr).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
