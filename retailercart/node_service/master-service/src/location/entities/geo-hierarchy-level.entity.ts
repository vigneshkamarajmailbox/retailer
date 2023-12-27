import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, Index, OneToMany, PrimaryColumn } from 'typeorm';
import { GeoHierarchyValue } from './geo-hierarchy-value.entity';

@Entity({ name: TableName.GEO_HIERARCHY_LEVEL })
export class GeoHierarchyLevel {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @Index()
  @PrimaryColumn({
    name: 'level_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  levelCode: string;

  @Column({ name: 'level_name', type: SQLColumnType.VARCHAR, nullable: false })
  levelName: string;

  @OneToMany(() => GeoHierarchyValue, (geoHirVal) => geoHirVal.geoHierLevel, {
    lazy: true,
  })
  geoHierValue?: GeoHierarchyValue[];
}
