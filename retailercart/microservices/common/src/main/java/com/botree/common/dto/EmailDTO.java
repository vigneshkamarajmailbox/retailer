package com.botree.common.dto;

import java.util.List;

/**
 * This class is used to hold data data required send email.
 * @author vinodkumar.a
 */
public class EmailDTO {

    /** subject. */
    private String subject;

    /** message. */
    private String message;

    /** html. */
    private boolean html;

    /** from. */
    private String from;

    /** to. */
    private List<String> to;

    /** cc. */
    private List<String> cc;

    /** attachments. */
    private List<AttachmentDTO> attachments;

    /** inlineAttachment. */
    private AttachmentDTO inlineAttachment;

    /** inlineMsg. */
    private boolean inlineMsg;

    /**
     * @return the subject
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * @param subjectIn the subject to set
     */
    public final void setSubject(final String subjectIn) {
        subject = subjectIn;
    }

    /**
     * @return the message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * @param messageIn the message to set
     */
    public final void setMessage(final String messageIn) {
        message = messageIn;
    }

    /**
     * @return the html
     */
    public final boolean isHtml() {
        return html;
    }

    /**
     * @param htmlIn the html to set
     */
    public final void setHtml(final boolean htmlIn) {
        html = htmlIn;
    }

    /**
     * @return the from
     */
    public final String getFrom() {
        return from;
    }

    /**
     * @param fromIn the from to set
     */
    public final void setFrom(final String fromIn) {
        from = fromIn;
    }

    /**
     * @return the to
     */
    public final List<String> getTo() {
        return to;
    }

    /**
     * @param toIn the to to set
     */
    public final void setTo(final List<String> toIn) {
        to = toIn;
    }

    /**
     * @return the cc
     */
    public final List<String> getCc() {
        return cc;
    }

    /**
     * @param ccIn the cc to set
     */
    public final void setCc(final List<String> ccIn) {
        cc = ccIn;
    }

    /**
     * @return the attachments
     */
    public final List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    /**
     * @param attachmentsIn the attachments to set
     */
    public final void setAttachments(final List<AttachmentDTO> attachmentsIn) {
        attachments = attachmentsIn;
    }

    /**
     * @return the inlineAttachment
     */
    public final AttachmentDTO getInlineAttachment() {
        return inlineAttachment;
    }

    /**
     * @param inlineAttachmentIn the inlineAttachment to set
     */
    public final void setInlineAttachment(final AttachmentDTO inlineAttachmentIn) {
        inlineAttachment = inlineAttachmentIn;
    }

    /**
     * @return the inlineMsg
     */
    public final boolean isInlineMsg() {
        return inlineMsg;
    }

    /**
     * @param inlineMsgIn the inlineMsg to set
     */
    public final void setInlineMsg(final boolean inlineMsgIn) {
        inlineMsg = inlineMsgIn;
    }
}
