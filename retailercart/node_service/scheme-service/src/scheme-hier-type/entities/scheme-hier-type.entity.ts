import { SchemeAttribute } from 'src/scheme-attributes/entities/scheme-attribute.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { SchemeHierTypeValue } from 'src/shared/enum/table-value.enum';
import { Column, Entity, Index, OneToMany, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SCHEME_HIER_TYPE })
export class SchemeHierType {
  @Index()
  @PrimaryColumn({
    name: 'code',
    type: SQLColumnType.ENUM,
    enum: SchemeHierTypeValue,
    nullable: false,
  })
  code: string;

  @Column({ name: 'name', type: SQLColumnType.VARCHAR, nullable: false })
  name: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @OneToMany(() => SchemeAttribute, (attri) => attri.hierType, { lazy: true })
  schemeAttribute: SchemeAttribute;
}
