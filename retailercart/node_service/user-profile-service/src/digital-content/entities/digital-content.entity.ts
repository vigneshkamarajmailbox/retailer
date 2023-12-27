import { Company } from 'src/shared/entities/company.entity';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';
import { AbstractEntity } from 'src/shared/entities/base.model';

@Entity({ name: TableName.DIGITAL_CONTENT })
export class DigitalContent {
  @Index()
  @PrimaryColumn({ name: 'brochure_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  brochure_code: string;

  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @Column({ name: 'title', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  title: string;

  @Column({ name: 'description', type: SQLColumnType.VARCHAR, length: 75, nullable: true })
  description: string;

  @Column({ name: 'media_type', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  mediaType: string;

  @Column({ name: 'media_name', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  mediaName: string;

  @Column({ name: 'is_download', type: SQLColumnType.BOOLEAN, default: true })
  isDownload: boolean;

  @ManyToOne(() => Company, (cmp) => cmp.cmpCode, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'cmp_code',
      referencedColumnName: 'cmpCode',
    },
  ])
  company: Company;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
