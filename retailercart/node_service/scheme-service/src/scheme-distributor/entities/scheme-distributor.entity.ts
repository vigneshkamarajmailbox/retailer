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

@Entity({ name: TableName.SCHEME_DISTRIBUTOR })
export class SchemeDistributor {
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

  @PrimaryColumn({
    name: 'dist_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  distCode: string;

  @Column({
    name: 'budget',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  budget: string;

  @Column({
    name: 'balance_budget',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  balanceBudget: string;

  @Column({
    name: 'is_unlimited',
    type: SQLColumnType.TINYINT,
    nullable: false,
  })
  isUnlimited: string;

  @Column({
    name: 'is_retailer_budget',
    type: SQLColumnType.TINYINT,
    nullable: false,
  })
  isRetailerBudget: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (scheme) => scheme.distributorData, {
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
