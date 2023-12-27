import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SUPPLY_CHAIN_LEVEL })
export class SupplyChainMaster {
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
}
