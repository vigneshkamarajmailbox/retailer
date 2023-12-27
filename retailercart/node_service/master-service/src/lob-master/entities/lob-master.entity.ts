import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.LOB_MASTER })
export class LobMaster {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'lob_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  lobCode: string;

  @Column({
    name: 'lob_name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  lobName: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
