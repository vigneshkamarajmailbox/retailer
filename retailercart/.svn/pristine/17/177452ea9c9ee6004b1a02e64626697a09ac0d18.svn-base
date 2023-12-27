import { MediaSubCategory } from 'src/media-category/entities/media-sub-category.entity';
import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, OneToMany, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.MEDIA_CATEGORY })
export class MediaCategory {
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

  @OneToMany(() => MediaSubCategory, (medSubCat) => medSubCat.categoryCode, {
    lazy: true,
  })
  mediaSubCategory?: MediaSubCategory[];

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
