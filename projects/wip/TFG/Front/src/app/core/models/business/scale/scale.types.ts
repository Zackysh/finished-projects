export interface Scale {
  idScale?: number;
  idScaleManufacturer: number;
  idScaleType: number;
  comType: number;
  name: string;
  comPort: string;
  idCom: number;
  baudRate: number;
  parity: 'none' | 'even' | 'mark' | 'odd' | 'space';
  dataBits: 8 | 7 | 6 | 5;
  stopBit: 1 | 2;
  ip: string;
  port: number;
  active: boolean;
  idUser: number;
  useQueue: boolean;
}

export interface TruckScale extends Scale {
	weight1?: number;
  weight2?: number;
  weightNet?: number;
  weightType?: number;
  idProduct?: number;
  weight1Datetime?: string;
  weight2Datetime?: string;
  licensePlate?: string;
}

export interface Crate {
	idCrate: number;
	name: string;
}

export interface Hopper {
	idHopper: number;
	name: string;
}

export interface ScaleType {
	idScaleType: number;
	name: string;
}