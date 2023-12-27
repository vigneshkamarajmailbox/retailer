import { AbstractEntity } from 'src/shared/entities/base.model';
import { Column, Entity, Index, OneToMany, OneToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { UserCompanyMapEntity } from './user-company-mapping.entity';
import { UserOTPEntity } from './user-otp.entity';

@Entity({ name: TableName.USER })
export class UserEntity {
  @Index()
  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false, unique: true })
  userName: string;

  @Index()
  @PrimaryColumn({
    name: 'mobile_no',
    type: SQLColumnType.VARCHAR,
    length: 10,
  })
  mobileNo: number;

  @Column({
    name: 'gst_no',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  gstNo: string;

  @Column({
    name: 'first_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  firstName: string;

  @Column({
    name: 'last_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  lastName: string;

  @Column({
    name: 'group_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
    default: 'Retailer',
  })
  groupCode: string;

  @Column({
    name: 'email',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  email: string;

  @Column({
    name: 'password',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  password: string;

  @Column({
    name: 'pan_no',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  panNo: string;

  @Column({
    name: 'postal_code',
    type: SQLColumnType.INT,
    width: 6,
    nullable: true,
  })
  postalCode: number;

  @OneToOne(() => UserOTPEntity, (user) => user.user, {
    eager: true,
  })
  userOTP?: UserOTPEntity;

  @OneToMany(() => UserCompanyMapEntity, (cmp) => cmp.user, {
    eager: true,
  })
  company?: UserCompanyMapEntity[];

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
