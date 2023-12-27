import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { GeoHierarchyLevel } from './geo-hierarchy-level.entity';

@Entity({ name: TableName.GEO_HIERARCHY_VALUE })
export class GeoHierarchyValue {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'value_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  valueCode: string;

  @Column({ name: 'value_name', type: SQLColumnType.VARCHAR, nullable: false })
  valueName: string;

  @PrimaryColumn({
    name: 'level_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  levelCode: string;

  @Column({ name: 'level_name', type: SQLColumnType.VARCHAR, nullable: false })
  levelName: string;

  @Column({
    name: 'level_1_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level1Code: string;

  @Column({
    name: 'level_1_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level1Value: string;

  @Column({
    name: 'level_2_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level2Code: string;

  @Column({
    name: 'level_2_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level2Value: string;

  @Column({
    name: 'level_3_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level3Code: string;

  @Column({
    name: 'level_3_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level3NValue: string;

  @Column({
    name: 'level_4_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level4Code: string;

  @Column({
    name: 'level_4_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level4Value: string;

  @Column({
    name: 'level_5_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level5Code: string;

  @Column({
    name: 'level_5_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level5Value: string;

  @Column({
    name: 'level_6_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level6Code: string;

  @Column({
    name: 'level_6_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level6Value: string;

  @Column({
    name: 'level_7_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level7Code: string;

  @Column({
    name: 'level_7_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level7Value: string;

  @Column({
    name: 'level_8_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level8Code: string;

  @Column({
    name: 'level_8_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level8Value: string;

  @Column({
    name: 'level_9_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level9Code: string;

  @Column({
    name: 'level_9_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level9Value: string;

  @Column({
    name: 'level_10_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level10Code: string;

  @Column({
    name: 'level_10_value',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  level10Value: string;

  @ManyToOne(() => GeoHierarchyLevel, (geoHirlev) => geoHirlev.levelCode, {
    eager: true,
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'level_code',
      referencedColumnName: 'levelCode',
    },
  ])
  geoHierLevel: GeoHierarchyLevel;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
