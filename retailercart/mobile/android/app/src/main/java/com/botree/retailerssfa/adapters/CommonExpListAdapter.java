package com.botree.retailerssfa.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class CommonExpListAdapter extends BaseExpandableListAdapter {

    private CommonExpListAdapterInterface commonExpListAdapterInterface;

    @Override
    public int getGroupCount() {
        return commonExpListAdapterInterface.onGetGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return commonExpListAdapterInterface.onGetChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return commonExpListAdapterInterface.onGetGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return commonExpListAdapterInterface.onGetChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    void setCommonExpListAdapterInterface(CommonExpListAdapterInterface commonExpListAdapterInterface) {
        this.commonExpListAdapterInterface = commonExpListAdapterInterface;
    }

    public interface CommonExpListAdapterInterface {
        int onGetGroupCount();

        int onGetChildrenCount(int groupPosition);

        View onGetGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

        View onGetChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    }
}
