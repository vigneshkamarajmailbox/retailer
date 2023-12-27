import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Distributor } from './distributor.entities';
import { SalesHierValueEntity } from 'src/sales-hierarchy/entity/sales-hier-value.entity';

@Entity({ name: TableName.DISTRIBUTOR_SALES_HIER })
export class DistributorSalesHier {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'distr_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  distrCode: string;

  @PrimaryColumn({
    name: 'sales_force_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  salesForceCode: string;

  @PrimaryColumn({
    name: 'lob_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  lobCode: string;

  @Column({
    name: 'sales_hier_path',
    type: SQLColumnType.VARCHAR,
    length: 500,
    nullable: false,
  })
  salesHierPath: string;

  @ManyToOne(() => Distributor, (distr) => distr.distributorSalesHier, {
    cascade: false,
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'distr_code',
      referencedColumnName: 'distrCode',
    },
  ])
  distributor?: Distributor;

  @ManyToOne(() => SalesHierValueEntity, (salesHierValueEntity) => salesHierValueEntity.valueCode, { eager: true })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'sales_force_code',
      referencedColumnName: 'valueCode',
    },
  ])
  salesHierValue?: SalesHierValueEntity;
}
