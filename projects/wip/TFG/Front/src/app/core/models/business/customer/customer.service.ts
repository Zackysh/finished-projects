import { BehaviorSubject, Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes as APIRes, BLANK_TEMPLATE_ID } from '../../core.types';
import { Pagination, PaginationResponse, PaginationSearch } from '../../pagination.types';
import { Customer, PaymentMethod } from './customer.types';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  customers = new BehaviorSubject<Customer[] | null>(null);
  customer = new BehaviorSubject<Customer | null>(null);
  _pagination = new BehaviorSubject<Pagination | null>(null);

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Subject Interaction
  // -----------------------------------------------------------------------------------------------------

  get customer$(): Observable<Customer> {
    return this.customer.asObservable();
  }
  get customers$(): Observable<Customer[]> {
    return this.customers.asObservable();
  }
  get pagination$(): Observable<Pagination> {
    return this._pagination.asObservable();
  }

  /**
   * Get customer by id from Subject
   * @param idCustomer
   */
  getCustomerById$(idCustomer: number): Observable<Customer> {
    return of(this.customers.value?.find(c => c.idInternal == idCustomer));
  }

  /**
   * Inserts provided customer into customers Subject
   * @param customer that will be added to customers Subject
   */
  createCustomer$(customer: Customer) {
    if (customer) {
      this.customers.next([...this.customers.value, customer]);
    }
  }

  /**
   * Updates customers Subject with a new value
   * @param customers new value that will be assigned to customers Subject
   */
  updateCustomers$(customers: Customer[]) {
    this.customers.next(customers);
  }

  /**
   * Update customer Subject
   * @param customer new value that will be assigned to customer Subject
   * @param updateOnList if true, customer will be updated also in customers Subject
   */
  updateCustomer$(customer: Customer, updateOnList: boolean) {
    // Update selected customer
    this.customer.next(customer);
    // Update on list
    if (customer && updateOnList)
      this.customers.next(
        this.customers.value.map(c => (+c.idInternal === +customer.idInternal ? customer : c)),
      );
  }

  /**
   * Sets customer Subject to null and drop it out from customers Subject
   */
  deleteCustomer$() {
    // Remove also from list
    this.customers.next(
      this.customers.value.filter(c => c.idInternal !== this.customer.value.idInternal),
    );
    // Remove selected customer
    this.customer.next(null);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Local Storage Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Update stored blank-template value
   * @param customer
   */
  updateStoredTemplate(customer: Customer): void {
    localStorage.setItem('blank-customer', JSON.stringify(customer));
  }

  /**
   * Try to get blank-template from localStorage
   * instead of generating a new one
   */
  unshiftStoredTemplate(): void {
    let localStCustomer: Customer;
    try {
      localStCustomer = JSON.parse(localStorage.getItem('blank-customer'));
    } catch (error) {
      throw 'Failed to load blank-customer from local storage';
    }
    if (!localStCustomer) {
      throw "There's no blank-customer stored";
    }
    this.customer.next(localStCustomer);
  }

  /**
   * Generates a blank-template for customer
   * Then store it on localStorage and customer Subject
   */
  unshiftTemplate(): void {
    // If there isn't already a blank customer ...
    if (this.customer.value?.idInternal != BLANK_TEMPLATE_ID) {
      let newCustomer: Customer = {
        idInternal: -1,
        corporateName: 'New customer',
      };
      // Insert blank user to be filled
      this.customer.next(newCustomer);
      localStorage.setItem('blank-customer', JSON.stringify(newCustomer));
      return;
    }
    console.error("There's already a customer to be filled");
  }

  // -----------------------------------------------------------------------------------------------------
  // @ API Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Get customers from API
   * @param page page number to get
   * @param size size of pages
   * @param orderBy field name that will be used to order the result
   */
  getCustomersRemote(
    query: PaginationSearch = undefined,
    page: number,
    size: number,
    orderBy?: string,
  ): Observable<Customer[]> {
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
      .get<APIRes<PaginationResponse<Customer>>>('core/customer/page', { headers: headers })
      .pipe(
        map(res => res.data),
        switchMap(res => {
          // Change first page from1 1 (API) to 0 (mat-paginator)
          res.pagination.page--;
          // Save paginator
          this._pagination.next(res.pagination);
          // Return customers
          return of(res.page);
        }),
      );
  }

  upload(file: File) {
    let formData = new FormData();
    formData.append('file', file[0]);

    return this._httpClient.post<Customer>(`core/image`, formData).subscribe(res => console.log(res));
  }

  /**
   * Get customer by id from API
   * @param idCustomer
   */
  getCustomerByIdRemote(idCustomer: number): Observable<Customer> {
    return this._httpClient
      .get<APIRes<Customer>>(`core/customer?idCustomer=${idCustomer}`)
      .pipe(map(res => res.data));
  }

  /**
   * Get available payment methods from API
   */
  getPaymentMethods(): Observable<PaymentMethod[]> {
    return this._httpClient
      .get<APIRes<PaymentMethod[]>>('core/customer/payment-methods')
      .pipe(map(res => res.data));
  }

  /**
   * Create customer in API
   * @param customer customer that will be sent to API
   */
  createCustomerRemote(customer: Customer): Observable<Customer> {
    return this._httpClient
      .post<APIRes<Customer>>('core/customer', { ...customer })
      .pipe(map(res => res.data));
  }

  /**
   * Update customer in API
   * @param idCustomer
   * @param customer new value that will be assigned to specified customer
   */
  updateCustomerRemote(idCustomer: number, customer: Customer): Observable<Customer> {
    return this._httpClient
      .put<APIRes<Customer>>(`core/customer?idCustomer=${idCustomer}`, {
        ...customer,
      })
      .pipe(map(res => res.data));
  }

  /**
   * Delete customer in API
   * @param idCustomer
   */
  deleteCustomerRemote(idCustomer: number): Observable<Customer> {
    return this._httpClient
      .delete<APIRes<Customer>>(`core/customer?idCustomer=${idCustomer}`)
      .pipe(map(res => res.data));
  }
}
