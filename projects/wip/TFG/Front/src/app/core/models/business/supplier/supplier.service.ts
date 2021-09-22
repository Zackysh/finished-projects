import { BehaviorSubject, Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes, BLANK_TEMPLATE_ID } from '../../core.types';
import { Pagination, PaginationResponse, PaginationSearch } from '../../pagination.types';
import { PaymentMethod, Supplier } from './supplier.types';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  suppliers = new BehaviorSubject<Supplier[] | null>(null);
  supplier = new BehaviorSubject<Supplier | null>(null);
  _pagination = new BehaviorSubject<Pagination | null>(null);

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Subject Interaction
  // -----------------------------------------------------------------------------------------------------

  get supplier$(): Observable<Supplier> {
    return this.supplier.asObservable();
  }
  get suppliers$(): Observable<Supplier[]> {
    return this.suppliers.asObservable();
  }
  get pagination$(): Observable<Pagination> {
    return this._pagination.asObservable();
  }

  /**
   * Get supplier by id from in memory Subject
   * @param idSupplier
   */
  getSupplierById$(idSupplier: number): Observable<Supplier> {
    return of(this.suppliers.value?.find(c => c.idInternal == idSupplier));
  }

  /**
   * Inserts provided supplier into suppliers Subject
   * @param supplier that will be added to suppliers Subject
   */
  createSupplier$(supplier: Supplier) {
    if (supplier) {
      this.suppliers.next([...this.suppliers.value, supplier]);
    }
  }

  /**
   * Updates suppliers Subject with a new value
   * @param suppliers new value that will be assigned to suppliers Subject
   */
  updateSuppliers$(suppliers: Supplier[]) {
    this.suppliers.next(suppliers);
  }

  /**
   * Update supplier Subject
   * @param supplier new value that will be assigned to supplier Subject
   * @param updateOnList if true, supplier will be updated also in suppliers Subject
   */
  updateSupplier$(supplier: Supplier, updateOnList: boolean) {
    // Update selected supplier
    this.supplier.next(supplier);
    // Update on list
    if (supplier && updateOnList)
      this.suppliers.next(
        this.suppliers.value.map(c => (+c.idSupplier === +supplier.idSupplier ? supplier : c)),
      );
  }

  /**
   * Sets supplier Subject to null and drop it out from suppliers Subject
   */
  deleteSupplier$() {
    // Remove also from list
    this.suppliers.next(
      this.suppliers.value.filter(c => c.idSupplier !== this.supplier.value.idSupplier),
    );
    // Remove selected supplier
    this.supplier.next(null);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Local Storage Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Update stored blank-template value
   * @param supplier
   */
  updateStoredTemplate(supplier: Supplier): void {
    localStorage.setItem('blank-supplier', JSON.stringify(supplier));
  }

  /**
   * Try to get blank-template from localStorage
   * instead of generating a new one
   */
  unshiftStoredTemplate(): void {
    let localStSupplier: Supplier;
    try {
      localStSupplier = JSON.parse(localStorage.getItem('blank-supplier'));
    } catch (error) {
      throw 'Failed to load blank-supplier from local storage';
    }
    if (!localStSupplier) {
      throw "There's no blank-supplier stored";
    }
    this.supplier.next(localStSupplier);
  }

  /**
   * Generates a blank-template for supplier
   * Then store it on localStorage and supplier Subject
   */
  unshiftTemplate(): void {
    // If there isn't already a blank supplier ...
    if (this.supplier.value?.idSupplier != BLANK_TEMPLATE_ID) {
      let newSupplier: Supplier = {
        idSupplier: -1,
        corporateName: 'New supplier',
      };
      // Insert blank user to be filled
      this.supplier.next(newSupplier);
      localStorage.setItem('blank-supplier', JSON.stringify(newSupplier));
      return;
    }
    console.error("There's already a supplier to be filled");
  }

  // -----------------------------------------------------------------------------------------------------
  // @ API Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Get suppliers from API
   * @param page page number to get
   * @param size size of pages
   * @param orderBy field name that will be used to order the result
   */
  getSuppliersRemote(
    query: PaginationSearch = undefined,
    page: number,
    size: number,
    orderBy?: string,
  ): Observable<Supplier[]> {
    let headers = new HttpHeaders();
    // Set pagination headers
    headers = headers.set('size', `${size}`);
    headers = headers.set('page', `${page + 1}`);
    headers = orderBy ? headers.set('order-by', orderBy) : headers;
    // Set query search headers
    if (query) {
      headers = headers.set('search-field', `${query.field}`);
      headers = headers.set('search-value', `${query.value}`);
    }
    let sentHeaders = {};
    headers.keys().forEach(key => (sentHeaders[key] = headers.get(key)));

    return this._httpClient
      .get<APIRes<PaginationResponse<Supplier>>>('core/supplier/page', { headers: headers })
      .pipe(
        map(res => res.data),
        switchMap(res => {
          // Change first page from1 1 (API) to 0 (mat-paginator)
          res.pagination.page--;
          // Save paginator
          this._pagination.next(res.pagination);
          // Return suppliers
          return of(res.page);
        }),
      );
  }

  /**
   * Get supplier by id from API
   * @param idSupplier
   */
  getSupplierByIdRemote(idSupplier: number): Observable<Supplier> {
    return this._httpClient
      .get<APIRes<Supplier>>(`core/supplier/${idSupplier}`)
      .pipe(map(res => res.data));
  }

  /**
   * Create supplier in API
   * @param supplier supplier that will be sent to API
   */
  createSupplierRemote(supplier: Supplier): Observable<Supplier> {
    return this._httpClient
      .post<APIRes<Supplier>>('core/supplier', { ...supplier })
      .pipe(map(res => res.data));
  }

  /**
   * Update supplier in API
   * @param idSupplier
   * @param supplier new value that will be assigned to specified supplier
   */
  updateSupplierRemote(idSupplier: number, supplier: Supplier): Observable<Supplier> {
    return this._httpClient
      .put<APIRes<Supplier>>(`core/supplier/${idSupplier}`, { ...supplier })
      .pipe(map(res => res.data));
  }

  /**
   * Delete supplier in API
   * @param idSupplier
   */
  deleteSupplierRemote(idSupplier: number): Observable<Supplier> {
    return this._httpClient
      .delete<APIRes<Supplier>>(`core/supplier/${idSupplier}`)
      .pipe(map(res => res.data));
  }
}
