export interface PaginationResponse<T> {
  page: T[];
  pagination: Pagination;
}

export interface Pagination {
  size: number;
  page: number;
  count: number;
  searchCount?: number;
}

export interface PaginationSearch {
  field: string;
  value: string | number;
}

export const DEFAULT_SIZE = 15;
export const DEFAULT_PAGE = 0;
