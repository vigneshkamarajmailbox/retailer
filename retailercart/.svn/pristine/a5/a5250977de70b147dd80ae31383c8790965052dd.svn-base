/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFALocation;
import com.botree.retailerssfa.util.SFALocationUpdate;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.support.Globals.NAME_UPDATE_LOCATION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LAT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LONG;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ROUTECODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;


public class UpdateLocationFragment extends SFAFragment implements
        View.OnClickListener, SFALocationUpdate {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = UpdateLocationFragment.class.getSimpleName();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    FloatingActionButton imgBtn;
    private SFALocation sfaLocation;
    private SFADatabase db;
    private SFASharedPref preferences;
    private String retrCode = "";
    private TextView latitudeTxt;
    private TextView lonngitudeTxt;
    private CoordinatorLayout updateLocationLayout;
    private GoogleMap mMap;
    private boolean isMapReady;
    private boolean isLocationReady;
    private Circle circle;
    private boolean isOutlet = false;
    private Uri fileUri;
    private File photofile;
    private TextView pinCodeTxt;
    private LinearLayout pinCodeLayout;

    public UpdateLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (sfaLocation != null)
            sfaLocation.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setAutoScreenCount(NAME_UPDATE_LOCATION);

        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();
        sfaLocation = new SFALocation(getSFAFragmentActivity(),
                getSFAFragmentActivity().getSavedInstanceBundle(), this);

        if (getArguments() != null) {
            retrCode = getArguments().getString("retailerCode");
            if (getArguments().getBoolean("isOutletVisit")) {
                isOutlet = getArguments().getBoolean("isOutletVisit");
                Log.d(TAG, "is Outlet : " + isOutlet);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_location, container, false);
        ((MainActivity) getSFAFragmentActivity()).hideBottomNevigation();
        initialize(view, savedInstanceState);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAutoScreenCount(NAME_UPDATE_LOCATION);
    }

    private void initialize(View view, final Bundle savedInstanceState) {
        updateLocationLayout = view.findViewById(R.id.update_location_layout);
        Button updateBtn = view.findViewById(R.id.update_loc_btn);
        Button submitBtn = view.findViewById(R.id.loc_update_submit_btn);
        latitudeTxt = view.findViewById(R.id.updatelocation_latitude_txt);
        lonngitudeTxt = view.findViewById(R.id.updatelocation_longitude_txt);
        imgBtn = view.findViewById(R.id.update_loc_img_cap_btn);
        pinCodeTxt = view.findViewById(R.id.update_loc_pin_txt);
        pinCodeLayout = view.findViewById(R.id.pin_code_layout);

        if (isOutlet) {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Please Update the current outlet location, to proceed order booking", Snackbar.LENGTH_LONG);
        }
        MapView mMapView = view.findViewById(R.id.map);

        try {

            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            MapsInitializer.initialize(getSFAFragmentActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "initialize: " + e.getMessage(), e);
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                isMapReady = true;

            }
        });

        updateBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_favorite, menu);
        MenuItem favorite = menu.findItem(R.id.favorite);
        setFavoriteIconBasedOnPref(favorite, NAME_UPDATE_LOCATION);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.favorite) {
            if (!db.isFavoriteMenu(NAME_UPDATE_LOCATION)) {
                menuItem.setIcon(R.drawable.ic_favorite_star_fill);
                db.deleteQuickActions(NAME_UPDATE_LOCATION);
                db.insertQuickAction(NAME_UPDATE_LOCATION, true);
            } else {
                menuItem.setIcon(R.drawable.ic_favorite_star);
                db.deleteQuickActions(NAME_UPDATE_LOCATION);
                db.insertQuickAction(NAME_UPDATE_LOCATION, false);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onStart() {
        if (sfaLocation != null)
            sfaLocation.onStart();

        super.onStart();
    }


    @Override
    public void onResume() {
        if (sfaLocation != null)
            sfaLocation.onResume();

        super.onResume();
    }

    @Override
    public void onPause() {
        if (sfaLocation != null)
            sfaLocation.onPause();

        super.onPause();
    }

    @Override
    public void onStop() {
        if (sfaLocation != null)
            sfaLocation.onStop();

        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    Globals.getOurInstance().getProfileImagePath(getSFAFragmentActivity(), fileUri, photofile, true);

                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Image captured successfully", Toast.LENGTH_SHORT);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "User cancelled image capture", Toast.LENGTH_SHORT);
                } else {
                    // failed to capture image
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Sorry! Failed to capture image", Toast.LENGTH_SHORT);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: " + e.getMessage(), e);
        }

        switch (resultCode) {
            case Activity.RESULT_OK:
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(getSFAFragmentActivity(), "You must enable location service",
                        Toast.LENGTH_LONG).show();
                if (sfaLocation == null) {
                    sfaLocation = new SFALocation(getSFAFragmentActivity(),
                            getSFAFragmentActivity().getSavedInstanceBundle(), this);
                } else {
                    sfaLocation.checkLocationSettings();
                }

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private void setLocation() {

//        String distrCode = preferences.readString(PREF_DISTRCODE);
//        String salesmanCode = preferences.readString(PREF_SALESMANCODE);
//        String routeCode = preferences.readString(PREF_ROUTECODE);

        String loginCode = preferences.readString(PREF_LOGIN_CODE);
        String strPinCode = pinCodeTxt.getText().toString();

//        db.deleteUpdateLocation(distrCode, salesmanCode, retrCode);
//        db.insertUpdateLocation(routeCode, retrCode, String.valueOf(sfaLocation.getLatitude()),
//                String.valueOf(sfaLocation.getLongitude()), strPinCode);
//        db.updateRetailerGeo(String.valueOf(sfaLocation.getLatitude()),
//                String.valueOf(sfaLocation.getLongitude()), distrCode, salesmanCode, routeCode, retrCode);
//        db.updateRetailerMenuReason(distrCode, salesmanCode, routeCode, retrCode);


        List<String> mappedUsers = db.getAllMappedUsers();
        StringBuilder updateTo = new StringBuilder();
        for(String code : mappedUsers){
            updateTo.append(",").append(code);
        }
        if(updateTo.toString().endsWith(","))
            updateTo = new StringBuilder(updateTo.substring(0, updateTo.length() - 1));
        db.deleteUpdateLocation(loginCode);
        db.insertUpdateLocation(loginCode, updateTo.toString(), String.valueOf(sfaLocation.getLatitude()),
                String.valueOf(sfaLocation.getLongitude()), strPinCode);

        Globals.getOurInstance().setStrimage("");
        SFAFragmentManager.newInstance().popBackStack(getSFAFragmentActivity(), null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.update_loc_img_cap_btn:
                showCameraActivity();
                break;

            case R.id.update_loc_btn:

                if (validation()) {
                    updateUI(sfaLocation.getLocation());

                } else {
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Please wait until the location update", Snackbar.LENGTH_LONG);
                }

                break;

            case R.id.loc_update_submit_btn:

                if (validate()) {
                    setLocation();
                }

                break;

            default:
                break;
        }

    }

    private boolean validate() {
        if (String.valueOf(sfaLocation.getLongitude()).length() < 4) {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Please wait until the location update", Snackbar.LENGTH_LONG);
            return false;
        } else if (db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_OUTLET_IMAGE).equalsIgnoreCase("Y")) {
            if (!Globals.getOurInstance().getStrimage().isEmpty()) {
                return true;
            } else {
                NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Please capture the outlet image", Snackbar.LENGTH_LONG);
                return false;
            }
        } else {
            return true;
        }

    }

    private void showCameraActivity() {
        Globals.getOurInstance().setStrimage("");
        if (Globals.getOurInstance().isCameraAvailable(getActivity())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (android.os.Build.VERSION.SDK_INT >= 24) {

                photofile = Globals.getOurInstance().getOutputMediaFile(MEDIA_TYPE_IMAGE);
                fileUri = FileProvider.getUriForFile(getSFAFragmentActivity(),
                        getSFAFragmentActivity().getPackageName() + ".fileprovider", photofile);

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                Log.e(TAG, "onClick: " + fileUri.toString());
            } else {
                fileUri = Globals.getOurInstance().getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            }

            if (intent.resolveActivity(getSFAFragmentActivity().getPackageManager()) != null) {
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

            } else {
                NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
            }

        } else {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
        }
    }

    private boolean validation() {
        return String.valueOf(sfaLocation.getLongitude()).length() > 4;
    }

    private void updateUI(Location newLocation) {
        latitudeTxt.setText(String.valueOf(newLocation.getLatitude()));
        lonngitudeTxt.setText(String.valueOf(newLocation.getLongitude()));

        if (newLocation.getLatitude() > 0.0 && newLocation.getLongitude() > 0.0) {
            preferences.writeString(PREF_LAST_LAT, String.valueOf(newLocation.getLatitude()));
            preferences.writeString(PREF_LAST_LONG, String.valueOf(newLocation.getLongitude()));
        }

        getAddressDetail(newLocation);
    }

    private Marker updateMap(Location newLocation) {

        final LatLng currentLatLon = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());


        final Marker myMarker = mMap.addMarker(new MarkerOptions().position(currentLatLon).title("Current Location"));
        mMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLon, 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLon)      // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return myMarker;
    }

    private boolean removeCircle() {
        if (circle != null) {

            circle.remove();
            circle = null;
            return true;
        }
        return false;
    }


    @Override
    public void newLocation(final Location newLocation) {
        if (mMap != null)
            mMap.clear();

        if (isMapReady && isLocationReady) {

            updateUI(newLocation);
            final Marker myMarker = updateMap(newLocation);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (removeCircle()) {
                        return true;
                    }

                    if (marker != null && marker.getTitle().equals(myMarker.getTitle())) {

                        CircleOptions circleOptions = new CircleOptions()
                                .center(new LatLng(newLocation.getLatitude(), newLocation.getLongitude()))
                                //set center
                                .radius(500)
                                .fillColor(0x4033b5e5)
                                .strokeWidth(0);

                        circle = mMap.addCircle(circleOptions);

                        return true;
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public void onConnectionSuccess() {
        isLocationReady = true;
    }

    @Override
    public void onConnectionError() {
        isLocationReady = false;
    }


    private void getAddressDetail(Location newLocation) {

        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            if (newLocation != null && newLocation.getLatitude() > 0) {
                if (pinCodeTxt.getText().toString().isEmpty()) {
                    List<Address> addresses = geocoder.getFromLocation(newLocation.getLatitude(),
                            newLocation.getLongitude(), 1);
                    String geoPostalCode = addresses.get(0).getPostalCode();
                    pinCodeTxt.setText(geoPostalCode);
                    if (geoPostalCode.isEmpty()) {
                        pinCodeLayout.setVisibility(View.INVISIBLE);
                    }
                }
            } else {
                NotifyUtil.showSnackBar(getSFAFragmentActivity(), updateLocationLayout, "Unable to fetch the current PIN code", Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }
    }
}
