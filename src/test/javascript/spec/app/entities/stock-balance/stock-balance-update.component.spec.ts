import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { StockBalanceUpdateComponent } from 'app/entities/stock-balance/stock-balance-update.component';
import { StockBalanceService } from 'app/entities/stock-balance/stock-balance.service';
import { StockBalance } from 'app/shared/model/stock-balance.model';

describe('Component Tests', () => {
  describe('StockBalance Management Update Component', () => {
    let comp: StockBalanceUpdateComponent;
    let fixture: ComponentFixture<StockBalanceUpdateComponent>;
    let service: StockBalanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [StockBalanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockBalanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockBalanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockBalanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockBalance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockBalance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
