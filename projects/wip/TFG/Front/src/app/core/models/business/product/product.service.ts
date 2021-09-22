import { BehaviorSubject, Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes, BLANK_TEMPLATE_ID } from '../../core.types';
import { Pagination, PaginationResponse, PaginationSearch } from '../../pagination.types';
import { Scale } from '../scale/scale.types';
import { Product, ProductType } from './product.types';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  products = new BehaviorSubject<Product[] | null>(null);
  product = new BehaviorSubject<Product | null>(null);
  _pagination = new BehaviorSubject<Pagination | null>(null);

  /**
   * Constructor
   */
  constructor(private _httpClient?: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Subject Interaction
  // -----------------------------------------------------------------------------------------------------

  get product$(): Observable<Product> {
    return this.product.asObservable();
  }
  get products$(): Observable<Product[]> {
    return this.products.asObservable();
  }
  get pagination$(): Observable<Pagination> {
    return this._pagination.asObservable();
  }

  /**
   * Get product by id from in memory Subject
   * @param idProduct
   */
  getProductById$(idProduct: number): Observable<Product> {
    return of(this.products.value?.find(c => c.idProduct == idProduct));
  }

  /**
   * Inserts provided product into products Subject
   * @param product that will be added to products Subject
   */
  createProduct$(product: Product) {
    if (product) {
      this.products.next([...this.products.value, product]);
    }
  }

  /**
   * Updates products Subject with a new value
   * @param products new value that will be assigned to products Subject
   */
  updateProducts$(products: Product[]) {
    this.products.next(products);
  }

  /**
   * Update product Subject
   * @param product new value that will be assigned to product Subject
   * @param updateOnList if true, product will be updated also in products Subject
   */
  updateProduct$(product: Product, updateOnList: boolean = false) {
    // Update selected product
    this.product.next(product);
    // Update on list
    if (product && updateOnList)
      this.products.next(
        this.products.value.map(c => (+c.idProduct === +product.idProduct ? product : c)),
      );
  }

  /**
   * Sets product Subject to null and drop it out from products Subject
   */
  deleteProduct$() {
    // Remove also from list
    this.products.next(this.products.value.filter(c => c.idProduct !== this.product.value.idProduct));
    // Remove selected product
    this.product.next(null);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Local Storage Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Update stored blank-template value
   * @param product
   */
  updateStoredTemplate(product: Product): void {
    localStorage.setItem('blank-product', JSON.stringify(product));
  }

  /**
   * Try to get blank-template from localStorage
   * instead of generating a new one
   */
  unshiftStoredTemplate(): void {
    let localStProduct: Product;
    try {
      localStProduct = JSON.parse(localStorage.getProduct('blank-product'));
    } catch (error) {
      throw 'Failed to load blank-product from local storage';
    }
    if (!localStProduct) {
      throw "There's no blank-product stored";
    }
    this.product.next(localStProduct);
  }

  /**
   * Generates a blank-template for product
   * Then store it on localStorage and product Subject
   */
  unshiftTemplate(): void {
    // If there isn't already a blank product ...
    if (this.product.value?.idProduct !== BLANK_TEMPLATE_ID) {
      let newProduct: Product = {
        idProduct: -1,
        name: 'New Product',
      };
      // Insert blank product to be filled
      this.products.next([newProduct, ...this.products.value]);
      this.product.next(newProduct);
      localStorage.setItem('blank-product', JSON.stringify(newProduct));
      return;
    }
    console.error("There's already a product to be filled");
  }

  // -----------------------------------------------------------------------------------------------------
  // @ API Interaction
  // -----------------------------------------------------------------------------------------------------

  /**
   * Get products from API
   * @param page page number to get
   * @param size size of pages
   * @param orderBy field name that will be used to order the result
   */
  getProductsRemote(
    query: PaginationSearch = undefined,
    page: number,
    size: number,
    orderBy?: string,
  ): Observable<Product[]> {
    let headers = new HttpHeaders();
    // Set pagination headers
    headers = headers.set('size', `${size}`);
    headers = headers.set('page', `${page + 1}`);
    if (orderBy) headers = headers.set('order-by', orderBy);
    // Set query search headers
    if (query) {
      headers = headers.set('search-field', `${query.field}`);
      headers = headers.set('search-value', `${query.value}`);
    }

    return this._httpClient
      .get<APIRes<PaginationResponse<Product>>>('core/product/page', { headers: headers })
      .pipe(
        map(res => res.data),
        switchMap(res => {
          // Change first page from1 1 (API) to 0 (mat-paginator)
          res.pagination.page--;
          // Save paginator
          this._pagination.next(res.pagination);

          // Return products
          return of(res.page);
        }),
      );
  }

  getProductTypes(): Observable<ProductType[]> {
    return this._httpClient.get<APIRes<ProductType[]>>('core/product/type').pipe(
      map(res => res.data),
      switchMap(productTypes => {
        // Return product types
        return of(productTypes);
      }),
    );
  }

  /**
   * Get product by id from API
   * @param idProduct
   */
  getProductByIdRemote(idProduct: number): Observable<Product> {
    return this._httpClient.get<APIRes<Product>>(`core/product/${idProduct}`).pipe(map(res => res.data));
  }

  /**
   * Create product in API
   * @param product product that will be sent to API
   */
  createProductRemote(product: Product): Observable<Product> {
    return this._httpClient
      .post<APIRes<Product>>('core/product', { ...product })
      .pipe(map(res => res.data));
  }

  /**
   * Update product in API
   * @param idProduct
   * @param product new value that will be assigned to specified product
   */
  updateProductRemote(idProduct: number, product: Product): Observable<Product> {
    return this._httpClient.put<APIRes<Product>>(`core/product/${idProduct}`, { ...product }).pipe(
      map(res => {
        return res.data;
      }),
    );
  }

  /**
   * Delete product in API
   * @param idProduct
   */
  deleteProductRemote(idProduct: number): Observable<Product> {
    return this._httpClient
      .delete<APIRes<Product>>(`core/product/${idProduct}`)
      .pipe(map(res => res.data));
  }

  getScales(): Observable<Scale[]> {
    return this._httpClient.get<APIRes<Scale[]>>(`core/scale`).pipe(map(res => res.data));
  }
}
