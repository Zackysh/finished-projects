import { NgModule, Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'spacecamel' })
export class SpaceCamelPipe implements PipeTransform {
  transform(value: any, ...args: any[]) {
    if (typeof value !== 'string') return value;
    return value.replace(/([A-Z])/g, ' $1').replace(/^./, function (str) { return str.toUpperCase(); })
  }
}

@NgModule({
  declarations: [SpaceCamelPipe],
  exports: [SpaceCamelPipe]
})
export class SpaceCamelPipeModule{}