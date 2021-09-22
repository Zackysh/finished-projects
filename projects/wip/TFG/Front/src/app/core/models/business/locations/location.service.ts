import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes } from '../../core.types';
import { Country, Municipio, Poblacion, Provincia } from './location.types';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  countries = new BehaviorSubject<Country[] | null>(null);
  provincias = new BehaviorSubject<Provincia[] | null>(null);

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  get countries$(): Observable<Country[]> {
    return this.countries.asObservable();
  }
  setCountries$(countries: Country[]) {
    this.countries.next(countries);
  }

  get provincias$(): Observable<Provincia[]> {
    return this.provincias.asObservable();
  }
  setProvincias$(provincias: Provincia[]) {
    this.provincias.next(provincias);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  getCountries(): Observable<Country[]> {
    return this._httpClient.get<APIRes<Country[]>>('core/location/country').pipe(map(res => res.data));
  }

  getProvincias(): Observable<Provincia[]> {
    return this._httpClient
      .get<APIRes<Provincia[]>>('core/location/provincia')
      .pipe(map(res => res.data));
  }

  getMunicipiosByProvincia(idProvincia: number): Observable<Municipio[]> {
    return this._httpClient
      .get<APIRes<Municipio[]>>(`core/location/municipio-provincia/${idProvincia}`)
      .pipe(map(res => res.data));
  }

  findPoblacionById(idPoblacion: number): Observable<Poblacion> {
    return this._httpClient
      .get<APIRes<Poblacion>>(`core/location/poblacion/${idPoblacion}`)
      .pipe(map(res => res.data));
  }

  getPoblacionesByProvincia(idProvincia: number): Observable<Poblacion[]> {
    return this._httpClient
      .get<APIRes<Poblacion[]>>(`core/location/poblacion-provincia/${idProvincia}`)
      .pipe(map(res => res.data));
  }

  getPoblacionesByMunicipio(nombreMunicipio: string): Observable<Poblacion[]> {
    let headers = new HttpHeaders();
    headers = headers.set('nombre-municipio', nombreMunicipio);
    return this._httpClient
      .get<APIRes<Poblacion[]>>('core/location/poblacion-municipio', { headers })
      .pipe(map(res => res.data));
  }
}
