import { Module } from '@nestjs/common';
import { ConfigurationModule } from './configuration/configuration.module';
import { KycTypeModule } from './kyc-type/kyc-type.module';
import { LanguageModule } from './language/language.module';
import { LobMasterModule } from './lob-master/lob-master.module';
import { LocationModule } from './location/location.module';
import { MediaCategoryModule } from './media-category/media-category.module';
import { RetailerCategoryModule } from './retailer-category/retailer-category.module';
import { ScreenModule } from './screen/screen.module';
import { SharedModule } from './shared/shared.module';
import { ThemeModule } from './theme/theme.module';
import { UomMasterModule } from './uom-master/uom-master.module';

@Module({
  imports: [
    SharedModule,
    KycTypeModule,
    MediaCategoryModule,
    LanguageModule,
    ScreenModule,
    LobMasterModule,
    RetailerCategoryModule,
    LocationModule,
    UomMasterModule,
    ThemeModule,
    ConfigurationModule,
  ],
})
export class AppModule {}
