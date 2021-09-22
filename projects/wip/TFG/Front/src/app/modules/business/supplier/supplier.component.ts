import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'suppliers',
  templateUrl: './supplier.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupplierComponent {
  /**
   * Constructor
   */
  constructor() { }
}
