import { Column, Entity, PrimaryColumn, ManyToOne, JoinColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductEntity } from './product.entity';

@Entity({ name: TableName.PRODUCT_TAGS })
export class ProductTags {
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
    length: 150,
    nullable: false,
    unique: true,
  })
  prodCode: string;

  @Column({
    name: 'tag_code',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  tagCode: string;

  @Column({
    name: 'tag_name',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  tagName: string;

  @ManyToOne(() => ProductEntity, (prod) => prod.productTags, {
    eager: true,
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
