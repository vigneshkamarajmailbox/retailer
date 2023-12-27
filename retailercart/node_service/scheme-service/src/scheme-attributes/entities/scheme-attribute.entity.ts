import { SchemeHierType } from 'src/scheme-hier-type/entities/scheme-hier-type.entity';
import { Scheme } from 'src/scheme/entities/scheme.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { SchemeHierTypeValue } from 'src/shared/enum/table-value.enum';
import {
  Column,
  Entity,
  Index,
  JoinColumn,
  ManyToOne,
  PrimaryColumn,
} from 'typeorm';

@Entity({ name: TableName.SCHEME_ATTRIBUTES })
export class SchemeAttribute {
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
    name: 'attr_type',
    type: SQLColumnType.ENUM,
    enum: SchemeHierTypeValue,
    nullable: false,
  })
  attributeType: string;

  @PrimaryColumn({
    name: 'attr_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  attributeValue: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (sch) => sch.schemeAttribute, {
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

  @ManyToOne(() => SchemeHierType, (hier) => hier.schemeAttribute, {
    eager: true,
  })
  @JoinColumn([
    {
      name: 'attr_type',
      referencedColumnName: 'code',
    },
  ])
  hierType: SchemeHierType;
}
