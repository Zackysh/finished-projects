import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { Crate, Hopper, Scale } from 'app/core/models/business/scale/scale.types';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'bw-scale',
  templateUrl: './bw-scale.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BwScaleComponent implements OnInit, OnDestroy {
  @Input('scale') scale: Scale;
  crates: Crate[];
  hoppers: Hopper[];
  private _unsubscribeAll: Subject<any> = new Subject<any>();
  isLoading = false;

  /**
   * Constructor
   */
  constructor(
    private _route: ActivatedRoute,
    private _scaleService: ScaleService,
    private _changeDetectorRef: ChangeDetectorRef,
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
    // If no input has been provided, params should've
    if (!this.scale)
      // fetch requested scale
      this._route.params.pipe(takeUntil(this._unsubscribeAll)).subscribe(
        params => {
          if (params.id) {
            this._scaleService.findById(params.id).subscribe(scale => {
              this.scale = scale;

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
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------
}
