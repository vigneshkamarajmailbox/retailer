package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinothbaskaran on 20/11/17.
 */

public class QuickViewVo implements Parcelable {
    @Override
    public String toString() {
        return "QuickViewVo{" +
                "name='" + name + '\'' +
                ", icons=" + icons +
                ", isActiveIcon=" + isActiveIcon +
                '}';
    }

    private String name="";
    private Integer icons;
    private Integer color;
    private boolean isActiveIcon;
    private Integer sequence;
    private boolean seqVisible =false;


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    public QuickViewVo(Parcel in) {
        name = in.readString();
        icons = in.readInt();
    }

    public QuickViewVo(String name, int icon) {
        this.name = name;
        this.icons = icon;
    }

    public QuickViewVo(String name, int icon,boolean isActiveIcon,Integer sequence) {
        this.name = name;
        this.icons = icon;
        this.isActiveIcon=isActiveIcon;
        this.sequence=sequence;
    }


    public QuickViewVo(String name, int icon,boolean isActiveIcon) {
        this.name = name;
        this.icons = icon;
        this.isActiveIcon=isActiveIcon;
    }

    public QuickViewVo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIcons() {
        return icons;
    }

    public void setIcons(Integer icons) {
        this.icons = icons;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(icons);
    }

    public static final Creator<QuickViewVo> CREATOR = new Creator<QuickViewVo>() {
        @Override
        public QuickViewVo createFromParcel(Parcel in) {
            return new QuickViewVo(in);
        }

        @Override
        public QuickViewVo[] newArray(int size) {
            return new QuickViewVo[size];
        }
    };

    public boolean isActiveIcon() {
        return isActiveIcon;
    }

    public void setActiveIcon(boolean activeIcon) {
        isActiveIcon = activeIcon;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public boolean isSeqVisible() {
        return seqVisible;
    }

    public void setSeqVisible(boolean seqVisible) {
        this.seqVisible = seqVisible;
    }
}
