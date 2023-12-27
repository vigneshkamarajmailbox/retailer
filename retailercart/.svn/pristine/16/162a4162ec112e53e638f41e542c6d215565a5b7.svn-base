import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SALES_HIER_LEVEL })
export class SalesHierarchyLevel {
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

  @Column(() => AbstractEntity, { prefix: false })
  keyFelid: AbstractEntity;
}
