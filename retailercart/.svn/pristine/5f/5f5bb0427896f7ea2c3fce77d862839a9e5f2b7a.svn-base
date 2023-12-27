import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  PrimaryColumn,
  PrimaryGeneratedColumn,
  CreateDateColumn,
  UpdateDateColumn,
} from 'typeorm';
import { QtyDetail } from '../dto/create-cart-detail.dto';

@Entity({ name: TableName.CART_DETAILS })
export class CartDetails {
  @PrimaryGeneratedColumn('uuid', { name: 'cart_list_id' })
  cartListId: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  userName: string;

  @PrimaryColumn({
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
    length: 250,
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

  //   @Column({
  //     name: 'prod_mrp',
  //     type: SQLColumnType.VARCHAR,
  //     length: 100,
  //     nullable: true,
  //   })
  //   prodMrp: string;

  //   @Column({
  //     name: 'prod_retailer_price',
  //     type: SQLColumnType.VARCHAR,
  //     length: 100,
  //     nullable: true,
  //   })
  //   prodRetailerPrice: string;

  @Column({
    name: 'prod_qty',
    type: SQLColumnType.BIGINT,
    nullable: true,
  })
  prodQty: number;

  @Column({
    name: 'minimum_qty',
    type: SQLColumnType.VARCHAR,
    length: 20,
    nullable: true,
  })
  minQty: number;

  @Column({
    name: 'prod_qty_details',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  cartQtyDetails: QtyDetail[];

  @Column({
    name: 'prod_uom_details',
    type: SQLColumnType.JSON,
    nullable: true,
  })
  cartUomDetails: string;

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
    name: 'isapply_scheme',
    type: SQLColumnType.INT,
    nullable: true,
  })
  isApplyScheme: number;

  @Column({
    name: 'scheme_percentage',
    type: SQLColumnType.INT,
    nullable: true,
  })
  schemePercentage: number;

  @Column({
    name: 'scheme_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  schemeAmount: number;

  @Column({
    name: 'tax_percentage',
    type: SQLColumnType.INT,
    nullable: true,
  })
  taxPercentage: number;

  @Column({
    name: 'tax_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  taxAmount: number;

  @Column({
    name: 'total_amount',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  totalAmount: number;

  @Column({
    name: 'is_free_product',
    type: SQLColumnType.INT,
    nullable: true,
  })
  isFreeProd: number;

  //   @Column({
  //     name: 'is_active',
  //     type: SQLColumnType.INT,
  //     nullable: false,
  //   })
  //   isActive: number;

  @Column({ name: 'status', type: SQLColumnType.BOOLEAN, default: true })
  isActive: boolean;

  @CreateDateColumn()
  created_at: Date;

  @UpdateDateColumn()
  mod_dt: Date;
}
