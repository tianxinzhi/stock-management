import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { StockTransactionUpdateComponent } from 'app/entities/stock-transaction/stock-transaction-update.component';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';
import { StockTransaction } from 'app/shared/model/stock-transaction.model';

describe('Component Tests', () => {
  describe('StockTransaction Management Update Component', () => {
    let comp: StockTransactionUpdateComponent;
    let fixture: ComponentFixture<StockTransactionUpdateComponent>;
    let service: StockTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [StockTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockTransaction(123);
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
        const entity = new StockTransaction();
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
