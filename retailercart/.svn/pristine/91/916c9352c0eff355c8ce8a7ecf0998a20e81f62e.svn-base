import { Distributor } from 'src/distributor/entities/distributor.entities';
import { UserCompanyMapEntity } from 'src/users/entities/user-company-mapping.entity';
import { Column, Entity, OneToMany, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../enum/column-type.enum';
import { TableName } from '../enum/table-name.enum';
import { AbstractEntity } from './base.model';

@Entity({ name: TableName.COMPANY })
export class Company {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;
  @Column({ name: 'cmp_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  cmpName: string;
  @Column({ name: 'org_type', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  orgType: string;
  @Column({ name: 'cmp_image_name', type: SQLColumnType.TEXT, nullable: false })
  cmpImageName: string;
  @Column({ name: 'org_image_name', type: SQLColumnType.TEXT, nullable: false })
  orgImageName: string;

  @OneToMany(() => UserCompanyMapEntity, (distr) => distr.company)
  user?: UserCompanyMapEntity[];

  @OneToMany(() => Distributor, (distr) => distr.company)
  distributor?: Distributor;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
