import { Component, Input, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { Scale } from 'app/core/models/business/scale/scale.types';
import { Subject } from 'rxjs';
import { MultiScaleComponent } from '../multi-scale.component';

@Component({
  selector: 'scale-wrapper',
  templateUrl: './scale-wrapper.component.html',
	styleUrls: ['./scale-wrapper.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ScaleWrapperComponent implements OnInit, OnDestroy {
  @Input('scales') scales: Scale[] = [];

  Flog() {}

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  /**
   * Constructor
   */
  constructor(public _multiScaleComponent: MultiScaleComponent) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {}

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
