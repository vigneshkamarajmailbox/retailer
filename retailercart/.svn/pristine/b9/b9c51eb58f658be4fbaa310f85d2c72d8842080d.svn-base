import { Distributor } from 'src/distributor/entities/distributor.entities';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Retailer } from './retailer.entity';

@Entity({ name: TableName.RETAILER_DISTR_MAPPING })
export class RetailerDistributorMapping {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @PrimaryColumn({ name: 'distr_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  distrCode: string;

  @ManyToOne(() => Retailer, (retailer) => retailer.retailerDistributorMapping)
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
      foreignKeyConstraintName: 'fk_retailer_distr_mapping_t_1',
    },
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
      foreignKeyConstraintName: 'fk_retailer_distr_mapping_t_1',
    },
    {
      name: 'retailer_code',
      referencedColumnName: 'retailerCode',
      foreignKeyConstraintName: 'fk_retailer_distr_mapping_t_1',
    },
  ])
  retailer: Retailer;

  @ManyToOne(() => Distributor, (distr) => distr.retailer, {
    eager: true,
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
}
