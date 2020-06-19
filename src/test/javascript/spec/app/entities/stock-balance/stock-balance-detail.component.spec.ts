import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { StockBalanceDetailComponent } from 'app/entities/stock-balance/stock-balance-detail.component';
import { StockBalance } from 'app/shared/model/stock-balance.model';

describe('Component Tests', () => {
  describe('StockBalance Management Detail Component', () => {
    let comp: StockBalanceDetailComponent;
    let fixture: ComponentFixture<StockBalanceDetailComponent>;
    const route = ({ data: of({ stockBalance: new StockBalance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [StockBalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockBalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockBalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stockBalance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockBalance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
