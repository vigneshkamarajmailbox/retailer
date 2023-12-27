import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, OneToMany, PrimaryColumn } from 'typeorm';
import { RetailerClassMapping } from './retailer-class-mapping.entity';

@Entity({ name: TableName.RETAILER_CLASS })
export class RetailerClass {
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'channel_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  channelCode: string;

  @Column({ name: 'channel_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  channelName: string;

  @PrimaryColumn({ name: 'group_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  groupCode: string;

  @Column({ name: 'group_name', type: SQLColumnType.VARCHAR, length: 100 })
  groupName: string;

  @PrimaryColumn({ name: 'class_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  classCode: string;

  @Column({ name: 'class_name', type: SQLColumnType.VARCHAR, length: 100 })
  className: string;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;

  @OneToMany(() => RetailerClassMapping, (retailerClassMapping) => retailerClassMapping.retailerClass, {
    eager: true,
  })
  retailer: RetailerClassMapping[];
}
