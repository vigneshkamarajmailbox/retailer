package com.botree.interdbentity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to return the response for upload service.
 * @author vinodkumar.a
 */
public class UploadStatus {

    /** keyGeneratorStatusList. */
    private List<KeyGeneratorStatusModel> keyGeneratorStatusList = new ArrayList<>();

    /** orderBookingStatusList. */
    private List<OrderBookingStatusModel> orderBookingStatusList = new ArrayList<>();

    /** syncLogEntityStatusList. */
    private List<SyncLogEntity> syncLogEntityStatusList = new ArrayList<>();

    /** feedBackStatusList. */
    private List<FeedBackStatusModel> feedBackStatusList = new ArrayList<>();

    /** locationUpdateStatusList. */
    private List<LocationUpdateStatusModel> locationUpdateStatusList = new ArrayList<>();

    /** favoriteProductStatusList. */
    private List<FavoriteProductStatusModel> favoriteProductStatusList = new ArrayList<>();

    /** message. */
    private String message;

    /**
     * @return the keyGeneratorStatusList
     */
    public final List<KeyGeneratorStatusModel> getKeyGeneratorStatusList() {
        return keyGeneratorStatusList;
    }

    /**
     * @param keyGeneratorStatusListIn the keyGeneratorStatusList to set
     */
    public final void setKeyGeneratorStatusList(
            final List<KeyGeneratorStatusModel> keyGeneratorStatusListIn) {
        keyGeneratorStatusList = keyGeneratorStatusListIn;
    }

    /**
     * @return the orderBookingStatusList
     */
    public final List<OrderBookingStatusModel> getOrderBookingStatusList() {
        return orderBookingStatusList;
    }

    /**
     * @param orderBookingStatusListIn the orderBookingStatusList to set
     */
    public final void setOrderBookingStatusList(
            final List<OrderBookingStatusModel> orderBookingStatusListIn) {
        orderBookingStatusList = orderBookingStatusListIn;
    }

    /**
     * @return the syncLogEntityStatusList
     */
    public final List<SyncLogEntity> getSyncLogEntityStatusList() {
        return syncLogEntityStatusList;
    }

    /**
     * @param syncLogEntityStatusListIn the syncLogEntityStatusList to set
     */
    public final void setSyncLogEntityStatusList(
            final List<SyncLogEntity> syncLogEntityStatusListIn) {
        syncLogEntityStatusList = syncLogEntityStatusListIn;
    }

    /**
     * @return the feedBackStatusList
     */
    public final List<FeedBackStatusModel> getFeedBackStatusList() {
        return feedBackStatusList;
    }

    /**
     * @param feedBackStatusListIn the feedBackStatusList to set
     */
    public final void setFeedBackStatusList(
            final List<FeedBackStatusModel> feedBackStatusListIn) {
        feedBackStatusList = feedBackStatusListIn;
    }

    /**
     * @return the locationUpdateStatusList
     */
    public final List<LocationUpdateStatusModel> getLocationUpdateStatusList() {
        return locationUpdateStatusList;
    }

    /**
     * @param locationUpdateStatusListIn the locationUpdateStatusList to set
     */
    public final void setLocationUpdateStatusList(
            final List<LocationUpdateStatusModel> locationUpdateStatusListIn) {
        locationUpdateStatusList = locationUpdateStatusListIn;
    }

    /**
     * @return the favoriteProductStatusList
     */
    public final List<FavoriteProductStatusModel> getFavoriteProductStatusList() {
        return favoriteProductStatusList;
    }

    /**
     * @param favoriteProductStatusListIn the favoriteProductStatusList to set
     */
    public final void setFavoriteProductStatusList(
            final List<FavoriteProductStatusModel> favoriteProductStatusListIn) {
        favoriteProductStatusList = favoriteProductStatusListIn;
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
        this.message = messageIn;
    }
}
