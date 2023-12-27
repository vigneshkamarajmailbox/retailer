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

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    /**
     * the last x position
     */
    private float lastX;

    /**
     * if the first swipe was from left to right (->), dont listen to swipes from the right
     */
    private boolean slidingLeft;

    /**
     * if the first swipe was from right to left (<-), dont listen to swipes from the left
     */
    private boolean slidingRight;

    public CustomViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(final Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                // Disallow parent ViewPager to intercept touch events.
                this.getParent().requestDisallowInterceptTouchEvent(true);

                // save the current x position
                this.lastX = ev.getX();

                break;

            case MotionEvent.ACTION_UP:
                // Allow parent ViewPager to intercept touch events.
                this.getParent().requestDisallowInterceptTouchEvent(false);

                // save the current x position
                this.lastX = ev.getX();

                // reset swipe actions
                this.slidingLeft = false;
                this.slidingRight = false;

                break;

            case MotionEvent.ACTION_MOVE:
                /*
                 * if this is the first item, scrolling from left to
                 * right should navigate in the surrounding ViewPager
                 */
                if (this.getCurrentItem() == 0) {
                    // swiping from left to right (->)?
                    if (this.lastX <= ev.getX() && !this.slidingRight) {
                        // make the parent touch interception active -> parent pager can swipe
                        this.getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        /*
                         * if the first swipe was from right to left, dont listen to swipes
                         * from left to right. this fixes glitches where the user first swipes
                         * right, then left and the scrolling state gets reset
                         */
                        this.slidingRight = true;

                        // save the current x position
                        this.lastX = ev.getX();
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else
                    /*
                     * if this is the last item, scrolling from right to
                     * left should navigate in the surrounding ViewPager
                     */
                    if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
                        // swiping from right to left (<-)?
                        if (this.lastX >= ev.getX() && !this.slidingLeft) {
                            // make the parent touch interception active -> parent pager can swipe
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            /*
                             * if the first swipe was from left to right, dont listen to swipes
                             * from right to left. this fixes glitches where the user first swipes
                             * left, then right and the scrolling state gets reset
                             */
                            this.slidingLeft = true;

                            // save the current x position
                            this.lastX = ev.getX();
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }

                break;
            default:
                break;
        }

        super.onTouchEvent(ev);
        return true;
    }

}
