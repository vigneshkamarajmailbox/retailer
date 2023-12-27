import { Column, Entity, PrimaryColumn, OneToMany } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductHierarchyValue } from './product-hierarchy-value.entity';

@Entity({ name: TableName.PRODUCT_CROSS_MAPPING })
export class ProductHierCrossMapping {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  cmpCode: string;
  @PrimaryColumn({
    name: 'level_code',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  levelCode: string;
  @Column({
    name: 'common_name',
    type: SQLColumnType.VARCHAR,
    length: 50,
    nullable: false,
  })
  commonName: string;

  @OneToMany(() => ProductHierarchyValue, (prod) => prod.productHierLevel)
  productHierValue: ProductHierarchyValue[];
}
