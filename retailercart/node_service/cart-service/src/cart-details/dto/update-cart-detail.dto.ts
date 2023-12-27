import { PartialType } from '@nestjs/swagger';
import { CreateCartHeader } from './create-cart-detail.dto';

export class UpdateCartHeaderDto extends PartialType(CreateCartHeader) {}
