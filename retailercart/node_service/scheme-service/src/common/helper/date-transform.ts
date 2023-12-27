export class DateTransformer {
  to(data: string): Date {
    return new Date(data);
  }

  from(data: any): string {
    return data;
  }
}
