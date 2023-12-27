import { Scheme } from 'src/scheme/entities/scheme.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.SCHEME_SLAP })
export class SchemeSlap {
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
    name: 'slap_no',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  slapNo: string;

  @Column({ name: 'slap_desc', type: SQLColumnType.VARCHAR, nullable: false })
  slapDesc: string;

  @Column({
    name: 'slap_from',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  slapFrom: string;

  @Column({
    name: 'slap_to',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: false,
    default: '0.000000',
  })
  slapTo: string;

  @Column({
    name: 'flat_amount',
    type: SQLColumnType.DECIMAL,
    precision: 22,
    scale: 6,
    nullable: true,
    default: '0.000000',
  })
  flatAmount: string;

  @Column({
    name: 'discount',
    type: SQLColumnType.INT,
    nullable: true,
  })
  discount: string;

  @Column({
    name: 'prod_code',
    type: SQLColumnType.VARCHAR,
    nullable: true,
  })
  prodCode: string;

  @Column({
    name: 'batch_code',
    type: SQLColumnType.VARCHAR,
    nullable: true,
  })
  batchCode: string;

  @Column({ name: 'free_qty', type: SQLColumnType.INT, nullable: true })
  freeQty: number;

  @Column(() => AbstractModel, { prefix: false })
  abstractModel: AbstractModel;

  @ManyToOne(() => Scheme, (scheme) => scheme.slapData, {
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
