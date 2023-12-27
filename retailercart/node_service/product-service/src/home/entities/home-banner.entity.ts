import { Column, Entity, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.HOME_BANNER })
export class BannerEntity {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({ name: 'name', type: 'varchar', length: 50, nullable: false })
  name: string;

  @Column({
    name: 'sequence',
    type: SQLColumnType.INTEGER,
    nullable: false,
  })
  sequence: number;

  @Column({
    name: 'url',
    type: SQLColumnType.TEXT,
    nullable: true,
  })
  url: string;

  @Column({
    name: 'format',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  format: string;
}
