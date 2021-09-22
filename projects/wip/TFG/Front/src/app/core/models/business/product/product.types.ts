export interface Product {
  idProduct: number;
  name?: string;
  idProductType?: number;
  description?: string;
  descriptionShort?: string;
  reference?: string;
  alternativeReference?: string;
  ean13?: string;
  note?: string;
  onSale?: boolean;
  active?: boolean;
  quantity?: number;
  outOfStock?: 0 | 1 | 2;
  createDate?: Date;
  updateDate?: Date;
}
export interface ProductType {
  idProductType: number;
  name: string;
}

export const ean13Regex = /^(?=.{12,12}$)[0-9]{12}/;
export const DEFAULT_ORDER_BY = 'idProduct';
