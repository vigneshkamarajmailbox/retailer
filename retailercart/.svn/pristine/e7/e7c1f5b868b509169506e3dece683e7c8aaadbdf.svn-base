import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { ReturnTableStatusValue } from 'src/shared/enum/table-value.enum';
import {
  Column,
  Entity,
  Index,
  OneToMany,
  PrimaryColumn,
  PrimaryGeneratedColumn,
} from 'typeorm';
import { ReturnDetail } from './retrun-details.entity';

@Entity({ name: TableName.RETURN_HEADER })
@Index(TableName.RETURN_HEADER + '_index', ['returnNo', 'cmpCode', 'userName'])
export class ReturnHeader {
  @PrimaryGeneratedColumn('uuid', { name: 'return_no' })
  returnNo: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'order_no',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  orderNo: string;

  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  userName: string;

  @Column({
    name: 'return_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  returnDate: Date;

  @Column({
    name: 'return_type',
    type: SQLColumnType.VARCHAR,
    length: 20,
    nullable: true,
  })
  returnType: string;

  @Column({
    name: 'return_reason',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  returnReason: string;

  @Column({
    name: 'return_status',
    type: SQLColumnType.ENUM,
    enum: ReturnTableStatusValue,
    nullable: false,
  })
  returnStatus: string;

  @OneToMany(() => ReturnDetail, (detail) => detail.returnHeader, {
    eager: true,
  })
  returnDetail: ReturnDetail;
}
