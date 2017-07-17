import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { C_user } from './c-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class C_userService {

    private resourceUrl = 'api/c-users';
    private resourceSearchUrl = 'api/_search/c-users';

    constructor(private http: Http) { }

    create(c_user: C_user): Observable<C_user> {
        const copy = this.convert(c_user);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(c_user: C_user): Observable<C_user> {
        const copy = this.convert(c_user);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<C_user> {
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

    private convert(c_user: C_user): C_user {
        const copy: C_user = Object.assign({}, c_user);
        return copy;
    }
}
