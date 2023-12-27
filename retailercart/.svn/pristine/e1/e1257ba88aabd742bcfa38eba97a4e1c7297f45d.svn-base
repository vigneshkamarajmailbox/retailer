import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: 'delivery_tracking_t' })
export class DeliveryTracking {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'order_no',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  orderNo: string;

  @Column({
    name: 'order_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  orderDate: Date;

  @Column({
    name: 'order_placed_date',
    type: SQLColumnType.DATE_TIME,
    nullable: false,
  })
  orderPlacedDate: Date;

  @Column({
    name: 'vehicle_allocated_date',
    type: SQLColumnType.DATE_TIME,
    nullable: true,
    default: null,
  })
  vehicleAllocatedDate: Date;

  @Column({
    name: 'delivery_date',
    type: SQLColumnType.DATE_TIME,
    nullable: true,
    default: null,
  })
  deliveryDate: Date;

  @Column({
    name: 'is_order_billed',
    type: SQLColumnType.DATE_TIME,
    nullable: true,
    default: null,
  })
  isOrderBilled: boolean;

  @Column({
    name: 'is_order_placed',
    type: SQLColumnType.DATE_TIME,
    nullable: true,
    default: null,
  })
  isOrderPlaced: boolean;

  isVehicleAllocated: boolean;

  isDelivered: boolean;
}
