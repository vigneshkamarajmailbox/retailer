export class ColumnNumericTransformer {
  to(data: number): number {
    return data;
  }
  from(data: any): number {
    return Number(data);
  }
}
