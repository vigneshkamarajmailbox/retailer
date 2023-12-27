import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { Column } from 'typeorm';

export class AbstractEntity {
  @Column({
    name: 'upload_flag',
    type: SQLColumnType.CHAR,
    default: 'N',
    select: false,
  })
  uploadFlag: string;

  @Column({
    name: 'status',
    type: SQLColumnType.CHAR,
    default: 'Y',
    select: false,
  })
  isActive: string;

  @Column({
    name: 'created_at',
    type: SQLColumnType.DATE_TIME,
    select: false,
    default: () => 'now()',
  })
  createdAt: Date;

  @Column({
    name: 'mod_dt',
    type: SQLColumnType.DATE_TIME,
    select: false,
    default: () => 'now()',
  })
  modDt: Date;
}
