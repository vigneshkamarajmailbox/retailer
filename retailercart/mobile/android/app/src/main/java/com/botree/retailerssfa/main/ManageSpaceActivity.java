package com.botree.retailerssfa.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ManageSpaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"User is not allow to manage the app data",Toast.LENGTH_SHORT).show();
        finish();

    }
}