import { AbstractEntity } from 'src/shared/entities/base.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { RetailerClass } from './retailer-class.entity';
import { Retailer } from './retailer.entity';

@Entity({ name: TableName.RETAILER_CLASS_MAPPING, synchronize: false })
export class RetailerClassMapping {
  @Index()
  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'channel_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  channelCode: string;

  @PrimaryColumn({ name: 'group_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  groupCode: string;

  @PrimaryColumn({ name: 'class_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  classCode: string;
  @Index()
  @PrimaryColumn({ name: 'retailer_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  retailerCode: string;

  @Index()
  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;

  @ManyToOne(() => Retailer, (retailer) => retailer.retailerClass, {
    cascade: false,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
      foreignKeyConstraintName: 'fk_retailer_class_mapping_t_1',
    },
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
      foreignKeyConstraintName: 'fk_retailer_class_mapping_t_1',
    },
    {
      name: 'retailer_code',
      referencedColumnName: 'retailerCode',
      foreignKeyConstraintName: 'fk_retailer_class_mapping_t_1',
    },
  ])
  retailer: Retailer;

  @ManyToOne(() => RetailerClass, (retailer) => retailer.retailer, {
    cascade: false,
    onDelete: 'NO ACTION',
    onUpdate: 'NO ACTION',
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'channel_code',
      referencedColumnName: 'channelCode',
    },
    {
      name: 'group_code',
      referencedColumnName: 'groupCode',
    },
    {
      name: 'class_code',
      referencedColumnName: 'classCode',
    },
  ])
  retailerClass: RetailerClass;
}
