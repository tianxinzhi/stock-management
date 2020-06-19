import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttrValue } from 'app/shared/model/attr-value.model';

type EntityResponseType = HttpResponse<IAttrValue>;
type EntityArrayResponseType = HttpResponse<IAttrValue[]>;

@Injectable({ providedIn: 'root' })
export class AttrValueService {
  public resourceUrl = SERVER_API_URL + 'api/attr-values';

  constructor(protected http: HttpClient) {}

  create(attrValue: IAttrValue): Observable<EntityResponseType> {
    return this.http.post<IAttrValue>(this.resourceUrl, attrValue, { observe: 'response' });
  }

  update(attrValue: IAttrValue): Observable<EntityResponseType> {
    return this.http.put<IAttrValue>(this.resourceUrl, attrValue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttrValue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttrValue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
