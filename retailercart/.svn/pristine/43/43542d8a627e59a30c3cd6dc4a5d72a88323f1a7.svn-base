import { AbstractModel } from 'src/shared/entities/abstract.model';
import { SQLColumnType } from 'src/shared/enum/column-type.enum';
import { TableName } from 'src/shared/enum/table-name.enum';
import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: TableName.THEME_MASTER })
export class Theme {
  @PrimaryColumn({
    name: 'cmp_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  cmpCode: string;

  @PrimaryColumn({
    name: 'module_no',
    type: SQLColumnType.INT,
    nullable: false,
  })
  moduleNo: number;

  @PrimaryColumn({
    name: 'screen_no',
    type: SQLColumnType.INT,
    nullable: false,
  })
  screenNo: number;

  @PrimaryColumn({
    name: 'group_code',
    type: SQLColumnType.VARCHAR,
    nullable: false,
  })
  groupCode: string;

  @Column({
    name: 'background_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  backgroundColor: string;

  @Column({
    name: 'tint_background_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintBackgroundColor: string;

  @Column({
    name: 'surface_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  surfaceColor: string;

  @Column({
    name: 'tint_surface_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintSurfaceColor: string;

  @Column({
    name: 'primary_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  primaryColor: string;

  @Column({
    name: 'tint_primary_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintPrimaryColor: string;

  @Column({
    name: 'primary_color_dark',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  primaryColorDark: string;

  @Column({
    name: 'tint_primary_color_dark',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintPrimaryColorDark: string;

  @Column({
    name: 'accent_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  accentColor: string;

  @Column({
    name: 'tint_accent_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintAccentColor: string;

  @Column({
    name: 'accent_color_dark',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  accentColorDark: string;

  @Column({
    name: 'tint_accent_color_dark',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintAccentColorDark: string;

  @Column({
    name: 'error_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  errorColor: string;

  @Column({
    name: 'tint_error_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  tintErrorColor: string;

  @Column({
    name: 'text_primary_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  textPrimaryColor: string;

  @Column({
    name: 'text_primary_color_inverse',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  textPrimaryColorInverse: string;

  @Column({
    name: 'text_secondary_color',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  textSecondaryColor: string;

  @Column({
    name: 'text_secondary_color_inverse',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  textSecondaryColorInverse: string;

  @Column({
    name: 'font_scale',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  fontScale: string;

  @Column({
    name: 'corner_radius',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  cornerRadius: string;

  @Column({
    name: 'background_aware',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  backgroundAware: string;

  @Column({
    name: 'contrast',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  contrast: string;

  @Column({
    name: 'opacity',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  opacity: string;

  @Column({
    name: 'elevation',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  elevation: string;

  @Column({
    name: 'style',
    type: SQLColumnType.VARCHAR,
    length: 75,
    nullable: true,
  })
  style: string;

  @Column(() => AbstractModel, { prefix: false })
  abstractEntity: AbstractModel;
}
