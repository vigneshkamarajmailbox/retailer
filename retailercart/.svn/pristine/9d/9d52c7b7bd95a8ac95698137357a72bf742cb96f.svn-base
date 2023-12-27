package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinothbaskaran on 16/2/18.
 */

public class UserModel implements Serializable {

    private String userCode = "";

    private String cmpCode = "";

    @SerializedName("password")
    private String credential = "";

    private String userName = "";

    private String userType = "";

    private String mappedCode = "";

    private Boolean isLastLevel = true;

    private String hierLevel = "";

    private String lastHierLevel = "";

    @SerializedName("enableSubSearch")
    private Boolean enableSubSearch = false;

    private Boolean loginStatus = false;

    private String message;

    @SerializedName("mobileNo")
    private String mobileNo = "";

    @SerializedName("token")
    private String accessToken = "";

    @SerializedName("salesman")
    private Users users = new Users();

    @SerializedName("stockistMappingList")
    private List<StockistModel> stockistModelList = new ArrayList<>();


    private StockistModel stockistModel = new StockistModel();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String password) {
        this.credential = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMappedCode() {
        return mappedCode;
    }

    public void setMappedCode(String mappedCode) {
        this.mappedCode = mappedCode;
    }

    public Boolean getLastLevel() {
        return isLastLevel;
    }

    public void setLastLevel(Boolean lastLevel) {
        isLastLevel = lastLevel;
    }

    public String getHierLevel() {
        return hierLevel;
    }

    public void setHierLevel(String hierLevel) {
        this.hierLevel = hierLevel;
    }

    public String getLastHierLevel() {
        return lastHierLevel;
    }

    public void setLastHierLevel(String lastHierLevel) {
        this.lastHierLevel = lastHierLevel;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userCode='" + userCode + '\'' +
                ", credential='" + credential + '\'' +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", mappedCode='" + mappedCode + '\'' +
                ", isLastLevel=" + isLastLevel +
                ", hierLevel='" + hierLevel + '\'' +
                ", lastHierLevel='" + lastHierLevel + '\'' +
                ", loginStatus=" + loginStatus +
                ", message='" + message + '\'' +
                ", users=" + users +
                '}';
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public List<StockistModel> getStockistModelList() {
        return stockistModelList;
    }

    public void setStockistModelList(List<StockistModel> stockistModelList) {
        this.stockistModelList = stockistModelList;
    }

    public StockistModel getStockistModel() {
        return stockistModel;
    }

    public void setStockistModel(StockistModel stockistModel) {
        this.stockistModel = stockistModel;
    }

    public Boolean getEnableSubSearch() {
        return enableSubSearch;
    }

    public void setEnableSubSearch(Boolean enableSubSearch) {
        this.enableSubSearch = enableSubSearch;
    }
}
