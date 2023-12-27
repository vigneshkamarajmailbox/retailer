import { Module } from '@nestjs/common';
import { AppliedSchemeModule } from './applied-scheme/applied-scheme.module';
import { SchemeAttributesModule } from './scheme-attributes/scheme-attributes.module';
import { SchemeHierTypeModule } from './scheme-hier-type/scheme-hier-type.module';
import { SchemeHistoryModule } from './scheme-history/scheme-history.module';
import { SchemeReatailerCategoryModule } from './scheme-reatailer-category/scheme-reatailer-category.module';
import { SchemeSlapModule } from './scheme-slap/scheme-slap.module';
import { SchemeModule } from './scheme/scheme.module';
import { SharedModule } from './shared/shared.module';
import { SchemeDistributorModule } from './scheme-distributor/scheme-distributor.module';

@Module({
  imports: [
    SharedModule,
    SchemeSlapModule,
    SchemeModule,
    SchemeHierTypeModule,
    AppliedSchemeModule,
    SchemeHistoryModule,
    SchemeAttributesModule,
    SchemeReatailerCategoryModule,
    SchemeDistributorModule,
  ],
})
export class AppModule {}
