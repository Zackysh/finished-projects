import { Injectable } from '@angular/core';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { BehaviorSubject } from 'rxjs';
import { ScaleService } from './scale.service';
import { Scale } from './scale.types';

@Injectable({
  providedIn: 'root',
})
export class BwScaleService {
  private scales = new BehaviorSubject<Scale[]>([]);

  get scales$() {
    return this.scales.asObservable();
  }

  /**
   * Constructor
   */
  constructor(private _scaleService: ScaleService) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  public toggleScaleById(item: FuseNavigationItem, idScale: number): void {
    // find scale in subject
    const sc = this.scales.value.find(sc => sc.idScale === idScale);
    if (sc) {
			// If it's already selected, remove it
      this.scales.next(this.scales.value.filter(sc => sc.idScale !== idScale));
      item.active = false;
    } else {
			// If it isn't selected, store it
      this._scaleService.findById(idScale).subscribe(scale => {
        this.scales.next([...this.scales.value, scale]);
      });
      item.active = true;
    }
    console.log(this.scales);
  }

  public toggleScale(item: FuseNavigationItem, scale: Scale): void {
    const sc = this.scales.value.find(sc => sc.idScale === scale.idScale);
    if (sc) {
      this.scales.next(this.scales.value.filter(sc => sc.idScale !== scale.idScale));
      item.active = false;
    } else {
      this.scales.next([...this.scales.value, scale]);
      item.active = true;
    }
    console.log(this.scales.value);
  }
}
