import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { S3 } from 'aws-sdk';

@Injectable()
export class AwsService {
  private readonly s3: S3;
  constructor(private readonly config: ConfigService) {
    this.s3 = new S3({
      accessKeyId: this.config.get('AWS_ACCESS_KEY'),
      secretAccessKey: this.config.get('AWS_SECRET_KEY'),
      region: this.config.get('AWS_REGION'),
    });
  }
  async uploadFile(key: string, buffer: Buffer, contentType: string) {
    const params = {
      Bucket: this.config.get('AWS_BUCKET_NAME'),
      Key: key,
      Body: buffer,
      ContentType: contentType,
    };
    try {
      return this.s3.upload(params).promise();
    } catch (error) {
      console.log(error, 'upload');
    }
  }
}
