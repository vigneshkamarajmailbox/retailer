import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.ROUTE })
export class Route {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  CmpCode: string;

  @PrimaryColumn({ name: 'route_code', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  routeCode: string;

  @PrimaryColumn({ name: 'route_type', type: SQLColumnType.CHAR, length: 1, default: '' })
  routeType: string;

  @PrimaryColumn({ name: 'geo_code', type: SQLColumnType.VARCHAR, length: 50 })
  geoCode: string;

  @Column({ name: 'route_name', type: SQLColumnType.VARCHAR, length: 100 })
  routeName: string;

  @Column({ name: 'distance', type: SQLColumnType.INT, width: 1 })
  distance: number;

  @Column({ name: 'is_van_route', type: SQLColumnType.INT, width: 1 })
  isVanRoute: number;

  @Column({ name: 'population', type: SQLColumnType.INT, width: 11 })
  population: number;

  @Column({ name: 'source', type: SQLColumnType.VARCHAR, length: 13 })
  source: string;

  @Column({ name: 'is_priority_route', type: SQLColumnType.CHAR, length: 1 })
  isPriorityRoute: string;
}
