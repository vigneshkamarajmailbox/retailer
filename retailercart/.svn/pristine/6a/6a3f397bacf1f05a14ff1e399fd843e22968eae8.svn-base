import { Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';
import { SQLColumnType } from '../enum/column-type.enum';

export class AbstractModel {
  @Column({
    name: 'upload_flag',
    type: SQLColumnType.CHAR,
    default: 'N',
    select: false,
  })
  uploadFlag: string;

  @Column({
    name: 'status',
    type: SQLColumnType.BOOLEAN,
    default: true,
    select: false,
  })
  isActive: boolean;

  @CreateDateColumn({ name: 'created_at', select: false })
  createdAt: Date;

  @UpdateDateColumn({ name: 'mod_dt', select: false })
  modDt: Date;
}
