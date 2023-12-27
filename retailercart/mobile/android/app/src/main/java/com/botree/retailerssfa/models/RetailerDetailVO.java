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

public class RetailerDetailVO {

    private String distCode;
    private String salesmanCode;
    private String routeCode;
    private String customerCode;
    private boolean containsOrder;
    private boolean containsCollection;
    private boolean containsSalesReturn;
    private boolean containsSurvey;
    private boolean containsStockCapture;
    private boolean containsReason;

    public boolean isContainsOrder() {
        return containsOrder;
    }

    public void setContainsOrder(boolean containsOrder) {
        this.containsOrder = containsOrder;
    }

    public boolean isContainsCollection() {
        return containsCollection;
    }

    public void setContainsCollection(boolean containsCollection) {
        this.containsCollection = containsCollection;
    }

    public boolean isContainsSalesReturn() {
        return containsSalesReturn;
    }

    public void setContainsSalesReturn(boolean containsSalesReturn) {
        this.containsSalesReturn = containsSalesReturn;
    }

    public boolean isContainsSurvey() {
        return containsSurvey;
    }

    public void setContainsSurvey(boolean containsSurvey) {
        this.containsSurvey = containsSurvey;
    }

    public boolean isContainsStockCapture() {
        return containsStockCapture;
    }

    public void setContainsStockCapture(boolean containsStockCapture) {
        this.containsStockCapture = containsStockCapture;
    }

    public boolean isContainsReason() {
        return containsReason;
    }

    public void setContainsReason(boolean containsReason) {
        this.containsReason = containsReason;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetailerDetailVO that = (RetailerDetailVO) o;

        if (isContainsOrder() != that.isContainsOrder()) return false;
        if (isContainsCollection() != that.isContainsCollection()) return false;
        if (isContainsSalesReturn() != that.isContainsSalesReturn()) return false;
        if (isContainsSurvey() != that.isContainsSurvey()) return false;
        if (isContainsStockCapture() != that.isContainsStockCapture()) return false;
        if (isContainsReason() != that.isContainsReason()) return false;
        if (!getDistCode().equals(that.getDistCode())) return false;
        if (!getSalesmanCode().equals(that.getSalesmanCode())) return false;
        if (!getRouteCode().equals(that.getRouteCode())) return false;
        return getCustomerCode().equals(that.getCustomerCode());

    }

    @Override
    public int hashCode() {
        int result = getDistCode().hashCode();
        result = 31 * result + getSalesmanCode().hashCode();
        result = 31 * result + getRouteCode().hashCode();
        result = 31 * result + getCustomerCode().hashCode();
        result = 31 * result + (isContainsOrder() ? 1 : 0);
        result = 31 * result + (isContainsCollection() ? 1 : 0);
        result = 31 * result + (isContainsSalesReturn() ? 1 : 0);
        result = 31 * result + (isContainsSurvey() ? 1 : 0);
        result = 31 * result + (isContainsStockCapture() ? 1 : 0);
        result = 31 * result + (isContainsReason() ? 1 : 0);
        return result;
    }
}
