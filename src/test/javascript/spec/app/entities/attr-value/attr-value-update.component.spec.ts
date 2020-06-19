import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { AttrValueUpdateComponent } from 'app/entities/attr-value/attr-value-update.component';
import { AttrValueService } from 'app/entities/attr-value/attr-value.service';
import { AttrValue } from 'app/shared/model/attr-value.model';

describe('Component Tests', () => {
  describe('AttrValue Management Update Component', () => {
    let comp: AttrValueUpdateComponent;
    let fixture: ComponentFixture<AttrValueUpdateComponent>;
    let service: AttrValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrValueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttrValueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrValueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrValueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttrValue(123);
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
        const entity = new AttrValue();
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
