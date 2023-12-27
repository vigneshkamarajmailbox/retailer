/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.botree.retailerssfa.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Godlin Josheela Rani S
 */

public class CoveragePackColumn {
    private Integer selectionType = 1;
    private static final Integer COLUMN_COUNT = 7;
    List<Integer> columns = new ArrayList<>();
    ArrayList<Integer> slots = new ArrayList<>();

    public CoveragePackColumn() {
    }

    public CoveragePackColumn(Integer selectionType) {
        this.selectionType = selectionType;

        for (int i = 0; i < COLUMN_COUNT; i++) {
            slots.add(0);
        }
    }

    public CoveragePackColumn(Integer selectionType, List<Integer> columns) {
        this.selectionType = selectionType;
        this.columns = columns;

        for (int i = 0; i < COLUMN_COUNT; i++) {
            slots.add(0);
        }

        slotUpdate();
    }

    private void slotUpdate() {
        for (int i = 0; i < columns.size(); i++) {
            slots.set(columns.get(i), 1);
        }
    }

    public void lockColumn(Integer column) {
        slots.set(column, -1);
    }

    public void unlockColumn(Integer column) {
        slots.set(column, 0);
    }

    public int selectColumn(Integer column) {
        int unlock;
        if (slots.get(column) != -1) {
            unlock = addColumn(column);
            slots.set(column, 1);
            return unlock;
        } else {
            unlock = -2;
        }
        return unlock;
    }

    public int unSelectColumn(Integer column) {
        int unlock = -1;
        if (slots.get(column) != -1) {
            unlock = removeColumn(column);
            slots.set(column, 0);
            return unlock;
        } else {
            unlock = -1;
        }
        return unlock;
    }

    private int addColumn(Integer column) {
        int unlock = -1;
        if (columns.size() < selectionType) {
            columns.add(column);
        } else {
            unlock = columns.get(columns.size() - 1);
            columns.remove(columns.size() - 1);
            columns.add(column);
        }

        return unlock;
    }

    public int removeColumn(Integer value) {
        int unlock = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(value)) {
                unlock = columns.get(i);
                columns.remove(i);
            }
        }
        return unlock;
    }

    public int setSelection(Integer selection) {
        int unlock = -1;
        if (selection > 0) {
            while (columns.size() > selection) {
                unlock = columns.get(columns.size() - 1);
                columns.remove(columns.size() - 1);
            }
            this.selectionType = selection;
        }
        return unlock;
    }
}
