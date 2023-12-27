import { Module } from '@nestjs/common';
import { ScreenService } from './screen.service';
import { ScreenController } from './screen.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Screen } from './entities/screen.entity';
import { ScreenAccess } from './entities/screen-access.entity';
import { Theme } from 'src/theme/entities/theme.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Screen, ScreenAccess, Theme])],
  controllers: [ScreenController],
  providers: [ScreenService],
})
export class ScreenModule {}
