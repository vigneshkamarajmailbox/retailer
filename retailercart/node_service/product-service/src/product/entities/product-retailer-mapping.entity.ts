import { Column, Entity, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.PRODUCT_RETAILER_MAPPING })
export class ProductRetailerMapping {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'prod_code',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  prodCode: string;

  @Column({
    name: 'retailer_code',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
    unique: true,
  })
  retailerCode: string;
}
