import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.LANGUAGE })
export class Language {
  @PrimaryColumn({
    name: 'code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  code: string;

  @Column({
    name: 'name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  name: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
