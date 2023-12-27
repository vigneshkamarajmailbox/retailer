import { ValidatorConstraint, ValidatorConstraintInterface, ValidationArguments } from 'class-validator';
import { Injectable } from '@nestjs/common';
import { UserEntity } from 'src/users/entities/user.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

@ValidatorConstraint({ name: 'IsUserNameExists', async: true })
@Injectable()
export class IsUserNameExistsValidator implements ValidatorConstraintInterface {
  @InjectRepository(UserEntity)
  private user: Repository<UserEntity>;

  async validate(username: string, args: ValidationArguments) {
    const user = await this.user.findOne({
      where: { userName: username },
    });
    if (user) {
      return true;
    } else {
      return false;
    }
  }

  defaultMessage(args: ValidationArguments) {
    return 'Username not Exist';
  }
}
