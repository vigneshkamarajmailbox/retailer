package com.botree.interdbentity.model;

/**
 * Notification Type Master Entity class.
 * @author vinodkumar.a
 */
public class NotificationTypeMasterEntity extends AbstractEntity {

    /** notificationType. */
    private String notificationType;

    /** notificationName. */
    private String notificationName;

    /**
     * @return the notificationType
     */
    public final String getNotificationType() {
        return notificationType;
    }

    /**
     * @param notificationTypeIn the notificationType to set
     */
    public final void setNotificationType(final String notificationTypeIn) {
        notificationType = notificationTypeIn;
    }

    /**
     * @return the notificationName
     */
    public final String getNotificationName() {
        return notificationName;
    }

    /**
     * @param notificationNameIn the notificationName to set
     */
    public final void setNotificationName(final String notificationNameIn) {
        notificationName = notificationNameIn;
    }
}
