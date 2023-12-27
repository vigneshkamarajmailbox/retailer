import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Retailer } from './retailer.entity';

@Entity({ name: TableName.RETAILER_CREDIT })
export class RetailerCredit {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false, select: false })
  userName: string;

  @Index()
  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @Column({ name: 'credit_bills', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  creditBills: string;

  @Column({ name: 'credit_days', type: SQLColumnType.VARCHAR, length: 50, nullable: true, select: false })
  creditDays: string;

  @Column({
    name: 'credit_limit',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  creditLimit: string;

  @Column({
    name: 'credit_usage',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  creditUsage: string;

  @Column({
    name: 'cash_disc_perc',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
    select: false,
  })
  cashDiscPerc: string;

  @Column({
    name: 'out_standing_amt',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  outStandingAmt: string;

  @ManyToOne(() => Retailer, (retailer) => retailer.credit, {
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
    {
      name: 'retailer_code',
      referencedColumnName: 'retailerCode',
    },
  ])
  retailer: Retailer;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
