/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.botree.retailerssfa.base.SFAActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runtime permission is checked here. If version is marshmallow or higher then permission need to
 * be add {@link #addPermissions()}.
 */

public class PermissionUtil {

    private static final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    private SFAActivity sfaActivity;
    private List<String> permissionsNeeded = new ArrayList<>();

    private List<String> permissionsList = new ArrayList<>();

    private Map<String, String> permissionMap = new HashMap<>();

    public PermissionUtil(SFAActivity sfaActivity) {
        this.sfaActivity = sfaActivity;

        addPermissions();
    }

    /**
     * Check the permission is granted access or not.
     *
     * @param sfaActivity instance of activity to check the permission is granted or not
     * @param permission  which need to request for grant access from user
     * @return true if permission is already granted else false.
     */
    private static boolean isPermissionGranted(SFAActivity sfaActivity, String permission) {

        return ContextCompat.checkSelfPermission(sfaActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Add all the required permission to the {@link HashMap}
     */
    private void addPermissions() {
        permissionMap.put(Manifest.permission.ACCESS_FINE_LOCATION, "Location");
        permissionMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, "GPS");
        permissionMap.put(Manifest.permission.CAMERA, "Camera");
        permissionMap.put(Manifest.permission.CALL_PHONE, "Call");
        permissionMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write Storage");
        permissionMap.put(Manifest.permission.BLUETOOTH, "BlueTooth");
        permissionMap.put(Manifest.permission.BLUETOOTH_ADMIN, "BlueTooth Admin");
        permissionMap.put(Manifest.permission.RECORD_AUDIO, "Record Audio");
    }

    /**
     * Check whether list of permission is allowed or not. If not allowed then ask for permission.
     */
    public void checkPermission() {
        if (checkMarshMallow()) {
            getPermission();
            if (!permissionsList.isEmpty()) {
                if (!permissionsNeeded.isEmpty()) {

                    showMessageOkCancel(getPermissionMessage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (checkMarshMallow())
                                        sfaActivity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                REQUEST_MULTIPLE_PERMISSIONS);
                                }
                            });
                    return;
                }
                if (checkMarshMallow())
                    sfaActivity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_MULTIPLE_PERMISSIONS);
            }
        }
    }

    /**
     * Permission message is formed here, which contains what are permission need to be grant access.
     *
     * @return the permission message which is displayed to user before permission appear.
     */
    private String getPermissionMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("You need to grant access to ").append(permissionsNeeded.get(0));
        for (int i = 1; i < permissionsNeeded.size(); i++)
            builder.append(", ").append(permissionsNeeded.get(i));
        return builder.toString();
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(sfaActivity)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sfaActivity.finish();
                    }
                })
                .create()
                .show();
    }

    public int onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_MULTIPLE_PERMISSIONS) {
            for (int i = 0; i < permissions.length; i++)
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    return -1;
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Check whether permission is granted or not. If not then add the permission to
     * list which need grant access
     */
    private void getPermission() {
        for (Object o : permissionMap.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            pair.getKey();
            if (!addPermission(permissionsList, pair.getKey().toString()))
                permissionsNeeded.add(pair.getValue().toString());

        }
    }

    /**
     * Check if the permission granted access or denied. If denied then add permission to list.
     *
     * @param permissionsList permission need to added here
     * @param permission      which permission need to add
     * @return true if permission is added to list else false.
     */
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (!isPermissionGranted(sfaActivity, permission)) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (checkMarshMallow())
                return sfaActivity.shouldShowRequestPermissionRationale(permission);
        }
        return true;
    }

    /**
     * Just a check to see if we have marshmallows (version 23)
     *
     * @return true if current version is marshmallow or higher.
     */

    private boolean checkMarshMallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }
}
