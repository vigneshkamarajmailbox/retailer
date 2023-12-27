import { AbstractEntity } from 'src/shared/entities/base.model';
import { UserEntity } from 'src/users/entities/user.entity';
import { Column, Entity, Index, JoinColumn, ManyToOne, PrimaryColumn, PrimaryGeneratedColumn } from 'typeorm';
import { SQLColumnType } from '../../shared/enum/column-type.enum';
import { TableName } from '../../shared/enum/table-name.enum';

@Entity({ name: TableName.FEEDBACK })
export class Feedback {
  @Index()
  @PrimaryGeneratedColumn({ name: 'id' })
  id: number;

  @PrimaryColumn({ name: 'cmp_code', type: SQLColumnType.VARCHAR, length: 50, nullable: false })
  cmpCode: string;

  @PrimaryColumn({ name: 'user_name', type: SQLColumnType.VARCHAR, length: 100, nullable: false })
  userName: string;

  @Column({ name: 'title', type: SQLColumnType.VARCHAR, length: 50, nullable: true })
  title: string;

  @Column({ name: 'comments', type: SQLColumnType.VARCHAR, length: 250, nullable: true })
  comments: string;

  @Column({ name: 'image_path', type: SQLColumnType.VARCHAR, length: 250, nullable: true })
  imagePath: string;

  @ManyToOne(() => UserEntity, (user) => user.userName, {
    deferrable: 'INITIALLY IMMEDIATE',
    cascade: ['insert', 'update'],
  })
  @JoinColumn([
    {
      name: 'user_name',
      referencedColumnName: 'userName',
    },
  ])
  user: UserEntity;

  @Column(() => AbstractEntity, { prefix: false })
  abstractEntity: AbstractEntity;
}
