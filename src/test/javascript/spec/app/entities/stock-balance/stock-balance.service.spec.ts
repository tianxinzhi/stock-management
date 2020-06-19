import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StockBalanceService } from 'app/entities/stock-balance/stock-balance.service';
import { IStockBalance, StockBalance } from 'app/shared/model/stock-balance.model';
import { Subinventory } from 'app/shared/model/enumerations/subinventory.model';

describe('Service Tests', () => {
  describe('StockBalance Service', () => {
    let injector: TestBed;
    let service: StockBalanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IStockBalance;
    let expectedResult: IStockBalance | IStockBalance[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StockBalanceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new StockBalance(0, Subinventory.FG, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StockBalance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StockBalance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StockBalance', () => {
        const returnedFromService = Object.assign(
          {
            subInventory: 'BBBBBB',
            quantityOnhand: 1,
            quantityReserved: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StockBalance', () => {
        const returnedFromService = Object.assign(
          {
            subInventory: 'BBBBBB',
            quantityOnhand: 1,
            quantityReserved: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StockBalance', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
