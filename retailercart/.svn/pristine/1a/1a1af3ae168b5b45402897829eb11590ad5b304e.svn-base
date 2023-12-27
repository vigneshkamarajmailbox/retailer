import { registerDecorator, ValidationOptions } from 'class-validator';
import { IsCompanyExistsValidator } from '../companyValidation';

export function IsCompanyExists(validationOptions?: ValidationOptions) {
  return function (object: object, propertyName: string) {
    registerDecorator({
      name: 'IsCompanyExists',
      target: object.constructor,
      propertyName: propertyName,
      options: validationOptions,
      constraints: [],
      validator: IsCompanyExistsValidator,
    });
  };
}
