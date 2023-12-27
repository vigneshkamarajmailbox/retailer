import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { UserEntity } from 'src/users/entities/user.entity';
import { Column, Entity, Index, JoinColumn, OneToOne, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.KPI })
export class Kpi {
  @Index()
  @PrimaryColumn({ name: 'kpi_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  kpiCode: string;

  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 150, nullable: false })
  userName: string;

  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @Column({ name: 'name', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  name: string;

  @Column({ name: 'target', type: SQLColumnType.VARCHAR, length: 75, nullable: true })
  target: string;

  @Column({ name: 'achieved', type: SQLColumnType.VARCHAR, length: 75, nullable: true })
  achieved: string;

  @Column({ name: 'achieved_percentage', type: SQLColumnType.VARCHAR, length: 75, nullable: true })
  achievedPercentage: string;

  @OneToOne(() => UserEntity, (user) => user.userName)
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
  ])
  User: UserEntity;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
