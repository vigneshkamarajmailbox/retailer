import {
  Column,
  CreateDateColumn,
  Entity,
  Index,
  JoinColumn,
  OneToOne,
  PrimaryColumn,
  UpdateDateColumn,
} from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { UserEntity } from './user.entity';

@Entity({ name: TableName.USER_OTP })
export class UserOTPEntity {
  @Index({ unique: true })
  @PrimaryColumn({ name: 'mobile_no', type: SQLColumnType.VARCHAR, length: 10, nullable: false })
  mobileNo: number;

  @Column({ name: 'otp', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  otp: number;

  @CreateDateColumn({ name: 'expired_time' })
  expiredTime: Date;

  @UpdateDateColumn({ name: 'mod_dt' })
  modDt: Date;

  @OneToOne(() => UserEntity, (user) => user.userOTP, { cascade: false })
  @JoinColumn([
    {
      name: 'mobile_no',
      referencedColumnName: 'mobileNo',
    },
  ])
  user: UserEntity;
}
