import { Transform, TransformFnParams } from 'class-transformer';
import { format } from 'date-fns';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.NOTIFICATION })
export class Notification extends AbstractModel {
  @PrimaryColumn({
    name: 'notification_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  notificationCode: string;

  @Column({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @Column({
    name: 'title',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  title: string;

  @Column({
    name: 'desc',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  desc: string;

  @Column({
    name: 'notification_time',
    type: SQLColumnType.DATETIME,
    nullable: true,
  })
  @Transform((params: TransformFnParams) => {
    const date = params.value;
    const formattedDate = format(date, 'dd-MM-yyyy hh:mm:ss aa');
    return formattedDate;
  })
  notificationTime: string;
}
