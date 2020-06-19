import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StockManagementTestModule } from '../../../test.module';
import { AttrUpdateComponent } from 'app/entities/attr/attr-update.component';
import { AttrService } from 'app/entities/attr/attr.service';
import { Attr } from 'app/shared/model/attr.model';

describe('Component Tests', () => {
  describe('Attr Management Update Component', () => {
    let comp: AttrUpdateComponent;
    let fixture: ComponentFixture<AttrUpdateComponent>;
    let service: AttrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Attr(123);
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
        const entity = new Attr();
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
