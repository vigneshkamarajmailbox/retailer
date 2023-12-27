import { BadRequestException, ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { NestFactory } from '@nestjs/core';
import { DocumentBuilder, SwaggerCustomOptions, SwaggerDocumentOptions, SwaggerModule } from '@nestjs/swagger';
import * as bodyParser from 'body-parser';
import { AppModule } from './app.module';
import { useContainer } from 'class-validator';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.enableCors();
  app.setGlobalPrefix('rsfa/api/v1/user');
  app.useGlobalPipes(
    new ValidationPipe({
      exceptionFactory: (errors) => {
        const result = errors.map((error) => ({
          property: error.property,
          message: error.constraints[Object.keys(error.constraints)[0]],
        }));
        return new BadRequestException(result);
      },
      stopAtFirstError: true,
    }),
  );

  app.use(bodyParser.json({ limit: '1024mb' }));
  app.use(bodyParser.urlencoded({ limit: '1024mb', extended: true }));

  useContainer(app.select(AppModule), { fallbackOnErrors: true });

  // swagger
  const swaggerConfig = new DocumentBuilder()
    .setTitle('Retailer App')
    .setDescription('The Retailer App API description')
    .setVersion('1.0')
    .addTag('Retailer App')
    .addBearerAuth(
      {
        // I was also testing it without prefix 'Bearer ' before the JWT
        description: `[just text field] Please enter token in following format: Bearer <JWT>`,
        name: 'Authorization',
        bearerFormat: 'Bearer', // I`ve tested not to use this field, but the result was the same
        scheme: 'Bearer',
        type: 'http', // I`ve attempted type: 'apiKey' too
        in: 'Header',
      },
      'access-token', // This name here is important for matching up with @ApiBearerAuth() in your controller!
    )
    .build();

  const documentOptions: SwaggerDocumentOptions = {
    deepScanRoutes: true,
  };

  const options: SwaggerCustomOptions = {
    customSiteTitle: 'Retailer App API Docs',
  };

  const document = SwaggerModule.createDocument(app, swaggerConfig, documentOptions);
  SwaggerModule.setup('rsfa/api/v1/user', app, document, options);

  // config
  const config: ConfigService = app.get(ConfigService);
  await app.listen(Number(config.get<number>('APP_PORT')));
}
bootstrap();
