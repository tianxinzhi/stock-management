import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { StockItemDetailComponent } from 'app/entities/stock-item/stock-item-detail.component';
import { StockItem } from 'app/shared/model/stock-item.model';

describe('Component Tests', () => {
  describe('StockItem Management Detail Component', () => {
    let comp: StockItemDetailComponent;
    let fixture: ComponentFixture<StockItemDetailComponent>;
    const route = ({ data: of({ stockItem: new StockItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [StockItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stockItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
