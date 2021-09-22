import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';
import { Pokemon } from '../_models';

@Injectable({ providedIn: 'root' })
export class PokemonService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get(`${environment.apiUrl}/pokemon`);
  }

  getById(number: number) {
    return this.http.get<Pokemon>(`${environment.apiUrl}/pokemon/${number}`);
  }

  remove(number: number) {
    return this.http.delete(`${environment.apiUrl}/pokemon/${number}`);
  }

  create(pokemon: any) {
    return this.http.post(`${environment.apiUrl}/pokemon`, pokemon);
  }

  update(number: number, pokemon: Pokemon) {
    return this.http.put<Pokemon>(
      `${environment.apiUrl}/pokemon/?numPokemon=${number}`,
      pokemon
    );
  }

  getAvailableTypes() {
    return this.http.get(`${environment.apiUrl}/type`);
  }
}
