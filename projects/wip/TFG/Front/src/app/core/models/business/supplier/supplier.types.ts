export interface Supplier {
  idInternal?: number;
  idSupplier: number;
  corporateName?: string;
  businessName?: string;
  type?: number;
  codContable?: number;
  vat?: string;
  address?: string;
  postalCode?: number;
  idCountry?: number;
  idPoblacion?: number;
  idProvincia?: number;
  particularState?: string;
  particularCity?: string;
  particularTown?: string;
  sex?: string;
  birthDate?: Date;
  phone1?: number;
  phone2?: number;
  contact?: string;
  email?: string;
  web?: string;
  newsletter?: number;
  popupMessage?: string;
  observations?: string;
  codEconomicZone?: number;
  idPaymentMethod?: number;
  chargeAccount?: number;
  salesAccount?: number;
  surcharge?: number;
  holdback?: number;
  idUser?: number;
  creationDate?: Date;
  modificationDate?: Date;
}

export interface PaymentMethod {
  idPaymentMethod: number;
  description: string;
}

export const DEFAULT_ORDER_BY = 'idInternal';
