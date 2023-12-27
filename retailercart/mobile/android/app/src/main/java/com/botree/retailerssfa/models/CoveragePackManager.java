/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.botree.retailerssfa.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Godlin Josheela Rani S
 */
public class CoveragePackManager {

    private HashMap<Integer, CoveragePackColumn> packs = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> selectionsMap = new HashMap<>();


    public CoveragePackManager() {
    }

    public CoveragePackManager(HashMap<Integer, CoveragePackColumn> packs) {
        this.packs = packs;
    }

    public void addRow(int selectionType) {

        if (selectionType == 0) {
            selectionType = 1;
        }
        CoveragePackColumn coveragePackColumnX = new CoveragePackColumn(selectionType);
        packs.put(packs.size(), coveragePackColumnX);
        Log.w("#PM Init", "" + packs.size());
    }

    public void addRow(int selectionType, ArrayList<Integer> selections) {

        addRow(selectionType);
        int position = packs.size() - 1;

//        for (int i = 0; i < selections.size(); i++) {
//            addSlot(position, selections.get(i));
//        }

        selectionsMap.put(position, selections);
    }

    public boolean addSlot(int rowPosition, int columnPosition) {
        Log.w("#PM", "" + rowPosition + " : " + columnPosition);
        Log.w("#PM", "" + packs.size());

        CoveragePackColumn coveragePackColumn = packs.get(rowPosition);
        int result = coveragePackColumn.selectColumn(columnPosition);
        if (result == -1) {
            lockSlot(rowPosition, columnPosition);
            printPackSlots();
            return true;
        } else if (result == -2) {
            //Failed
            return false;
        } else {
            int columnUnLock = result;
            lockSlot(rowPosition, columnPosition);
            unLockSlot(columnUnLock);
            printPackSlots();
            return true;
        }

    }

    public boolean removeSlot(int rowPosition, int columnPosition) {

        CoveragePackColumn coveragePackColumn = packs.get(rowPosition);
        int result = coveragePackColumn.unSelectColumn(columnPosition);
        if (result == -1) {
            printPackSlots();
            return false;
        } else {
            int columnUnLock = result;
            unLockSlot(columnUnLock);
            printPackSlots();
            return true;
        }
    }

    public boolean setSelection(int rowPosition, int selectionType) {
        Log.w("#PM", "" + rowPosition + " : " + selectionType);
        Log.w("#PM", "" + packs.size());
        CoveragePackColumn coveragePackColumn = packs.get(rowPosition);
        int result = coveragePackColumn.setSelection(selectionType);
        if (result == -1) {
            return false;
        } else {
            int columnUnLock = result;
            unLockSlot(columnUnLock);
            return true;
        }
    }

    private void lockSlot(int rowPosition, int columnPosition) {
        for (int i = 0; i < packs.size(); i++) {
            if (i != rowPosition) {
                CoveragePackColumn coveragePackColumnX = packs.get(i);
                coveragePackColumnX.lockColumn(columnPosition);
                packs.put(i, coveragePackColumnX);
            }
        }
    }

    private void unLockSlot(int columnPosition) {
        for (int i = 0; i < packs.size(); i++) {
            CoveragePackColumn coveragePackColumnX = packs.get(i);
            coveragePackColumnX.unlockColumn(columnPosition);
            packs.put(i, coveragePackColumnX);
        }
    }

    public HashMap<Integer, List<Integer>> getPackData() {
        HashMap<Integer, List<Integer>> packData = new HashMap<>();
        for (int i = 0; i < packs.size(); i++) {
            CoveragePackColumn coveragePackColumnX = packs.get(i);
            packData.put(i, coveragePackColumnX.columns);
        }
        return packData;
    }

    public HashMap<Integer, ArrayList<Integer>> getPackSlots() {
        HashMap<Integer, ArrayList<Integer>> packData = new HashMap<>();
        for (int i = 0; i < packs.size(); i++) {
            CoveragePackColumn coveragePackColumnX = packs.get(i);
            packData.put(i, coveragePackColumnX.slots);
        }
        return packData;
    }

    public HashMap<Integer, ArrayList<Integer>> printPackSlots() {
        HashMap<Integer, ArrayList<Integer>> packData = new HashMap<>();
        for (int i = 0; i < packs.size(); i++) {
            CoveragePackColumn coveragePackColumnX = packs.get(i);
            packData.put(i, coveragePackColumnX.slots);
            Log.w("#PM Row :" + i, Arrays.toString(coveragePackColumnX.slots.toArray()));
        }
        return packData;
    }

    public void updateSlot() {
        Log.w("#PM Map ", "size :" + selectionsMap.size());
        for (int i = 0; i < selectionsMap.size(); i++) {
            ArrayList<Integer> selections = selectionsMap.get(i);
            if (selections != null) {
                Log.w("#PM Map :" + i, Arrays.toString(selections.toArray()));
                for (int s = 0; s < selections.size(); s++) {
                    addSlot(i, selections.get(s));
                }
                printPackSlots();
            }
        }
    }
}
