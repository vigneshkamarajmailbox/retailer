export class HomeDTO {
  key: string;
  highlights?: Highlight[];
  banner?: Banner[];
  title?: string;
  category?: Category[];
  brand?: Brand[];
  product?: ProductGroup[];
  subTitle?: string;
}

export interface Highlight {
  cmpCode: string;
  title: string;
  content: string;
}

export interface Banner {
  cmpCode: string;
  prodCode: string;
  prodName: string;
  tag: string;
  format: string;
  sequence: number;
  name: string;
}

export interface Category {
  cmpCode: string;
  categoryCode: string;
  name: string;
  image: string;
  sequence: number;
}

export interface Brand {
  cmpCode: string;
  code: string;
  name: string;
  image: string;
  sequence: number;
}

export interface ProductGroup {
  prodName: string;
  prodGroupCode: string;
  product: Product[];
  productMedia: ProductMedia[];
}

export interface Product {
  cmpCode: string;
  prodCode: string;
  batchCode: string;
  name: string;
  desc: string;
  shortName: string;
  shortDesc: string;
  currencySymbol: string;
  weightType: string;
  weightValue: string;
  mrp: number;
  priceToRetailer: number;
  suggestedQty: number;
  minimumOrderQty: number;
  uom: Uom[];
  productMedia: ProductMedia[];
  isWishlist: boolean;
  scheme: any;
  appliedScheme: any;
  priceAfterDiscount: any;
}

export interface Uom {
  code: string;
  name: string;
  conversionFactor: string;
}

export interface ProductMedia {
  mediaType: string;
  mediaUrl: string;
}
