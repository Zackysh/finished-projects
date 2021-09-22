import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector       : 'group',
    templateUrl    : './group.component.html',
    encapsulation  : ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GroupComponent
{
    /**
     * Constructor
     */
    constructor()
    {
    }
}
