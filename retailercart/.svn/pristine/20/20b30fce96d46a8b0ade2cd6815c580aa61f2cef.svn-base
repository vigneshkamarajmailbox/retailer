import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryColumn,
} from 'typeorm';
import { Screen } from './screen.entity';

@Entity({ name: TableName.SCREEN_ACCESS })
export class ScreenAccess {
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
    name: 'group_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  groupCode: string;

  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @Column({
    name: 'group_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  groupName: string;

  @Column({
    name: 'create_access',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  createAccess: boolean;

  @Column({
    name: 'edit_access',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  editAccess: boolean;

  @Column({
    name: 'view_access',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  viewAccess: boolean;

  @Column({
    name: 'delete_access',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  deleteAccess: boolean;

  @ManyToOne(() => Screen, (scr) => scr.screenAccess, {
    eager: true,
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'screen_no',
      referencedColumnName: 'screenNo',
    },
  ])
  screen: Screen;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
