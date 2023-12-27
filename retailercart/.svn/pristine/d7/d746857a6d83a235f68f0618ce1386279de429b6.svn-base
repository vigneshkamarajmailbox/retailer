import { Column, Entity, PrimaryColumn, CreateDateColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.PRODUCT_TAX_INFO })
export class ProductTaxInfo {
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
    unique: true,
  })
  prodCode: string;

  @Column({
    name: 'tax_state_code',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  taxStateCode: string;

  @Column({
    name: 'cgst',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  CGST: string;

  @Column({
    name: 'igst',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  IGST: string;

  @Column({
    name: 'sgst',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  SGST: string;

  @Column({
    name: 'cess',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  CESS: string;

  @Column({
    name: 'additional_tax',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  additionalTax: string;

  @Column({
    name: 'upload_flag',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  uploadFlag: string;

  @Column({
    name: 'hsn_code',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  hsnCode: string;

  @CreateDateColumn()
  modDt: Date;
}
