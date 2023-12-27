import { Column, Entity, PrimaryColumn, ManyToOne, JoinColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductHierCrossMapping } from './product-hier-cross-mapping.entity';

@Entity({ name: TableName.PRODUCT_HIERARCHY_VALUE })
export class ProductHierarchyValue {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'value_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  valueCode: string;

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

  @Column({
    name: 'value_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  valueName: string;

  @Column({
    name: 'image_url',
    type: SQLColumnType.TEXT,
    nullable: true,
  })
  imageUrl: string;

  @Column({
    name: 'level_1_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level1Code: string;

  @Column({
    name: 'level_1_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level1Value: string;

  @Column({
    name: 'level_2_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level2Code: string;

  @Column({
    name: 'level_2_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level2Value: string;

  @Column({
    name: 'level_3_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level3Code: string;

  @Column({
    name: 'level_3_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level3Value: string;

  @Column({
    name: 'level_4_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level4Code: string;

  @Column({
    name: 'level_4_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level4Value: string;

  @Column({
    name: 'level_5_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level5Code: string;

  @Column({
    name: 'level_5_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level5Value: string;

  @Column({
    name: 'level_6_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level6Code: string;

  @Column({
    name: 'level_6_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level6Value: string;

  @Column({
    name: 'level_7_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level7Code: string;

  @Column({
    name: 'level_7_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level7Value: string;

  @Column({
    name: 'level_8_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level8Code: string;

  @Column({
    name: 'level_8_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level8Value: string;

  @Column({
    name: 'level_9_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level9Code: string;

  @Column({
    name: 'level_9_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level9Value: string;

  @Column({
    name: 'level_10_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level10Code: string;

  @Column({
    name: 'level_10_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: true,
  })
  level10Value: string;

  @Column({
    name: 'mod_user_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  modUserCode: string;

  @Column({
    name: 'mod_dt',
    type: SQLColumnType.DATE_TIME,
    default: () => 'now()',
  })
  modDt: Date;

  @ManyToOne(() => ProductHierCrossMapping, (prod) => prod.productHierValue, {
    eager: true,
    cascade: true,
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'level_code',
      referencedColumnName: 'levelCode',
    },
  ])
  productHierLevel: ProductHierCrossMapping;
}
