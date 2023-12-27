import { registerDecorator, ValidationOptions } from 'class-validator';
import { IsUserNameExistsValidator } from '../userNameValidation';

export function IsUserNameExists(validationOptions?: ValidationOptions) {
  return function (object: object, propertyName: string) {
    registerDecorator({
      name: 'IsUserNameExists',
      target: object.constructor,
      propertyName: propertyName,
      options: validationOptions,
      constraints: [],
      validator: IsUserNameExistsValidator,
    });
  };
}
