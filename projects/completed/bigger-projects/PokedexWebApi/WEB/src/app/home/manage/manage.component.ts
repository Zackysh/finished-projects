import { Component, OnInit } from '@angular/core';
import { faTrashAlt, faEye } from '@fortawesome/free-regular-svg-icons';
import { faPencilAlt,  } from '@fortawesome/free-solid-svg-icons';
import { first } from 'rxjs/operators';
import { Pokemon } from 'src/app/_models';
import { PokemonService } from 'src/app/_services/pokemon.service';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css'],
})
export class ManageComponent implements OnInit {
  constructor(private pokemonService: PokemonService) {}
  faEye = faEye;
  pokemons: any;
  filteredPokemons: any;
  faTrashAlt = faTrashAlt;
  faEdit = faPencilAlt;
  searchValue: string;

  filterPokemons() {
    this.filteredPokemons = this.pokemons.filter((pokemon: Pokemon) =>
      pokemon.name.toLowerCase().includes(this.searchValue.trim().toLowerCase())
    );
  }

  remove(number: number) {
    if (
      window.confirm('You are about to delete selected pokemon, are you sure?')
    )
      this.pokemonService.remove(number).subscribe(() => {
        this.pokemons = this.pokemons.filter(
          (pokemon) => pokemon.number !== number
        );
        this.filteredPokemons = this.pokemons;
      });
  }

  ngOnInit(): void {
    this.pokemonService
      .getAll()
      .pipe(first())
      .subscribe((pokemons) => {
        this.pokemons = pokemons;
        this.filteredPokemons = pokemons;
      });
  }
}
