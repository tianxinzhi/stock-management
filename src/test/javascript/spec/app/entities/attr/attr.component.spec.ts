import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StockManagementTestModule } from '../../../test.module';
import { AttrComponent } from 'app/entities/attr/attr.component';
import { AttrService } from 'app/entities/attr/attr.service';
import { Attr } from 'app/shared/model/attr.model';

describe('Component Tests', () => {
  describe('Attr Management Component', () => {
    let comp: AttrComponent;
    let fixture: ComponentFixture<AttrComponent>;
    let service: AttrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StockManagementTestModule],
        declarations: [AttrComponent]
      })
        .overrideTemplate(AttrComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Attr(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attrs && comp.attrs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
