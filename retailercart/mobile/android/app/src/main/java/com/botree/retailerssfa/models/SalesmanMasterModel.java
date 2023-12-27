package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;

public class SalesmanMasterModel implements Parcelable {

    public static final Creator<SalesmanMasterModel> CREATOR = new Creator<SalesmanMasterModel>() {
        @Override
        public SalesmanMasterModel createFromParcel(Parcel in) {
            return new SalesmanMasterModel(in);
        }

        @Override
        public SalesmanMasterModel[] newArray(int size) {
            return new SalesmanMasterModel[size];
        }
    };
    @SerializedName("cmpCode")
    private String cmpCode = "";

    @SerializedName("distrCode")
    private String distrCode = "";

    @SerializedName("salesmanCode")
    private String salesmanCode = "";

    @SerializedName("salesmanName")
    private String salesmanName = "";

    private String address = "";

    @SerializedName("mobileNo")
    private String mobileNo = "";

    @SerializedName("emailID")
    private String emailId = "";

    private String dailyAllowance = "0";

    @SerializedName("salary")
    private int monthlySalary = 0;

    private List lobList = new ArrayList();

    @SerializedName("bankName")
    private String bankName = "";

    @SerializedName("accountNumber")
    private String accNo = "";

    private String branchName = "";

    @SerializedName("ifscCode")
    private String ifscCode = "";



    private String adharNo = "";

    @SerializedName("type")
    private String selectedKYC = "";

    @SerializedName("image")
    private String kycImage = "";

    @SerializedName(TAG_MOD_DT)
    private String modDt;

    @SerializedName("cancelledChequeImage")
    private String cancelledChequeImage = "";

    @SerializedName("salesmanImage")
    private String salesmanProfileImage = "";

    @SerializedName("status")
    private String salesmanStatus = "Y";

    @SerializedName("ssfaEnabled")
    private String ssfaEnabled = "N";

    private String upload;

    @SerializedName("lobCode")
    private String lobCode;

    private List<SalesmanMasterCheckBoxModel> routeMappedList = new ArrayList<>();

    private List<SalesmanMasterCheckBoxModel> lobMappedList = new ArrayList<>();

    private boolean isRouteCheckBoxEnabled = false;

    @SerializedName("routeCode")
    private String routeCode;

    HashMap<String, String> kycImageMap = new HashMap<>();

    public SalesmanMasterModel() {
    }

    protected SalesmanMasterModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        salesmanName = in.readString();
        address = in.readString();
        mobileNo = in.readString();
        emailId = in.readString();
        dailyAllowance = in.readString();
        monthlySalary = in.readInt();
        bankName = in.readString();
        accNo = in.readString();
        branchName = in.readString();
        ifscCode = in.readString();
        adharNo = in.readString();
        selectedKYC = in.readString();
        kycImage = in.readString();
        cancelledChequeImage = in.readString();
        routeMappedList = in.readArrayList(SalesmanMasterCheckBoxModel.class.getClassLoader());
        lobMappedList = in.readArrayList(SalesmanMasterCheckBoxModel.class.getClassLoader());

        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            kycImageMap.put(key,value);
        }

    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(salesmanCode);
        dest.writeString(salesmanName);
        dest.writeString(address);
        dest.writeString(mobileNo);
        dest.writeString(emailId);
        dest.writeString(dailyAllowance);
        dest.writeInt(monthlySalary);
        dest.writeString(bankName);
        dest.writeString(accNo);
        dest.writeString(branchName);
        dest.writeString(ifscCode);
        dest.writeString(adharNo);
        dest.writeString(selectedKYC);
        dest.writeString(kycImage);
        dest.writeString(cancelledChequeImage);
        dest.writeList(routeMappedList);
        dest.writeList(lobMappedList);

        dest.writeInt(kycImageMap.size());
        for (Map.Entry<String, String> entry: kycImageMap.entrySet()){

            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

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

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDailyAllowance() {
        return dailyAllowance;
    }

    public void setDailyAllowance(String dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public List getLobList() {
        return lobList;
    }

    public void setLobList(List lobList) {
        this.lobList = lobList;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAdharNo() {
        return adharNo;
    }

    public void setAdharNo(String adharNo) {
        this.adharNo = adharNo;
    }

    public String getSelectedKYC() {
        return selectedKYC;
    }

    public void setSelectedKYC(String selectedKYC) {
        this.selectedKYC = selectedKYC;
    }

    public String getKycImage() {
        return kycImage;
    }

    public void setKycImage(String kycImage) {
        this.kycImage = kycImage;
    }

    public String getCancelledChequeImage() {
        return cancelledChequeImage;
    }

    public void setCancelledChequeImage(String cancelledChequeImage) {
        this.cancelledChequeImage = cancelledChequeImage;
    }

    public List<SalesmanMasterCheckBoxModel> getRouteMappedList() {
        return routeMappedList;
    }

    public void setRouteMappedList(List<SalesmanMasterCheckBoxModel> routeMappedList) {
        this.routeMappedList = routeMappedList;
    }

    public List<SalesmanMasterCheckBoxModel> getLobMappedList() {
        return lobMappedList;
    }

    public void setLobMappedList(List<SalesmanMasterCheckBoxModel> lobMappedList) {
        this.lobMappedList = lobMappedList;
    }

    public boolean isRouteCheckBoxEnabled() {
        return isRouteCheckBoxEnabled;
    }

    public void setRouteCheckBoxEnabled(boolean routeCheckBoxEnabled) {
        isRouteCheckBoxEnabled = routeCheckBoxEnabled;
    }

    public String getSalesmanProfileImage() {
        return salesmanProfileImage;
    }

    public void setSalesmanProfileImage(String salesmanProfileImage) {
        this.salesmanProfileImage = salesmanProfileImage;
    }

    public String getSalesmanStatus() {
        return salesmanStatus;
    }

    public void setSalesmanStatus(String salesmanStatus) {
        this.salesmanStatus = salesmanStatus;
    }

    public String getSsfaEnabled() {
        return ssfaEnabled;
    }

    public void setSsfaEnabled(String ssfaEnabled) {
        this.ssfaEnabled = ssfaEnabled;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public HashMap<String, String> getKycImageMap() {
        return kycImageMap;
    }

    public void setKycImageMap(HashMap<String, String> kycImageMap) {
        this.kycImageMap = kycImageMap;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String toString() {
        return salesmanName;
    }
}
