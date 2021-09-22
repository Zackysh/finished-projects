/* tslint:disable:max-line-length */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { toProperCase } from 'app/util/formatters/string.utils';
import { combineLatest, Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

// dove

@Injectable({
  providedIn: 'root',
})
export class NavigationLoader {
  constructor(private _httpClient: HttpClient, private _scaleService: ScaleService) {}

  getDefaultNavigation(): Observable<FuseNavigationItem[]> {
    const obsv2 = combineLatest([this._scaleService.getNavData(), this._scaleService.getAllTypes()]);

    const obsv: Observable<FuseNavigationItem[]> = obsv2.pipe(
      // fetch scales
      map(([scales, types]) => {
        const bwType = types.find(t => t.name === 'BW');
        if (!bwType) console.error('BwScale type not found! bw-scales will be loaded as regular scales');

        // no-bw scales as navItems
        const items: FuseNavigationItem[] = [];

        // classify bw / no-bw scales
        for (const sc of scales) {
          // if scale is BW, don't create item
          if (bwType && sc.idScaleType === bwType.idScaleType) continue;
          else {
            const item: FuseNavigationItem = {
              id: `dashboards.scales.${sc.idScale}`,
              title: `${toProperCase(sc.name)}`,
              type: 'basic',
              icon: 'iconsmind:double_circle',
              link: `scales/${sc.idScale}`,
            };
            items.push(item);
          }
        }
        return items;
      }),
      switchMap(scaleItems => {
        return of<FuseNavigationItem[]>([
          {
            id: 'dashboard.scales',
            title: 'Scales',
            type: 'collapsable',
            icon: 'iconsmind:scale',
            children: [
              {
                id: 'dashboards.bw-scales',
                title: 'BW Scales',
                type: 'basic',
                icon: 'iconsmind:dove',
                link: 'scales',
              },
              ...scaleItems,
            ],
          },
        ]);
      }),
      map(nav => [...defaultNavigation, ...nav]),
    );
    return obsv;
  }
}

export const defaultNavigation: FuseNavigationItem[] = [
  {
    id: 'dashboards.settings',
    title: 'Settings',
    type: 'collapsable',
    icon: 'feather:settings',
    children: [
      {
        id: 'dashboards.settings.users',
        title: 'dashboards.settings.users',
        type: 'collapsable',
        icon: 'feather:user',
        children: [
          {
            id: 'dashboards.settings.user.permissions',
            title: 'dashboards.settings.user.permissions',
            type: 'basic',
            link: 'settings/user/group',
          },
          {
            id: 'dashboards.settings.user.list',
            title: 'dashboards.settings.user.list',
            type: 'basic',
            link: 'settings/user/list',
          },
        ],
      },
      {
        id: 'dashboards.company',
        title: 'dashboards.company',
        type: 'basic',
        icon: 'heroicons_outline:library',
        link: '/settings/company',
      },
    ],
  },
  {
    id: 'dashboards.customer',
    title: 'Customer',
    type: 'basic',
    // icon: 'heroicons-outline:badge-check',
    link: 'customer',
  },
  {
    id: 'dashboards.supplier',
    title: 'Supplier',
    type: 'basic',
    // icon: 'heroicons-outline:badge-check',
    link: 'supplier',
  },
  {
    id: 'dashboards.product',
    title: 'Product',
    type: 'basic',
    // icon: 'heroicons-outline:badge-check',
    link: 'product',
  },
];
