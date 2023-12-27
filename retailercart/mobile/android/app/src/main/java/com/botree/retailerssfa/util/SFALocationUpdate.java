/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.util;

import android.location.Location;

/**
 * Need to implement where need to get the location updates and pass this instance to {@link SFALocation}
 */

public interface SFALocationUpdate {

    /**
     * If new location is detected then trigger here.
     *
     * @param newLocation is new location from {@link SFALocation}
     */
    void newLocation(Location newLocation);

    /**
     * Once connection is succeeded then {@link SFALocation} trigger here.
     */
    void onConnectionSuccess();

    /**
     * If connection error then {@link SFALocation} trigger here.
     */
    void onConnectionError();


}
