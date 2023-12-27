import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateOrderHeaderDto } from './dto/create-order-header.dto';
import { OrderDto } from './dto/create-order.dto';
import { OrderHeader } from './entities/order-header.entity';
import { Order } from './entities/order.entity';

export class OrderRepository {
  @InjectRepository(OrderHeader)
  private ordHeaderRepository: Repository<OrderHeader>;

  @InjectRepository(Order)
  private orderRepository: Repository<Order>;

  async createOrderHeader(saveOrderHeader: CreateOrderHeaderDto): Promise<any> {
    const saveOrder = this.ordHeaderRepository.create(saveOrderHeader as any);
    return await this.ordHeaderRepository.save(saveOrder);
  }

  async createOrder(saveOrder: any): Promise<any> {
    // const saveOrderDetails = this.ordHeaderRepository.create(saveOrder as any);
    return await this.orderRepository.save(saveOrder);
  }

  async findOrder(userName: string, limit?: number, offset?: number) {
    const perPage = limit;
    const skip = offset; //page = 1 , perPage * page - perPage;

    return await this.ordHeaderRepository
      .createQueryBuilder('t1')
      .innerJoinAndSelect('t1.orderedProduct', 't2')
      .where('t1.user_name =:userName', { userName: userName })
      .getMany();

    // return await this.ordHeaderRepository.find({
    //   relations: { orderedProduct: true },
    //   select: {
    //     cmpCode: true,
    //     orderNo: true,
    //     orderDate: true,
    //     billDate: true,
    //     deliveryDate: true,
    //     orderStatus: true,
    //     netAmount: true,
    //     grossAmount: true,
    //     taxAmount: true,
    //     orderedProduct: {
    //       prodName: true,
    //       prodMrp: true,
    //       prodImage: true,
    //       prodRetailerPrice: true,
    //     },
    //   },
    //   where: { userName: userName },
    //   take: perPage,
    //   skip,
    // });
  }

  async findOrderById(userName: string, orderNo: string) {
    const data = await this.ordHeaderRepository.find({
      relations: { orderedProduct: true },
      select: {
        cmpCode: true,
        orderNo: true,
        orderDate: true,
        billDate: true,
        deliveryDate: true,
        orderStatus: true,
        netAmount: true,
        grossAmount: true,
        taxAmount: true,
        orderedProduct: {
          prodName: true,
          prodMrp: true,
          prodImage: true,
          prodRetailerPrice: true,
        },
      },
      where: { userName: userName, orderNo: orderNo },
    });

    return data;
  }

  async findReOrders(userName: string) {
    const data = await this.ordHeaderRepository.find({
      relations: { orderedProduct: true },
      select: {
        cmpCode: true,
        orderNo: true,
        orderDate: true,
        billDate: true,
        deliveryDate: true,
        orderStatus: true,
        netAmount: true,
        grossAmount: true,
        taxAmount: true,
        orderedProduct: {
          prodName: true,
          prodMrp: true,
          prodImage: true,
          prodRetailerPrice: true,
        },
      },
      where: { userName: userName, reOrderStatus: 1 },
    });

    return data;
  }

  async findOneOrderById(
    userName: string,
    orderNo: string,
  ): Promise<OrderHeader> {
    return await this.ordHeaderRepository.findOne({
      relations: { orderedProduct: false },
      loadEagerRelations: false,
      select: {
        cmpCode: true,
        orderNo: true,
        orderDate: true,
        billDate: true,
        deliveryDate: true,
        orderStatus: true,
        netAmount: true,
        grossAmount: true,
        taxAmount: true,
        orderedProduct: false,
      },
      where: { userName: userName, orderNo: orderNo },
    });
  }
}
