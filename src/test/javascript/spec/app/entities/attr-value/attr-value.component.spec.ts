import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StockManagementTestModule } from '../../../test.module';
import { AttrValueComponent } from 'app/entities/attr-value/attr-value.component';
import { AttrValueService } from 'app/entities/attr-value/attr-value.service';
import { AttrValue } from 'app/shared/model/attr-value.model';

describe('Component Tests', () => {
  describe('AttrValue Management Component', () => {
    let comp: AttrValueComponent;
    let fixture: ComponentFixture<AttrValueComponent>;
    let service: AttrValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrValueComponent]
      })
        .overrideTemplate(AttrValueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrValueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrValueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttrValue(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attrValues && comp.attrValues[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
