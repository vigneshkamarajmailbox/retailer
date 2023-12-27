import { Scheme } from 'src/scheme/entities/scheme.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryColumn,
} from 'typeorm';

@Entity({ name: TableName.SCHEME_RETAILER_CATEGORY })
export class SchemeReatailerCategory {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'sch_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  schemeCode: string;

  @PrimaryColumn({
    name: 'channel_code',
    type: SQLColumnType.VARCHAR,
    length: 70,
    nullable: false,
  })
  channelCode: string;

  @PrimaryColumn({
    name: 'group_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  groupCode: string;

  @PrimaryColumn({
    name: 'class_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  classCode: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (sch) => sch.schemeRetailerCategory, {
    lazy: true,
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
