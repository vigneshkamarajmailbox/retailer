package com.botree.retailerssfa.print;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.BaseActivity;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


// =============== SOM, This class is meant for wrapping the Bluetooth connecting interface provided by android devices.......
// =============== Basically is a state machine, and the communications will happen in a separate thread so as not to obstruct the main UI


public class BTWrapperActivity extends BaseActivity {

    public static final int REQUEST_CONNECT_BT = 0x2300;
    private static final String TAG = BTWrapperActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 0x1000;
    // Unique UUID for this application, Basically the SPP Profile
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket mbtSocket = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private ArrayAdapter<String> mArrayAdapter = null;
    //static private Set<BluetoothDevice> btDevices = null;
    private ArrayAdapter<BluetoothDevice> btDevices = null;//BluetoothDevice[] btDevices = null;

    private Dialog progressDialog;

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {

                    // No paired device found
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<>(getApplicationContext(), R.layout.bluetooth_text_view, R.id.txt_bluetooth);
                    }

                    // Ensure non repetability
                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        // Add the name and address to an array adapter to show in a ListView
                        mArrayAdapter.add(device.getName() + "\n" + device.getAddress() + "\n" /*+ "Status : Unpaired"*/);
                        //mArrayAdapter.notifyDataSetChanged();
                        mArrayAdapter.notifyDataSetChanged();
                        cancleProgressDialog();
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "onReceive: ", ex);
                }
            }
        }
    };
    private ListView bluetoothList;
    private Runnable socketErrorRunnable = new Runnable() {

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Cannot establish connection", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();

        }
    };

    public static BluetoothSocket getMbtSocket() {
        return mbtSocket;
    }


    // Som ==== This method will Connect to our SPP Bluetooth Device after discovering and pairing if required
    // Do not forget to add the permission for Bluetooth to use this method
    // Also this method is very tightly coupled with the above method, for getting the status of bt connection

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change the title of the activity
//        setTitle("Bluetooth Devices");

        setContentView(R.layout.btwrapper_activity);

        bluetoothList = findViewById(R.id.bluetooth_list);


        // Get the List of paired and available devices
        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }

        } catch (Exception ex) {
            this.finish();
            return;
        }

        // Register the Broadcast receiver for handling new BT device discovery
        IntentFilter btIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBTReceiver, btIntentFilter);
    }

    private void flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket.close();
                mbtSocket = null;
            }

            if (mBluetoothAdapter != null) {
                mBluetoothAdapter.cancelDiscovery();
            }

            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }

            if (mArrayAdapter != null) {
                mArrayAdapter.clear();
                mArrayAdapter.notifyDataSetChanged();
                mArrayAdapter.notifyDataSetInvalidated();
                mArrayAdapter = null;
            }

        } catch (Exception e) {
            Log.e(TAG, "flushData: " + e.getMessage(), e);
        }

    }

    private int initDevicesList() {

        // Flush any Pending Data
        flushData();

        // Get the Bluetooth Adaptor of the device
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Weird Condition ==== Implies that the device does not have a bluetooth module ?????
            Toast.makeText(getApplicationContext(), "Bluetooth not supported!!", Toast.LENGTH_LONG).show();
            return -1;
        }

        cancelDiscovering();

        mArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.bluetooth_text_view);

        bluetoothList.setAdapter(mArrayAdapter);

        bluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
// Dont proceed if the bluetooth adapter is not valid
                if (mBluetoothAdapter == null) {
                    return;
                }

                // Cancel the dicovery if still going on
                cancelDiscovering();

                // Try to connect with the selected device,
                Toast.makeText(getApplicationContext(), "Connecting to " + btDevices.getItem(position).getName() + "," + btDevices.getItem(position).getAddress(), Toast.LENGTH_SHORT).show();
                showProgressDialog(BTWrapperActivity.this, "Connecting");
                // made the thread different as the connecting proceedure might break down the system
                Thread connectThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
//						Method m = btDevices.getItem(position).getClass().getMethod("createRfcommSocket",
//								new Class[] {int.class}); //createInsecureRfcommSocket
                            //	Method m = btDevices.createRfcommSocketToServiceRecord(SPP_UUID);
                            mbtSocket = btDevices.getItem(position).createRfcommSocketToServiceRecord(SPP_UUID);

                            mbtSocket.connect();
                        } catch (IOException ex) {
                            runOnUiThread(socketErrorRunnable);
                            //Handler myHandler = new Handler();
                            //myHandler.post(socketErrorRunnable);
                            try {
                                mbtSocket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "run: " + e.getMessage(), e);
                            }
                            mbtSocket = null;
                        } finally {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    //flushData();
                                    unregisterReceiver(mBTReceiver);
                                    finish();
                                    cancleProgressDialog();
                                }
                            });
                        }
                    }
                });

                connectThread.start();
            }
        });

        // get the list of devices already paired
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception ex) {
            Log.e(TAG, "initDevicesList: ", ex);
            return -2;
        }

        Toast.makeText(getApplicationContext(), "Getting all available Bluetooth Devices", Toast.LENGTH_SHORT).show();

        return 0;

    } // End getDeviceList

    private void cancelDiscovering() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

        if (reqCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            // Start getting the paired devices list
            Set<BluetoothDevice> btDeviceList = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            try {
                if (!btDeviceList.isEmpty()) {
                    // Loop through paired devices
                    for (BluetoothDevice device : btDeviceList) {
                        btHasDevice(btDeviceList, device);
                    }
                }
            } catch (Exception ex) {
                Log.e(TAG, "onActivityResult: " + ex.getMessage(), ex);
            }

            // Also register for new devices which are discovered
            mBluetoothAdapter.startDiscovery();

            showProgressDialog(BTWrapperActivity.this, "Getting Bluetooth Devices");

        } else {
            Toast.makeText(this, "Denied Bluetooth Permission", Toast.LENGTH_SHORT).show();
            this.finish();
        }

    } // End onActivityResult

    private void btHasDevice(Set<BluetoothDevice> btDeviceList, BluetoothDevice device) {
        if (!btDeviceList.contains(device)) {

            btDevices.add(device); // Add the device to the device list

            // Add the name and address to an array adapter to show in a ListView
            mArrayAdapter.add(device.getName() + "\n" + device.getAddress() /*+ "\n" + "Status : Paired"*/);
            mArrayAdapter.notifyDataSetChanged();
            cancleProgressDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Add the menu options
        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int i = item.getItemId();
        if (i == Menu.FIRST) {
            initDevicesList();

        }

        return true;
    }

    @Override
    public Toolbar getBaseToolbar() {
        return null;
    }

    private void showProgressDialog(Activity fragActivity, String tvMessage) {
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = new Dialog(fragActivity, R.style.ThemeDialogCustom);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.layout_loading_spinner);
        TextView tvLoading = progressDialog.findViewById(R.id.tvLoading);
        RelativeLayout layoutLoding = progressDialog.findViewById(R.id.layoutLoading);
        layoutLoding.setVisibility(View.VISIBLE);
        layoutLoding.setBackgroundColor(ContextCompat.getColor(fragActivity, R.color.transparent));
        tvLoading.setText(tvMessage);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void cancleProgressDialog() {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();

        } catch (Exception e) {
            Log.e(TAG, "cancleProgressDialog: " + e.getMessage(), e);
        }
    }

    @Override
    public void onStop() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onStop();
    }
} // End of class definition

