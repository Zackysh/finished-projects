import { Injectable } from '@angular/core';
import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';

import { validateId } from './spain-id.validator.js';

export function ValidateNif(control: AbstractControl): { [key: string]: any } | null {
  if (!control.value || !FormHelper.validatId(control.value)) {
    return { invalidnif: true };
  }

  return null;
}

@Injectable({ providedIn: 'root' })
export class FormHelper {
  static validatId(value: any): any {
    return validateId(value);
  }
  convertIntObj = (obj) => {
    let res = {};
    for (const key in obj) {
      const parsed = parseInt(obj[key], 10);
      res[key] = isNaN(parsed) ? obj[key] : parsed;
    }
    return res;
  };

  ValidateNif(control: AbstractControl): { [key: string]: any } | null {
    if (!control.value || !FormHelper.validatId(control.value)) {
      return { invalidnif: true };
    }

    return null;
  }

  formatDate(date: any) {
    let d = new Date(date);
    let month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear()

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
  }

  getFormValidationErrors(form: FormGroup) {
    const result = [];
    Object.keys(form.controls).forEach(key => {
      const controlErrors: ValidationErrors = form.get(key).errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach(keyError => {
          result.push({
            'control': key,
            'error': keyError,
            'value': controlErrors[keyError]
          });
        });
      }
    });

    return result;
  }
}