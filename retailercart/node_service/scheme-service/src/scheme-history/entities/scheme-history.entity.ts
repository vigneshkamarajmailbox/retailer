import { Scheme } from 'src/scheme/entities/scheme.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { SchemeHistoryStatusValue } from 'src/shared/enum/table-value.enum';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SCHEME_HISTROY })
export class SchemeHistory {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'sch_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  schemeCode: string;

  @PrimaryColumn({
    name: 'user_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  userCode: string;

  @PrimaryColumn({
    name: 'cart_id',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cartId: string;

  @PrimaryColumn({
    name: 'order_id',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  orderId: string;

  @PrimaryColumn({
    name: 'sch_status',
    type: SQLColumnType.ENUM,
    enum: SchemeHistoryStatusValue,
    nullable: false,
  })
  schemeStatus: SchemeHistoryStatusValue;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (scheme) => scheme.schemeHistory, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'sch_code',
      referencedColumnName: 'schemeCode',
    },
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
  ])
  scheme: Scheme;
}
