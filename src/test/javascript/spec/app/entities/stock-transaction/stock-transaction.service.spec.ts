import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';
import { IStockTransaction, StockTransaction } from 'app/shared/model/stock-transaction.model';
import { Subinventory } from 'app/shared/model/enumerations/subinventory.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

describe('Service Tests', () => {
  describe('StockTransaction Service', () => {
    let injector: TestBed;
    let service: StockTransactionService;
    let httpMock: HttpTestingController;
    let elemDefault: IStockTransaction;
    let expectedResult: IStockTransaction | IStockTransaction[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StockTransactionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new StockTransaction(0, Subinventory.FG, Subinventory.FG, TransactionType.IN, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StockTransaction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            subinventoryFrom: 'BBBBBB',
            subinventoryTo: 'BBBBBB',
            transactionType: 'BBBBBB',
            transactionQuantity: 1,
            reference: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            subinventoryFrom: 'BBBBBB',
            subinventoryTo: 'BBBBBB',
            transactionType: 'BBBBBB',
            transactionQuantity: 1,
            reference: 'BBBBBB'
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

      it('should delete a StockTransaction', () => {
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
