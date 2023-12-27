import { Column, Entity, OneToMany, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { AbstractEntity } from './abstract-entity';
import { ProductBatch } from './product-batch.entity';
import { ProductGroupEntity } from './product-group-mapping.entity';
import { ProductMedia } from './product-media.entity';
import { ProductUom } from './product-uom.entity';
import { ProductTags } from './product-tags.entity';

@Entity({ name: TableName.PRODUCT })
export class ProductEntity {
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
    unique: true,
  })
  prodCode: string;

  @Column({
    name: 'prod_hier_level',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  prodHierLevel: string;

  @Column({
    name: 'prod_hier_value',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  prodHierValue: string;

  @Column({
    name: 'prod_hier_path',
    type: SQLColumnType.VARCHAR,
    length: 500,
    nullable: false,
  })
  prodHierPath: string;

  @Column({
    name: 'prod_name',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  prodName: string;

  @Column({
    name: 'prod_short',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  prodShort: string;

  @Column({
    name: 'prod_short_desc',
    type: SQLColumnType.VARCHAR,
    length: 250,
    nullable: false,
  })
  prodShortDesc: string;

  @Column({
    name: 'prod_desc',
    type: SQLColumnType.VARCHAR,
    length: 500,
    nullable: false,
  })
  prodDesc: string;

  @Column({
    name: 'weight_type',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  weightType: string;

  @Column({
    name: 'weight_value',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  weightValue: string;

  @Column({
    name: 'prod_bar_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  prodBarCode: string;

  @Column({
    name: 'prod_hsn_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  prodHsnCode: string;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;

  @OneToMany(() => ProductBatch, (prod) => prod.product, {
    eager: true,
  })
  productBatch?: ProductBatch[];

  @OneToMany(() => ProductUom, (uom) => uom.product, {
    eager: true,
  })
  productUom?: ProductUom[];

  @OneToMany(() => ProductMedia, (prod) => prod.product, {
    eager: true,
  })
  productMedia?: ProductMedia[];

  @OneToMany(() => ProductGroupEntity, (prod) => prod.product)
  productGroupMapping?: ProductGroupEntity[];

  @OneToMany(() => ProductTags, (prod) => prod.product)
  productTags?: ProductTags[];
}
