import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { UserEntity } from 'src/users/entities/user.entity';
import { Column, Entity, Index, JoinColumn, OneToMany, OneToOne, PrimaryColumn } from 'typeorm';
import { RetailerAddMapping } from './retailer-add-mapping.entity';
import { RetailerClassMapping } from './retailer-class-mapping.entity';
import { RetailerCredit } from './retailer-credit.entity';
import { RetailerDistributorMapping } from './retailer-distr-mapping.entity';
import { RetailerRouteMapping } from './retailer-route-mapping.entity';

@Entity({ name: TableName.RETAILER })
// @Index(['cmpCode', 'userName', 'retailerCode'])
export class Retailer {
  @Index()
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;
  @Index()
  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;
  @Index()
  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @Column({ name: 'retailer_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  retailerName: string;

  @Column({ name: 'mobile_no', type: SQLColumnType.VARCHAR, length: 10, nullable: false })
  mobileNo: string;

  @Column({ name: 'alt_mobile_no', type: SQLColumnType.VARCHAR, length: 10, nullable: true })
  altMobileNo: string;

  @Column({ name: 'email', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  email: string;

  @Column({ name: 'gst_no', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  gstNo: string;

  @Column({ name: 'pan_no', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  panNo: string;

  @Column({ name: 'group_code', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  groupCode: string;

  @OneToMany(() => RetailerCredit, (retailerCredit) => retailerCredit.retailer, {
    eager: true,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  credit?: RetailerCredit[];

  @OneToMany(() => RetailerAddMapping, (retailerAddMapping) => retailerAddMapping.retailer, {
    eager: true,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  address?: RetailerAddMapping[];

  @OneToOne(() => UserEntity, (user) => user.userName, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
  ])
  user: UserEntity;

  @OneToMany(() => RetailerDistributorMapping, (retailerDistMapping) => retailerDistMapping.retailer, {
    eager: true,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  retailerDistributorMapping?: RetailerDistributorMapping[];

  @OneToMany(() => RetailerClassMapping, (retailerClassMapping) => retailerClassMapping.retailer, {
    eager: true,
  })
  retailerClass?: RetailerClassMapping[];

  @OneToMany(() => RetailerRouteMapping, (retailerRouteMapping) => retailerRouteMapping.retailer, {
    eager: true,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  retailerRouteMapping?: RetailerRouteMapping[];

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
