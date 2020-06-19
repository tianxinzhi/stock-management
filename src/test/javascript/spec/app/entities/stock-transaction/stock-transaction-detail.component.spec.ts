import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { StockTransactionDetailComponent } from 'app/entities/stock-transaction/stock-transaction-detail.component';
import { StockTransaction } from 'app/shared/model/stock-transaction.model';

describe('Component Tests', () => {
  describe('StockTransaction Management Detail Component', () => {
    let comp: StockTransactionDetailComponent;
    let fixture: ComponentFixture<StockTransactionDetailComponent>;
    const route = ({ data: of({ stockTransaction: new StockTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [StockTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stockTransaction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
