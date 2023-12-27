import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.CONFIGURATION })
export class Configuration {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'user_group_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  userGroupCode: string;

  @PrimaryColumn({
    name: 'config_key',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  configKey: string;

  @Column({ name: 'module_no', type: SQLColumnType.INT, nullable: false })
  moduleNo: number;

  @Column({ name: 'screen_no', type: SQLColumnType.INT, nullable: false })
  screenNo: number;

  @Column({
    name: 'config_value',
    type: SQLColumnType.BOOLEAN,
    nullable: false,
  })
  configValue: number;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
