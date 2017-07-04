import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Configurables } from './configurables.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConfigurablesService {

    private resourceUrl = 'api/configurables';
    private resourceSearchUrl = 'api/_search/configurables';

    constructor(private http: Http) { }

    create(configurables: Configurables): Observable<Configurables> {
        const copy = this.convert(configurables);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(configurables: Configurables): Observable<Configurables> {
        const copy = this.convert(configurables);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Configurables> {
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

    private convert(configurables: Configurables): Configurables {
        const copy: Configurables = Object.assign({}, configurables);
        return copy;
    }
}
