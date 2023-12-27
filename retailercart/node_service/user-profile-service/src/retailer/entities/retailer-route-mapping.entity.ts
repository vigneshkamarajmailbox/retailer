import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Retailer } from './retailer.entity';

@Entity({ name: TableName.RETAILER_ROUTE_MAPPING })
export class RetailerRouteMapping {
  @Index()
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;
  @Index()
  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @PrimaryColumn({ name: 'route_code', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  routeCode: string;

  @PrimaryColumn({ name: 'route_type', type: SQLColumnType.CHAR, length: 1 })
  routeType: string;

  @PrimaryColumn({ name: 'geo_code', type: SQLColumnType.VARCHAR, length: 50 })
  geoCode: string;

  @Index()
  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @ManyToOne(() => Retailer, (retailer) => retailer.retailerRouteMapping)
  @JoinColumn([
    { name: 'cmp_code', referencedColumnName: 'cmpCode' },
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
    {
      name: 'retailer_code',
      referencedColumnName: 'retailerCode',
    },
  ])
  retailer: Retailer;
}
