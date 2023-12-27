import { RetailerDistributorMapping } from 'src/retailer/entities/retailer-distr-mapping.entity';
import { Company } from 'src/shared/entities/company.entity';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, JoinColumn, ManyToOne, OneToMany, PrimaryColumn } from 'typeorm';
import { DistributorSalesHier } from './distributor-sales-hier.entity';

@Entity({ name: TableName.DISTRIBUTOR })
export class Distributor {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'distr_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  distrCode: string;

  @Column({ name: 'distr_name', type: SQLColumnType.VARCHAR, length: 100, nullable: true })
  distrName: string;

  @Column({ name: 'distr_addr_1', type: SQLColumnType.VARCHAR, length: 200, nullable: true })
  addressLine1: string;

  @Column({ name: 'distr_addr_2', type: SQLColumnType.VARCHAR, length: 200, nullable: true })
  addressLine2: string;

  @Column({ name: 'distr_addr_3', type: SQLColumnType.VARCHAR, length: 200, nullable: true })
  addressLine3: string;

  @Column({
    name: 'postal_code',
    type: SQLColumnType.INT,
    width: 6,
    nullable: true,
  })
  postalCode: number;

  @Column({
    name: 'phone_no',
    type: SQLColumnType.VARCHAR,
    length: 15,
    nullable: false,
  })
  phoneNo: string;

  @Column({
    name: 'mobile_no',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  mobileNo: string;

  @Column({
    name: 'contact_person',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  contactPerson: string;

  @Column({
    name: 'email',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  email: string;

  @Column({
    name: 'gst_state_code',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  GSTStateCode: string;

  @Column({
    name: 'day_off',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  dayOff: string;

  @Column({
    name: 'load_stock_prod',
    type: SQLColumnType.VARCHAR,
    length: 10,
    nullable: false,
  })
  loadStockProd: string;

  @Column({
    name: 'lob',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  lob: string;

  @ManyToOne(() => Company, (cmp) => cmp.distributor, {
    eager: false,
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
  ])
  company: Company;

  @OneToMany(() => DistributorSalesHier, (cmp) => cmp.distributor)
  distributorSalesHier: DistributorSalesHier[];

  @OneToMany(() => RetailerDistributorMapping, (retailer) => retailer.distributor)
  retailer: RetailerDistributorMapping;
}
