import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { PokemonService } from 'src/app/_services/pokemon.service';

@Component({
  selector: 'app-create-edit',
  templateUrl: './create-edit.component.html',
  styleUrls: ['./create-edit.component.css'],
})
export class CreateEditComponent implements OnInit {
  // form control
  types: Array<any>;
  selectedTypes: Array<any> = [];
  number: number;
  isAddMode: boolean;
  form: FormGroup;
  submitted: boolean = false;

  get f() {
    return this.form.controls;
  }

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private pokemonService: PokemonService
  ) {}

  onCheck(e, type) {
    if (e.currentTarget.checked) {
      this.selectedTypes.push(type);
    } else {
      this.selectedTypes = this.selectedTypes.filter(
        (own) => own.idType !== type.idType
      );
    }
  }

  ngOnInit(): void {
    this.number = window.history.state.number;
    this.isAddMode = this.number ? false : true;
    this.pokemonService
      .getAvailableTypes()
      .pipe(first())
      .subscribe((availableTypes: Array<any>) => {
        this.types = availableTypes;
      });

    this.form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(45)]],
      number: ['', Validators.required],
      skill: ['', [Validators.maxLength(50), Validators.required]],
      category: ['', [Validators.maxLength(50), Validators.required]],
      height: ['', [Validators.maxLength(50), Validators.required]],
      weight: ['', [Validators.maxLength(50), Validators.required]],
      sex: ['', [Validators.maxLength(50), Validators.required]],
      image: ['', [Validators.maxLength(250), Validators.required]],
      description: ['', [Validators.maxLength(500), Validators.required]],
    });

    if (!this.isAddMode) {
      this.pokemonService
        .getById(this.number)
        .pipe(first())
        .subscribe((pokemon) => {
          this.form.patchValue(pokemon);
          this.selectedTypes = pokemon.types;
        });
    }
  }

  isTypeSelected(idType: number) {
    return (
      this.selectedTypes.filter((type) => type.idType == idType).length !== 0
    );
  }

  onSubmit() {
    this.submitted = true;

    if (this.form.invalid) return;

    if (this.isAddMode) {
      this.form.value.types = this.selectedTypes.map((type) => type.idType);
      this.pokemonService
        .create(this.form.value)
        .pipe(first())
        .subscribe((x) => {
          window.alert('Successful creation!');
          console.log(x);
          this.router.navigateByUrl('/home/manage');
        });
    } else {
      this.form.value.types = this.selectedTypes.map((type) => type.idType);
      this.pokemonService
        .update(this.number, this.form.value)
        .pipe(first())
        .subscribe((x) => {
          window.alert('Successful edition!');
          console.log(x);
          this.router.navigateByUrl('/home/manage');
        });
    }
    
  }
}
