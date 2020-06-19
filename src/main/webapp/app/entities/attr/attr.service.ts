import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttr } from 'app/shared/model/attr.model';

type EntityResponseType = HttpResponse<IAttr>;
type EntityArrayResponseType = HttpResponse<IAttr[]>;

@Injectable({ providedIn: 'root' })
export class AttrService {
  public resourceUrl = SERVER_API_URL + 'api/attrs';

  constructor(protected http: HttpClient) {}

  create(attr: IAttr): Observable<EntityResponseType> {
    return this.http.post<IAttr>(this.resourceUrl, attr, { observe: 'response' });
  }

  update(attr: IAttr): Observable<EntityResponseType> {
    return this.http.put<IAttr>(this.resourceUrl, attr, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
