import {
  Column,
  Entity,
  JoinColumn,
  ManyToOne,
  OneToOne,
  OneToMany,
  PrimaryColumn,
} from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { AbstractEntity } from './abstract-entity';
import { DistributorStock } from './distributor-stock.entity';
import { ProductPricing } from './product-pricing.entity';
import { ProductEntity } from './product.entity';

@Entity({ name: TableName.PRODUCT_BATCH })
export class ProductBatch {
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

  @PrimaryColumn({
    name: 'prod_batch_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  prodBatchCode: string;

  @Column({
    name: 'manf_dt',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  manfDt: Date;

  @Column({
    name: 'expire_dt',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  expireDt: Date;

  @Column({
    name: 'batch_dt',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  batchDt: Date;

  @Column({
    name: 'is_latest',
    type: SQLColumnType.CHAR,
    length: 1,
    default: 'N',
  })
  isLatest: boolean;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;

  @ManyToOne(() => ProductEntity, (prod) => prod.productBatch, {
    cascade: true,
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
      //   foreignKeyConstraintName: TableName.PRODUCT_BATCH + 'fk_cmp_code',
    },
    {
      name: 'prod_code',
      referencedColumnName: 'prodCode',
      //   foreignKeyConstraintName: TableName.PRODUCT_BATCH + 'fk_prod_code',
    },
  ])
  product?: ProductEntity;

  @OneToMany(() => DistributorStock, (prod) => prod.distributorStock, {
    eager: true,
  })
  stock?: DistributorStock[];

  @OneToOne(() => ProductPricing, (prod) => prod.productBatch)
  productPricing?: ProductPricing;
}
