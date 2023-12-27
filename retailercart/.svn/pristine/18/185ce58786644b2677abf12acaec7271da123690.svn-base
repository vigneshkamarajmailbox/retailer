import { Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';
import { SQLColumnType } from '../enum/column-type.enum';

export class AbstractModel {
  @Column({ name: 'upload_flag', type: SQLColumnType.CHAR, default: 'N' })
  uploadFlag: string;

  @Column({ name: 'status', type: SQLColumnType.BOOLEAN, default: true })
  isActive: boolean;

  @CreateDateColumn()
  created_at: Date;

  @UpdateDateColumn()
  mod_dt: Date;
}
