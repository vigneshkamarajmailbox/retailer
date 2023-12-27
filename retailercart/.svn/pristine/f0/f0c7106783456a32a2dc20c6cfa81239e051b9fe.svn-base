import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductEntity } from '../entities/product.entity';

@Entity({ name: TableName.PRODUCT_GROUP_MAPPING })
export class ProductGroupEntity {
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
    name: 'prod_group_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  prodGroupCode: string;

  @Column({
    name: 'prod_group_name',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
    default: '',
  })
  prodGroupName: string;

  @ManyToOne(() => ProductEntity, (prod) => prod.productGroupMapping, {
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

  // @OneToMany(() => ProductTags, (prod) => prod.product)
  // productTags?: ProductTags[];
}
