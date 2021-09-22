import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { APIRes } from '../../core.types';
import { Product } from '../product/product.types';
import { Crate, Hopper, Scale, ScaleType } from './scale.types';

@Injectable({
  providedIn: 'root',
})
export class ScaleService {
  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  getAll(): Observable<Scale[]> {
    return this._httpClient.get<APIRes<Scale[]>>('core/scale').pipe(map(res => res.data));
  }

  findScaleProducts(idScale: number): Observable<Product[]> {
    return this._httpClient
      .get<APIRes<Product[]>>(`core/scale/${idScale}/product`)
      .pipe(map(res => res.data));
  }

  getNavData(): Observable<Scale[]> {
    return this._httpClient.get<APIRes<Scale[]>>('core/scale/nav-data').pipe(map(res => res.data));
  }

  getAllBwScales(): Observable<Scale[]> {
    return this._httpClient.get<APIRes<Scale[]>>('core/scale/bw').pipe(map(res => res.data));
  }

  getAllCrates(): Observable<Crate[]> {
    return this._httpClient.get<APIRes<Crate[]>>('core/crate').pipe(map(res => res.data));
  }

  getAllHoppers(): Observable<Hopper[]> {
    return this._httpClient.get<APIRes<Hopper[]>>('core/hopper').pipe(map(res => res.data));
  }

  getAllTypes(): Observable<ScaleType[]> {
    return this._httpClient.get<APIRes<ScaleType[]>>('core/scale/types').pipe(map(res => res.data));
  }

  getWeight(idScale: number): Observable<number> {
    return this._httpClient
      .get<APIRes<number>>(`core/scale/weight/${idScale}`)
      .pipe(map(res => res.data));
  }

  findById(idScale: number): Observable<Scale> {
    return this._httpClient.get<APIRes<Scale>>(`core/scale/${idScale}`).pipe(map(res => res.data));
  }

  createScale(scale: Scale): Observable<Scale> {
    return this._httpClient.post<APIRes<Scale>>('core/scale', { ...scale }).pipe(map(res => res.data));
  }

  updateScale(idScale: number, scale: Scale): Observable<Scale> {
    return this._httpClient
      .put<APIRes<Scale>>(`core/scale/${idScale}`, {
        ...scale,
      })
      .pipe(map(res => res.data));
  }

  deleteScale(idScale: number): Observable<Scale> {
    return this._httpClient.delete<APIRes<Scale>>(`core/scale/${idScale}`).pipe(map(res => res.data));
  }
}
