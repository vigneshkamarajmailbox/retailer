/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

/**
 * Interface used to handle the screen orientation change.
 * Pass the new instance of {@link TaskCallbacks} after screen orientation changed
 */

public interface ScreenOrientation {

    /**
     * New instance of {@link TaskCallbacks} after screen orientation changed
     *
     * @param taskCallbacks new instance of {@link TaskCallbacks}
     */
    void attach(TaskCallbacks taskCallbacks);
}
