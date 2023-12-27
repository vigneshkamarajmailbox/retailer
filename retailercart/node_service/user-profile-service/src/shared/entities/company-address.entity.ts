import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../enum/column-type.enum';
import { TableName } from '../enum/table-name.enum';
import { AbstractEntity } from './base.model';
import { Company } from './company.entity';

@Entity({ name: TableName.COMPANY_ADDRESS })
export class CompanyAddress {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 150, nullable: false })
  address1: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 150, nullable: false })
  address2: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  country: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  state: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  city: string;

  @Column({ type: SQLColumnType.TINY_INT, scale: 1, default: false })
  isMainBranch: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  landmark: string;

  @Column({ type: SQLColumnType.VARCHAR, length: 10, nullable: false })
  postal_code: string;

  @ManyToOne(() => Company, (cmp) => cmp.cmpCode, {
    eager: true,
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
