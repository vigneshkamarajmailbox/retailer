import { AbstractEntity } from 'src/shared/entities/base.model';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { Company } from 'src/shared/entities/company.entity';

@Entity({ name: TableName.CONTACT_US })
export class ContactUs {
  @Index()
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @Column({ name: 'email', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  email: string;

  @Column({ name: 'mobile_no', type: SQLColumnType.VARCHAR, length: 20, nullable: true })
  mobileNo: string;

  @Column({ name: 'toll_free_no', type: SQLColumnType.VARCHAR, length: 20, nullable: true })
  tollFreeNo: string;

  @ManyToOne(() => Company, (cmp) => cmp.cmpCode, {
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

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
