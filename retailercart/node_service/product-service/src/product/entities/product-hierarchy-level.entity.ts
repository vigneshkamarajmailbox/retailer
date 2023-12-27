import { Column, Entity, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.PRODUCT_HIERARCHY_LEVEL })
export class ProductHierarchyLevel {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'level_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  levelCode: string;

  @Column({
    name: 'level_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  levelName: string;

  @Column({
    name: 'mod_user_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  modUserCode: string;

  @Column({
    name: 'mod_dt',
    type: SQLColumnType.DATE_TIME,
    default: () => 'now()',
  })
  modDt: Date;
}
