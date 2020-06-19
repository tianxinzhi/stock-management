import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { AttrValueDetailComponent } from 'app/entities/attr-value/attr-value-detail.component';
import { AttrValue } from 'app/shared/model/attr-value.model';

describe('Component Tests', () => {
  describe('AttrValue Management Detail Component', () => {
    let comp: AttrValueDetailComponent;
    let fixture: ComponentFixture<AttrValueDetailComponent>;
    const route = ({ data: of({ attrValue: new AttrValue(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrValueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttrValueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttrValueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attrValue on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attrValue).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
