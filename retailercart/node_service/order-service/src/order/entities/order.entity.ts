import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  PrimaryGeneratedColumn,
  PrimaryColumn,
  JoinColumn,
  ManyToOne,
  CreateDateColumn,
  UpdateDateColumn,
} from 'typeorm';
import { OrderHeader } from './order-header.entity';

@Entity({ name: TableName.ORDER })
export class Order {
  @PrimaryGeneratedColumn('uuid', { name: 'id' })
  id: string;

  @PrimaryColumn({
    name: 'order_no',
    type: SQLColumnType.VARCHAR,
    length: 36,
    nullable: false,
  })
  orderNo: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  userName: string;

  @Column({
    name: 'dist_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  distCode: string;

  @Column({
    name: 'prod_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  prodCode: string;

  @Column({
    name: 'prod_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: true,
  })
  prodName: string;

  @Column({
    name: 'batch_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: true,
  })
  batchCode: string;

  @Column({
    name: 'prod_mrp',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: true,
  })
  prodMrp: number;

  @Column({
    name: 'prod_retailer_price',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: true,
  })
  prodRetailerPrice: number;

  @Column({
    name: 'prod_img',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  prodImage: string;

  @Column({
    name: 'prod_qty',
    type: SQLColumnType.VARCHAR,
    length: 20,
    nullable: true,
  })
  prodQty: number;

  @Column({
    name: 'prod_uom_code',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  uomCode: string;

  @Column({
    name: 'weight_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  weightCode: string;

  @Column({
    name: 'weight_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  weightValue: string;

  @Column({
    name: 'tax_percentge',
    type: SQLColumnType.INT,
    nullable: true,
  })
  taxPercentage: number;

  @Column({
    name: 'tax_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
    // type: SQLColumnType.DECIMAL,
    // precision: 22,
    // scale: 6,
    // nullable: false,
    // default: '0.000000',
  })
  taxAmount: number;

  @Column({
    name: 'total_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
    // type: SQLColumnType.DECIMAL,
    // precision: 22,
    // scale: 6,
    // nullable: false,
    // default: '0.000000',
  })
  totalAmount: number;

  @Column({
    name: 'order_status',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  orderStatus: string;

  @Column({
    name: 'is_free_prod',
    type: SQLColumnType.BOOLEAN,
    default: true,
    select: false,
  })
  isFree: boolean;

  @Column({
    name: 'status',
    type: SQLColumnType.BOOLEAN,
    default: true,
    select: false,
  })
  isActive: boolean;

  @CreateDateColumn({ select: false })
  created_at: Date;

  @UpdateDateColumn({ select: false })
  mod_dt: Date;

  @ManyToOne(
    () => OrderHeader,
    (orderHeaderColoumn) => orderHeaderColoumn.orderedProduct,
    {
      onDelete: 'NO ACTION',
      onUpdate: 'NO ACTION',
    },
  )
  @JoinColumn([
    {
      name: 'order_no',
      referencedColumnName: 'orderNo',
    },
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
  ])
  Order: OrderHeader;
}
