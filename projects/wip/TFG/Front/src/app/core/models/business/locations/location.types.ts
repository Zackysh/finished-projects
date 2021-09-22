export interface Country {
  idCountry: number;
  code: number;
  iso3166a1: string;
  iso3166a2: string;
  name: string
}

export interface Provincia {
  idProvincia: string;
  idComunidadAutonoma: number
  name: string;
}
export interface Municipio {
  name: string;
  idPoblacion: number;
  idProvincia: number;
}

export interface Poblacion {
  idPoblacion: number;
  poblacion: string;
  postalCode: number;
  municipio: string;
  comarca: string;
  iso2: string;
  idProvincia: number;
}
