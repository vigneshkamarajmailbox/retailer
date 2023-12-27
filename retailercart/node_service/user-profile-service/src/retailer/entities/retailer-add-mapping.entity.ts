import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Retailer } from './retailer.entity';

@Entity({ name: TableName.RETAILER_ADD_MAPPING })
export class RetailerAddMapping {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @Index()
  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @Column({ name: 'address_1', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  address1: string;

  @Column({ name: 'address_2', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  address2: string;

  @Column({ name: 'city_code', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  cityCode: string;

  @Column({ name: 'state_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  stateCode: string;

  @Column({ name: 'postal_code', type: SQLColumnType.VARCHAR, length: 30, nullable: true })
  postalCode: string;

  @Column({ name: 'gst_statCode', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  gstStateCode: string;

  @Column({ name: 'mobile_no', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  mobileNo: string;

  @Column({ name: 'default_address', type: SQLColumnType.BOOLEAN, default: true })
  defaultAddress: string;

  @ManyToOne(() => Retailer, (retailer) => retailer.address, {
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
      foreignKeyConstraintName: 'retailer_address_cmp_fk',
    },
    {
      name: 'user_name',
      referencedColumnName: 'userName',
      foreignKeyConstraintName: 'retailer_address_user_fk',
    },
    {
      name: 'retailer_code',
      referencedColumnName: 'retailerCode',
      foreignKeyConstraintName: 'retailer_address_code_fk',
    },
  ])
  retailer: Retailer;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
