package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewOutletImageList implements Parcelable {


    /** cmpCode. */
    private String cmpCode;

    /** distrCode. */
    private String distrCode;

    /** customerCode. */
    private String customerCode;

    /** imageId. */
    private String imageId;

    /** image. */
    private String image;

    public NewOutletImageList() {
        //ignore
    }
    private NewOutletImageList(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        customerCode = in.readString();
        imageId = in.readString();
        image = in.readString();
    }

    public static final Creator<NewOutletImageList> CREATOR = new Creator<NewOutletImageList>() {
        @Override
        public NewOutletImageList createFromParcel(Parcel in) {
            return new NewOutletImageList(in);
        }

        @Override
        public NewOutletImageList[] newArray(int size) {
            return new NewOutletImageList[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(customerCode);
        dest.writeString(imageId);
        dest.writeString(image);
    }
}
