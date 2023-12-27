import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  PrimaryColumn,
  PrimaryGeneratedColumn,
  CreateDateColumn,
  UpdateDateColumn,
} from 'typeorm';

@Entity({ name: TableName.CART_HEADER })
export class CartHeader {
  @PrimaryGeneratedColumn('uuid', { name: 'cart_id' })
  cartId: string;

  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  userName: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  cmpCode: string;

  @Column({
    name: 'cart_items',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  CartItems: string;

  @Column({
    name: 'schemes_code',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  schemeCodes: string;

  @Column({
    name: 'overall_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  overAllAmount: number;

  @Column({
    name: 'offer_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  offerAmount: number;

  @Column({
    name: 'discount_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  discountAmount: number;

  @Column({
    name: 'free_product',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  freeProduct: string;

  @Column({
    name: 'payable_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  payableAmount: number;

  @Column({
    name: 'is_apply_scheme',
    type: SQLColumnType.INT,
    nullable: false,
    default: 1,
  })
  isApplyScheme: number;

  @Column({
    name: 'is_active_sts',
    type: SQLColumnType.INT,
    nullable: false,
    default: 1,
  })
  isActiveSts: number;

  @CreateDateColumn()
  created_at: Date;

  @UpdateDateColumn()
  mod_dt: Date;
}
