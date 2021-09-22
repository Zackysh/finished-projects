import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { ScaleType } from 'app/core/models/business/scale/scale.types';
import { combineLatest, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import {
    ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'scale',
  templateUrl: './scale.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ScaleComponent implements OnInit, OnDestroy {
  logs() {
    console.log(this.type);
  }

  type: ScaleType;
  private _unsubscribeAll: Subject<any> = new Subject<any>();
  isLoading = true;

  /**
   * Constructor
   */
  constructor(
    private _route: ActivatedRoute,
    private _scaleService: ScaleService,
    private _changeDetectorRef: ChangeDetectorRef,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // fetch requested scale
    this._route.params.pipe(takeUntil(this._unsubscribeAll)).subscribe(
      params => {
        if (params.id) {
          combineLatest([this._scaleService.getAllTypes(), this._scaleService.findById(params.id)])
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(([types, scale]) => {
              this.type = types.find(t => t.idScaleType === scale.idScaleType);
              this.isLoading = false;

              // Mark for check
              this._changeDetectorRef.markForCheck();
            });
        }
      },
      error => {
        console.error('Failed to load scale');
      },
    );
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
