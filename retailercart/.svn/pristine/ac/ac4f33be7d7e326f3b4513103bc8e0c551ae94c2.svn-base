import { Transform, TransformFnParams } from 'class-transformer';
import { format } from 'date-fns';
import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn, PrimaryGeneratedColumn } from 'typeorm';

@Entity({ name: TableName.NOTIFICATION })
export class Notification {
  @PrimaryGeneratedColumn('uuid', {
    name: 'notification_code',
  })
  notificationCode: string;

  @PrimaryColumn({
    name: 'user_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
  })
  username: string;

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
    name: 'is_seen',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  isSeen: boolean;

  @Column({
    name: 'notification_time',
    type: SQLColumnType.DATE_TIME,
    nullable: true,
  })
  @Transform((params: TransformFnParams) => {
    const date = params.value;
    const formattedDate = format(date, 'dd-MM-yyyy hh:mm:ss aa');
    return formattedDate;
  })
  notificationTime: string;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
