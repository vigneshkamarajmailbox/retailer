import { AbstractEntity } from 'src/shared/entities/base.model';
import { UserEntity } from 'src/users/entities/user.entity';
import { Column, Entity, Index, JoinColumn, OneToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.KYC })
export class KycDetail {
  @Index()
  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'account_no', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  accountNo: number;

  @Column({ name: 'account_name', type: SQLColumnType.VARCHAR, length: 250, nullable: false })
  accountName: string;

  @Column({ name: 'bank_name', type: SQLColumnType.VARCHAR, length: 250, nullable: false })
  bankName: string;

  @Column({ name: 'bank_branch', type: SQLColumnType.VARCHAR, length: 250, nullable: false })
  bankBranch: string;

  @Column({ name: 'ifsc', type: SQLColumnType.VARCHAR, length: 20, nullable: false })
  ifsc: string;

  @Column({ name: 'email', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  email: string;

  @Column({ name: 'doc_image', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  documentImage: string;

  @Column({ name: 'doc_type', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  documentType: string;

  @OneToOne(() => UserEntity, (user) => user.userName)
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
  ])
  User: UserEntity;

  @Column(() => AbstractEntity, { prefix: false })
  baseEntity: AbstractEntity;
}
