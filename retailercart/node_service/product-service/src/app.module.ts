import { Module } from '@nestjs/common';
import { HomeTModule } from './home-t/home-t.module';
import { SharedModule } from './shared/shared.module';
import { ProductModule } from './product/product-t.module';

@Module({
  imports: [SharedModule, HomeTModule, ProductModule],
})
export class AppModule {}
