import { Scheme } from 'src/scheme/entities/scheme.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryColumn,
} from 'typeorm';

@Entity({ name: TableName.APPLIED_SCHEME })
export class AppliedScheme {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'sch_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  schemeCode: string;

  @Column({
    name: 'dist_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  distCode: string;

  @Column({
    name: 'user_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  userCode: string;

  @PrimaryColumn({
    name: 'order_no',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  orderNo: string;

  @Column({
    name: 'sch_value',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  schemeValue: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (scheme) => scheme.appliedScheme, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'sch_code',
      referencedColumnName: 'schemeCode',
    },
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
  ])
  scheme: Scheme;
}
