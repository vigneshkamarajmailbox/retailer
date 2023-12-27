import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  PrimaryColumn,
  PrimaryGeneratedColumn,
} from 'typeorm';

@Entity({ name: TableName.WISHLIST })
export class Wishlist {
  @PrimaryGeneratedColumn('uuid', { name: 'wishlist_id' })
  wishlistId: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  userName: string;

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
  prodMrp: string;

  @Column({
    name: 'prod_retailer_price',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: true,
  })
  prodRetailerPrice: string;
}
