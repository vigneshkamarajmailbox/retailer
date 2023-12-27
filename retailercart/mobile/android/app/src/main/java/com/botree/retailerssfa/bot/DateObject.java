package com.botree.retailerssfa.bot;

/**
 * Created by shantarao on 26/12/17.
 */

public class DateObject extends ListObject {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getType(int userId) {
        return TYPE_DATE;
    }
}