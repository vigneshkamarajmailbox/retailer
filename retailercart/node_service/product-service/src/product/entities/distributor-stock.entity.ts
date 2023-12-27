import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductBatch } from './product-batch.entity';

@Entity({ name: TableName.PRODUCT_DISTRIBUTOR_STOCK })
export class DistributorStock {
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
    name: 'distr_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  distrCode: string;

  @PrimaryColumn({
    name: 'batch_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  batchCode: string;

  @Column({
    name: 'sellable',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  sellable: number;

  @Column({
    name: 'unsellable',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  unSellable: number;

  @Column({
    name: 'offer',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  offer: number;

  @ManyToOne(() => ProductBatch, (prod) => prod.stock, { cascade: true })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'prod_code',
      referencedColumnName: 'prodCode',
    },
    {
      name: 'batch_code',
      referencedColumnName: 'prodBatchCode',
    },
  ])
  distributorStock: ProductBatch;
}
