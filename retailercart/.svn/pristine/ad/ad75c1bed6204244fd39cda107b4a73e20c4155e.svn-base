import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  PrimaryColumn,
  PrimaryGeneratedColumn,
  OneToMany,
} from 'typeorm';
import { Order } from './order.entity';

@Entity({ name: TableName.ORDERHEADER })
export class OrderHeader extends AbstractModel {
  @PrimaryGeneratedColumn('uuid', { name: 'order_no' })
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

  // @Column({
  //   name: 'dist_code',
  //   type: SQLColumnType.VARCHAR,
  //   length: 50,
  //   nullable: false,
  // })
  // distCode: string;

  @Column({
    name: 'order_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  orderDate: Date;

  @Column({
    name: 'bill_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  billDate: Date;

  @Column({
    name: 'delivery_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  deliveryDate: Date;

  @Column({
    name: 'address_1',
    type: SQLColumnType.VARCHAR,
    length: 250,
    nullable: true,
  })
  address1: string;

  @Column({
    name: 'address_2',
    type: SQLColumnType.VARCHAR,
    length: 250,
    nullable: true,
  })
  address2: string;

  @Column({
    name: 'net_amount',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
    // type: SQLColumnType.DECIMAL,
    // precision: 22,
    // scale: 6,
    // nullable: false,
    // default: '0.000000',
  })
  netAmount: number;

  @Column({
    name: 'gross_amount',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
    // type: SQLColumnType.DECIMAL,
    // precision: 22,
    // scale: 6,
    // nullable: false,
    // default: '0.000000',
  })
  grossAmount: number;

  @Column({
    name: 'tax_amount',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
    // type: SQLColumnType.DECIMAL,
    // precision: 22,
    // scale: 6,
    // nullable: false,
    // default: '0.000000',
  })
  taxAmount: number;

  @Column({
    name: 'is_apply_scheme',
    type: SQLColumnType.INT,
    nullable: false,
    default: '0',
  })
  isApplyScheme: number;

  @Column({
    name: 'reorder_status',
    type: SQLColumnType.INT,
    nullable: false,
    select: false,
  })
  reOrderStatus: number;

  @Column({
    name: 'order_status',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  orderStatus: string;

  @OneToMany(() => Order, (OrderColumn) => OrderColumn.Order, {
    eager: true,
  })
  orderedProduct?: Order[];
}
