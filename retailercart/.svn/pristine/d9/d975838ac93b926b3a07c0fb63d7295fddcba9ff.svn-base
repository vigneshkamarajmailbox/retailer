import {
  Column,
  Entity,
  PrimaryColumn,
  Index,
  ManyToOne,
  JoinColumn,
} from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { ProductEntity } from './product.entity';

@Entity({ name: TableName.PRODUCT_MEDIA })
export class ProductMedia {
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
    name: 'media_name',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: true,
  })
  mediaName: string;

  @Column({
    name: 'media_type',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: false,
  })
  mediaType: string;

  @Column({
    name: 'media_desc',
    type: SQLColumnType.VARCHAR,
    length: 150,
    nullable: true,
  })
  mediaDesc: string;

  @Column({ name: 'media_url', type: 'json', nullable: true })
  mediaUrl: any;

  @ManyToOne(() => ProductEntity, (prod) => prod.productMedia, {
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
  product: ProductEntity;
}
