/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.support;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.slidingpanelayout.widget.SlidingPaneLayout;

/**
 * Created by shantarao on 27/9/16.
 */

public class MySlidingPanelLayout extends SlidingPaneLayout {

    private float mInitialMotionX;
    private float mEdgeSlop;

    public MySlidingPanelLayout(Context context) {
        this(context, null);
    }

    public MySlidingPanelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlidingPanelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int i = ev.getActionMasked();
        if (i == MotionEvent.ACTION_DOWN) {
            mInitialMotionX = ev.getX();
        } else if (i == MotionEvent.ACTION_MOVE) {
            final float x = ev.getX();
            final float y = ev.getY();
            if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                    Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

                // How do we set super.mIsUnableToDrag = true?

                // send the parent a cancel event
                MotionEvent cancelEvent = MotionEvent.obtain(ev);
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                return super.onInterceptTouchEvent(cancelEvent);
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
