import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockBalance } from 'app/shared/model/stock-balance.model';

type EntityResponseType = HttpResponse<IStockBalance>;
type EntityArrayResponseType = HttpResponse<IStockBalance[]>;

@Injectable({ providedIn: 'root' })
export class StockBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/stock-balances';

  constructor(protected http: HttpClient) {}

  create(stockBalance: IStockBalance): Observable<EntityResponseType> {
    return this.http.post<IStockBalance>(this.resourceUrl, stockBalance, { observe: 'response' });
  }

  update(stockBalance: IStockBalance): Observable<EntityResponseType> {
    return this.http.put<IStockBalance>(this.resourceUrl, stockBalance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStockBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStockBalance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
