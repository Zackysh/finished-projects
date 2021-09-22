import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { Crate, Hopper, Scale } from 'app/core/models/business/scale/scale.types';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'bench-scale',
  templateUrl: './bench-scale.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BenchScaleComponent implements OnInit, OnDestroy {
  scale: Scale;
  crates: Crate[];
  hoppers: Hopper[];

  scaleForm: FormGroup;
  _IdInterval: number;

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  isLoading = false;

  /**
   * Constructor
   */
  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _scaleService: ScaleService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _formBuilder: FormBuilder,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // build form
    this.scaleForm = this._formBuilder.group({
      weight: [''],
    });

    // fetch requested scale
    this._route.params.pipe(takeUntil(this._unsubscribeAll)).subscribe(
      params => {
        if (params.id) {
          this._scaleService.findById(params.id).subscribe(scale => {
            this.scale = scale;
            this.startWeighing();

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
        }
      },
      error => {
        console.error('Failed to load scale');
      },
    );

    // fetch crates
    this._scaleService.getAllCrates().subscribe(crates => {
      this.crates = crates;
    });

    // fetch hopper
    this._scaleService.getAllHoppers().subscribe(hoppers => {
      this.hoppers = hoppers;
    });

    // this._router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe((data: any) => {
    //   if (this._IdInterval) window.clearInterval(this._IdInterval);
    // });
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    if (this._IdInterval) this.clearInterval();
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Private methods
  // -----------------------------------------------------------------------------------------------------

  private getWeight(): void {
    this._scaleService.getWeight(this.scale.idScale).subscribe(res => {
      this.scaleForm.patchValue({ weight: res });

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  private startWeighing(): void {
    if (this._IdInterval) this.clearInterval();
    this._IdInterval = window.setInterval(this.getWeight.bind(this), 100);
  }

  private clearInterval(): void {
    // Clear any timeout/interval up to that id
    for (let i = 1; i < this._IdInterval; i++) {
      window.clearInterval(i);
    }
  }
}
