import { Column, Entity, JoinColumn, OneToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductBatch } from './product-batch.entity';

@Entity({ name: TableName.PRODUCT_PRICING })
export class ProductPricing {
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
    length: 50,
    nullable: false,
  })
  prodCode: string;

  @Column({
    name: 'retailer_type',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  retailerType: string;

  @PrimaryColumn({
    name: 'batch_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  batchCode: string;

  @Column({ name: 'mrp', type: SQLColumnType.INTEGER, nullable: false })
  mrp: number;

  @Column({
    name: 'price_with_tax',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  priceWithTax: number; // sellratewith tax

  @Column({
    name: 'price_to_retailer',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  priceToRetailer: number; // sellrate

  @Column({
    name: 'minimum_order_qty',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  minimumOrderQty: number;

  @Column({
    name: 'suggested_qty',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  suggestedQty: number;

  @OneToOne(() => ProductBatch, (prod) => prod.productPricing, {
    cascade: true,
  })
  @JoinColumn([
    {
      name: 'cmp_code', // current
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'prod_code', // current table column name
      referencedColumnName: 'prodCode',
    },
    {
      name: 'batch_code', // current
      referencedColumnName: 'prodBatchCode',
    },
  ])
  productBatch: ProductBatch;
}
