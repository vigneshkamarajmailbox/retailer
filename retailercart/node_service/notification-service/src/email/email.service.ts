import { Injectable } from '@nestjs/common';
import * as nodemailer from 'nodemailer';
import { ConfigService } from '@nestjs/config';
import { TemplateEmail } from '../common/helper/email-template';

@Injectable()
export class EmailService {
  private transporter;

  constructor(private readonly config: ConfigService) {
    this.transporter = nodemailer.createTransport({
      host: this.config.get('EMAIL_HOST'),
      port: this.config.get('EMAIL_PORT'),
      // secure: true, // true for 465, false for other ports
      auth: {
        user: this.config.get('EMAIL_USERNAME'),
        pass: this.config.get('EMAIL_PASSWORD'),
      },
    });
  }

  async sendEmail(data): Promise<void> {
    const mailOptions = {
      from: this.config.get('EMAIL_FROM'),
      to: data.to,
      subject: data.subject,
      text: 'Hello Botree',
      html: await TemplateEmail(data.templateValue, data),
      // this.config.get('EMAIL_TEMPLATE'),
    };

    try {
      const info = await this.transporter.sendMail(mailOptions);
      console.log(`Email sent: ${info.response}`);
      console.log('Message sent: %s', info.messageId);
      // Preview only available when sending through an Ethereal account
      console.log('Preview URL: %s', nodemailer.getTestMessageUrl(info));
    } catch (error) {
      console.error(`Error sending email: ${error}`);
    }
  }
}
