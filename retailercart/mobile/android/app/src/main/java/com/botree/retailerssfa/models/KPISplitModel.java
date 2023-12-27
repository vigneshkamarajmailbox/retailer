package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ACHIEVED_LEVEL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ACHIEVED_PERC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTUAL_INCENTIVE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KPI_INCENTIVE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KPI_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KPI_SLAB_WEIGHTAGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_RANGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_WEIGHTAGE;

public class KPISplitModel {

    @SerializedName(TAG_KPI_NAME)
    private String kpisLabels;

    @SerializedName(TAG_ACTUAL_INCENTIVE)
    private Integer kpisTargets = 0;

    @SerializedName(TAG_KPI_INCENTIVE)
    private Integer kpisAchieved = 0;

    @SerializedName(TAG_ACHIEVED_PERC)
    private Integer kpisAchievedPercen = 0;

    @SerializedName(TAG_ACHIEVED_LEVEL)
    private String kpisAchievedLevel = "";

    @SerializedName(TAG_WEIGHTAGE)
    private Integer kpiWeightage = 0;

    @SerializedName(TAG_KPI_SLAB_WEIGHTAGE)
    private List<KPISlabWeightageList> kpiSlabWeightageList = new ArrayList<>();

    public String getKpisLabels() {
        return kpisLabels;
    }

    public void setKpisLabels(String kpisLabels) {
        this.kpisLabels = kpisLabels;
    }

    public Integer getKpisTargets() {
        return kpisTargets;
    }

    public void setKpisTargets(Integer kpisTargets) {
        this.kpisTargets = kpisTargets;
    }

    public Integer getKpisAchieved() {
        return kpisAchieved;
    }

    public void setKpisAchieved(Integer kpisAchieved) {
        this.kpisAchieved = kpisAchieved;
    }

    public Integer getKpisAchievedPercen() {
        return kpisAchievedPercen;
    }

    public void setKpisAchievedPercen(Integer kpisAchievedPercen) {
        this.kpisAchievedPercen = kpisAchievedPercen;
    }

    public Integer getKpiWeightage() {
        return kpiWeightage;
    }

    public void setKpiWeightage(Integer kpiWeightage) {
        this.kpiWeightage = kpiWeightage;
    }

    public String getKpisAchievedLevel() {
        return kpisAchievedLevel;
    }

    public void setKpisAchievedLevel(String kpisAchievedLevel) {
        this.kpisAchievedLevel = kpisAchievedLevel;
    }

    public List<KPISlabWeightageList> getKpiSlabWeightageList() {
        return kpiSlabWeightageList;
    }

    public void setKpiSlabWeightageList(List<KPISlabWeightageList> kpiSlabWeightageList) {
        this.kpiSlabWeightageList = kpiSlabWeightageList;
    }

    public static class KPISlabWeightageList {

        @SerializedName(TAG_RANGE)
        private Integer kpiSlabRange = 0;

        @SerializedName(TAG_WEIGHTAGE)
        private Integer kpiSlabWeightage = 0;

        public Integer getKpiSlabRange() {
            return kpiSlabRange;
        }

        public void setKpiSlabRange(Integer kpiSlabRange) {
            this.kpiSlabRange = kpiSlabRange;
        }

        public Integer getKpiSlabWeightage() {
            return kpiSlabWeightage;
        }

        public void setKpiSlabWeightage(Integer kpiSlabWeightage) {
            this.kpiSlabWeightage = kpiSlabWeightage;
        }
    }
}
