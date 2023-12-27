import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { MediaCategory } from './media-category.entity';

@Entity({ name: TableName.MEDIA_SUB_CATEGORY })
export class MediaSubCategory {
  @PrimaryColumn({
    name: 'code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  code: string;

  @Column({
    name: 'name',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  name: string;

  @PrimaryColumn({
    name: 'category_code',
    type: SQLColumnType.VARCHAR,
    length: 100,
    nullable: false,
  })
  categoryCode: string;

  @ManyToOne(() => MediaCategory, (category) => category.mediaSubCategory, {
    eager: true,
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'code',
      referencedColumnName: 'code',
    },
  ])
  Category: MediaCategory;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
