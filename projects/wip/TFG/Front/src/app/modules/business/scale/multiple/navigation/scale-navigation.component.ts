import { Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { BwScaleService } from 'app/core/models/business/scale/bw-scale.service';
import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { toProperCase } from 'app/util/formatters/string.utils';
import { Subject } from 'rxjs';

@Component({
  selector: 'bw-scale-sidebar',
  templateUrl: './scale-navigation.component.html',
  styleUrls: ['./scale-navigation.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class BwScaleSidebarComponent implements OnInit, OnDestroy {
  scales: FuseNavigationItem[];
  menuData: FuseNavigationItem[] = [];
  private _otherMenuData: FuseNavigationItem[] = [];
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  Flog() {
    this._bwScaleService.scales$.subscribe(console.log);
    console.log(this.menuData);
    console.log(this.scales);
  }

  /**
   * Constructor
   */
  constructor(private _scaleService: ScaleService, private _bwScaleService: BwScaleService) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // Fetch scales
    this._scaleService.getAllBwScales().subscribe(scales => {
      // Map scales to
      this.scales = scales.map(sc => ({
        id: `bw-scales.${sc.idScale}`,
        title: `${toProperCase(sc.name)}`,
        function: item => {
          this._bwScaleService.toggleScale(item, sc);
        },
        type: 'basic'
      }));

      this._updateMenuData();
    });

    // Generate other menu links
    this._generateOtherMenuLinks();
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
  // @ Private methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Generate other menus
   *
   * @private
   */
  private _generateOtherMenuLinks(): void {
    // Settings menu
    this._otherMenuData.push({
      title: 'Settings',
      type: 'basic',
      icon: 'heroicons_outline:cog',
      /* link: '/apps/mailbox/settings', */
    });

    // Update the menu data
    this._updateMenuData();
  }

  /**
   * Update the menu data
   *
   * @private
   */
  private _updateMenuData(): void {
    this.menuData = [
      ...(this.scales ?? []),
      {
        type: 'spacer',
      },
      ...this._otherMenuData,
    ];
  }
}

// TODO possible feature into _updateMenuData()

// {
// 	title: 'ACTIVE',
// 	type: 'group',
// 	children: [...this._activeScalesData],
// },
// {
// 	title: 'DISABLED',
// 	type: 'group',
// 	children: [...this._disabledScalesData],
// },
