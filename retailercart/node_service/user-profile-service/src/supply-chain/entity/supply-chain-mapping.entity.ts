import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SUPPLY_CHAIN_MAPPING })
export class SupplyChainMapping {
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
  @PrimaryColumn({
    name: 'sell_to_level_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  sellToLevelCode: string;

  @PrimaryColumn({
    name: 'mod_user_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  modUserCode: string;

  @PrimaryColumn({
    name: 'mod_dt',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  modDt: string;
}
