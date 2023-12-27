import { PartialType } from '@nestjs/mapped-types';
import { CreateOrderHeaderDto } from './create-order-header.dto';

export class UpdateOrderDto extends PartialType(CreateOrderHeaderDto) {}
