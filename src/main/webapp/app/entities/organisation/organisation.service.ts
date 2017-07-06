import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Organisation } from './organisation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OrganisationService {

    private resourceUrl = 'api/organisations';
    private resourceSearchUrl = 'api/_search/organisations';

    constructor(private http: Http) { }

    create(organisation: Organisation): Observable<Organisation> {
        const copy = this.convert(organisation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(organisation: Organisation): Observable<Organisation> {
        const copy = this.convert(organisation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Organisation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(organisation: Organisation): Organisation {
        const copy: Organisation = Object.assign({}, organisation);
        return copy;
    }
}
