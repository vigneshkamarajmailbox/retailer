import { Module } from '@nestjs/common';
import { LocationService } from './location.service';
import { LocationController } from './location.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { GeoHierarchyLevel } from './entities/geo-hierarchy-level.entity';
import { GeoHierarchyValue } from './entities/geo-hierarchy-value.entity';

@Module({
  imports: [TypeOrmModule.forFeature([GeoHierarchyLevel,GeoHierarchyValue])],
  controllers: [LocationController],
  providers: [LocationService]
})
export class LocationModule {}
