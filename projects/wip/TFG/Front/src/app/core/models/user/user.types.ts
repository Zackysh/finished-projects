export interface User {
  idUser: number;
  name: string;
  username: string;
  password: string;
  idGroup: number;
  idCompany: number;
  language?: string;
}

export interface Company {
  idCompany: number;
  cif: string;
  businessName: string;
  address: string;
  population: string;
  postal_code: string;
  phone: string;
  email: string;
  web: string;
}
