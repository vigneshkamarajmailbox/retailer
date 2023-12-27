import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryColumn,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { ReturnHeader } from './return-header.entity';

@Entity({ name: TableName.RETURN_DETAILS })
export class ReturnDetail {
  @PrimaryGeneratedColumn('uuid', { name: 'item_id' })
  id: string;

  @PrimaryColumn({
    name: 'return_no',
    type: SQLColumnType.VARCHAR,
    length: 36,
    nullable: false,
  })
  returnNo: string;

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
    name: 'prod_uom_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  prodUomCode: string;

  @Column({
    name: 'prod_qty',
    type: SQLColumnType.VARCHAR,
    length: 20,
    nullable: true,
  })
  prodQty: number;

  @Column({
    name: 'return_qty',
    type: SQLColumnType.VARCHAR,
    length: 20,
    nullable: true,
  })
  returnQty: number;

  @ManyToOne(() => ReturnHeader, (header) => header.returnDetail, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'return_no',
      referencedColumnName: 'returnNo',
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
  returnHeader: ReturnHeader;
}
