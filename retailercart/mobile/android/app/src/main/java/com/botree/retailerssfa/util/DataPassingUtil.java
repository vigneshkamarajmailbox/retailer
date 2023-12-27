package com.botree.retailerssfa.util;

public class DataPassingUtil {

    private static DataPassingUtil instance;

    public static synchronized  DataPassingUtil get() {
        if (instance == null) {
            instance = new DataPassingUtil();
        }
        return instance;
    }

    private int sync = 0;

    private Object largerDataObject;

    public Object getLargerDataObject(int request) {
        return (request == sync) ? largerDataObject : null;
    }

    public int setLargerDataObject(Object largerDataObject) {
        this.largerDataObject = largerDataObject;
        return ++sync;
    }
}
