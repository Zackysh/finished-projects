import { Observable } from 'rxjs';

import { HttpRequest } from '@angular/common/http';

export type FuseMockApiReplyCallback =
    | ((data: { request: HttpRequest<any>; urlParams: { [key: string]: string } }) => ([number, string | any]) | Observable<any>)
    | undefined;

export type FuseMockApiMethods =
    | 'get'
    | 'post'
    | 'put'
    | 'patch'
    | 'delete';
