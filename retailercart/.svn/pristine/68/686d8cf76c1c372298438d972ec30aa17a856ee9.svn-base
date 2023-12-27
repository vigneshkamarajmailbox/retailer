package com.botree.common.service;

import com.botree.common.constants.ReportConstants;
import com.botree.common.dto.EmailDTO;
import com.botree.common.util.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.nio.file.Files;

/**
 * Email Service to send the extracted report in excel format through mail.
 * @author vinodkumar.a
 */
@Service
public class EmailService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    /** MAX_TO_SIZE. */
    private static final int MAX_TO_SIZE = 20;

    /** mailSender. */
    private final JavaMailSender mailSender;

    /**
     * Constructor Method.
     * @param mailSenderIn mailSenderIn
     */
    public EmailService(final JavaMailSender mailSenderIn) {
        this.mailSender = mailSenderIn;
    }

    /**
     * This method is used send mail.
     * @param emailDTO Holds mail data
     */
    public final void sendMail(final EmailDTO emailDTO) {
        try {
            // Prepare message using a Spring helper
            final var mimeMessage = mailSender.createMimeMessage();
            final var message = new MimeMessageHelper(mimeMessage, true, ReportConstants.UTF_8);
            message.setSubject(emailDTO.getSubject());
            if (emailDTO.getFrom() == null) {
                message.setFrom(new InternetAddress("noreply.ssfa@botree.co.in", "SSFA Team"));
            } else {
                message.setFrom(new InternetAddress(emailDTO.getFrom()));
            }
            if (emailDTO.getCc() != null) {
                message.setCc(Function.getInternetAddresses(emailDTO.getCc()));
            }
            message.setText(emailDTO.getMessage(), emailDTO.isHtml());

            if (emailDTO.isInlineMsg() && emailDTO.getInlineAttachment() != null) {
                var img = emailDTO.getInlineAttachment().getFile();
                InputStreamSource imageSource =
                        new ByteArrayResource(Files.readAllBytes(img.toPath()));
                message.addInline(img.getName(), imageSource, Files.probeContentType(img.toPath()));
            }
            if (emailDTO.getAttachments() != null) {
                for (var attachment : emailDTO.getAttachments()) {
                    message.addAttachment(attachment.getName(), attachment.getFile());
                }
            }

            // Split and send if recipient size is greater than 20
            for (var i = 0; i < emailDTO.getTo().size(); i += MAX_TO_SIZE) {
                var temp = emailDTO.getTo().subList(i,
                        Math.min(emailDTO.getTo().size(), i + MAX_TO_SIZE));
                message.setTo(Function.getInternetAddresses(temp));
                // Send Mail
                mailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            LOG.error("exception while sending mail", e);
        }
    }
}
