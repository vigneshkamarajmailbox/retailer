import { Column, Entity, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.HOME_HIGHLIGHTS })
export class HighlightsEntity {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'title',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  title: string;

  @Column({
    name: 'content',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  content: string;

  @Column({
    name: 'image',
    type: SQLColumnType.TEXT,
    nullable: true,
  })
  image: string;
}
