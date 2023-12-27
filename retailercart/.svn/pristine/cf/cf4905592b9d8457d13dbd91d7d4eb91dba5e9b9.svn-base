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

package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOB_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOB_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD;

public class LobModel implements Parcelable {

    public static final Creator<LobModel> CREATOR = new Creator<LobModel>() {
        @Override
        public LobModel createFromParcel(Parcel in) {
            return new LobModel(in);
        }

        @Override
        public LobModel[] newArray(int size) {
            return new LobModel[size];
        }
    };

    @SerializedName(COLUMN_CMP_CODE)
    private String companyCode;
    @SerializedName(COLUMN_DISTR_CODE)
    private String distributorCode;
    @SerializedName(COLUMN_LOB_CODE)
    private String lobCode;
    @SerializedName(COLUMN_LOB_NAME)
    private String lobName;
    @SerializedName(TAG_UPLOAD)
    private String upload;
    private boolean isSelected;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    public static Creator<LobModel> getCREATOR() {
        return CREATOR;
    }

    protected LobModel(Parcel in) {
        companyCode = in.readString();
        distributorCode = in.readString();
        lobCode = in.readString();
        lobName = in.readString();
    }

    public LobModel() {

    }

    public LobModel(String companyCode, String distributorCode, String lobCode) {
        this.companyCode = companyCode;
        this.distributorCode = distributorCode;
        this.lobCode = lobCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(companyCode);
        parcel.writeString(distributorCode);
        parcel.writeString(lobCode);
        parcel.writeString(lobName);
    }

}
