import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { Pokemon } from 'src/app/_models';
import { PokemonService } from 'src/app/_services/pokemon.service';

@Component({
  selector: 'app-viewport',
  templateUrl: './viewport.component.html',
  styleUrls: ['./viewport.component.css'],
})
export class ViewportComponent implements OnInit {
  pokemon: Pokemon;

  constructor(private pokemonService: PokemonService, private router: Router) {}

  ngOnInit(): void {
    let number = window.history.state.number;
    if (number) {
      this.pokemonService
        .getById(number)
        .pipe(first())
        .subscribe((pokemon: Pokemon) => {
          this.pokemon = pokemon;
        });
    } else this.router.navigateByUrl('/home');
  }
}
