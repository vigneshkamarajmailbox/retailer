import { Company } from 'src/shared/entities/company.entity';
import { Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { TableName } from '../../shared/enum/table-name.enum';
import { UserEntity } from './user.entity';

@Entity({ name: TableName.USER_COMPANY_MAPPING })
export class UserCompanyMapEntity {
  @PrimaryColumn({ name: 'user_name', type: 'varchar', length: 100, nullable: false })
  userName: string;

  @PrimaryColumn({ name: 'cmp_code', type: 'varchar', length: 50, nullable: false })
  cmpCode: string;

  @ManyToOne(() => UserEntity, (user) => user.company, {
    cascade: false,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
      foreignKeyConstraintName: 'fk_user_company_mapping_t_2',
    },
  ])
  user: UserEntity;

  @ManyToOne(() => Company, (cmp) => cmp.user, { cascade: false, onDelete: 'NO ACTION', onUpdate: 'NO ACTION' })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
      foreignKeyConstraintName: 'fk_user_company_mapping_t_1',
    },
  ])
  company: Company;
}
