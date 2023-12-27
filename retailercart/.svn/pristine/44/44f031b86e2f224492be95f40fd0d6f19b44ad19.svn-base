import { AppliedScheme } from 'src/applied-scheme/entities/applied-scheme.entity';
import { SchemeAttribute } from 'src/scheme-attributes/entities/scheme-attribute.entity';
import { SchemeDistributor } from 'src/scheme-distributor/entities/scheme-distributor.entity';
import { SchemeHistory } from 'src/scheme-history/entities/scheme-history.entity';
import { SchemeReatailerCategory } from 'src/scheme-reatailer-category/entities/scheme-reatailer-category.entity';
import { SchemeSlap } from 'src/scheme-slap/entities/scheme-slap.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { SchemeTypeValue } from 'src/shared/enum/table-value.enum';
import { Column, Entity, Index, OneToMany, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SCHEME })
export class Scheme {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'sch_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  schemeCode: string;

  @Column({ name: 'sch_name', type: SQLColumnType.VARCHAR, nullable: false })
  schName: string;

  @Column({
    name: 'sch_type',
    type: SQLColumnType.ENUM,
    enum: SchemeTypeValue,
    nullable: false,
  })
  schType: SchemeTypeValue;

  @Column({
    name: 'apply_before_tax',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  applyBeforeTax: string;

  @Column({ name: 'valid_from', type: SQLColumnType.DATE, nullable: false })
  validFrom: Date;

  @Column({ name: 'valid_to', type: SQLColumnType.DATE, nullable: false })
  validTo: Date;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @OneToMany(() => SchemeAttribute, (att) => att.scheme, { eager: true })
  schemeAttribute?: SchemeAttribute[];

  @OneToMany(() => SchemeSlap, (slap) => slap.scheme, { eager: true })
  slapData?: SchemeSlap[];

  @OneToMany(() => AppliedScheme, (apply) => apply.scheme, {
    eager: true,
  })
  appliedScheme?: AppliedScheme[];

  @OneToMany(() => SchemeHistory, (history) => history.scheme, {
    eager: true,
  })
  schemeHistory?: SchemeHistory[];

  @OneToMany(() => SchemeReatailerCategory, (cat) => cat.scheme, {
    eager: true,
  })
  schemeRetailerCategory?: SchemeReatailerCategory[];

  @OneToMany(() => SchemeDistributor, (dist) => dist.scheme, { eager: true })
  distributorData?: SchemeDistributor[];
}
