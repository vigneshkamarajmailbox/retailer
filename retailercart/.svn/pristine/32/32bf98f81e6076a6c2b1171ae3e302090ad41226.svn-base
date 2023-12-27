import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, Index, OneToMany, PrimaryColumn } from 'typeorm';
import { ScreenAccess } from './screen-access.entity';

@Entity({ name: TableName.SCREEN })
export class Screen {
  @PrimaryColumn({
    name: 'module_no',
    type: SQLColumnType.INT,
    nullable: false,
  })
  moduleNo: number;

  @Index()
  @PrimaryColumn({
    name: 'screen_no',
    type: SQLColumnType.INT,
    nullable: false,
  })
  screenNo: number;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @Column({
    name: 'module_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  moduleName: string;

  @Column({
    name: 'screen_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  screenName: string;

  @Column({
    name: 'screen_type',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  screenType: string;

  @Column({ name: 'sequence', type: SQLColumnType.INT, nullable: false })
  sequence: number;

  @OneToMany(() => ScreenAccess, (screenAccess) => screenAccess.screenNo, {
    lazy: true,
  })
  screenAccess?: ScreenAccess[];

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
