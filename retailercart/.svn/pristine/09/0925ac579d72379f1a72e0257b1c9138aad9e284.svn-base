import { Column } from 'typeorm';
import { SQLColumnType } from '../enum/column-type.enum';

export class AbstractModel {
  @Column({
    name: 'upload_flag',
    type: SQLColumnType.CHAR,
    default: 'N',
    select: false,
  })
  upload_flag: string;

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
