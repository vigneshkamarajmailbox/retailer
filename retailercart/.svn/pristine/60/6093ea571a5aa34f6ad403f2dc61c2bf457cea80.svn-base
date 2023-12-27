import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { AbstractEntity } from './abstract-entity';
import { ProductEntity } from './product.entity';

@Entity({ name: TableName.PRODUCT_UOM })
export class ProductUom {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'prod_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  prodCode: string;

  @Column({
    name: 'prod_uom_code',
    type: SQLColumnType.VARCHAR,
    nullable: true,
  })
  prodUomCode: any;

  @Column({
    name: 'prod_conversion_factor',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  prodConversionFactor: string;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;

  @ManyToOne(() => ProductEntity, (prod) => prod.productUom, {
    cascade: true,
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
    {
      name: 'prod_code',
      referencedColumnName: 'prodCode',
    },
  ])
  product?: ProductEntity;
}
