package com.botree.retailerssfa.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.MyItem;
import com.botree.retailerssfa.models.PendingVO;
import com.botree.retailerssfa.models.SalesmanTrackerModel;
import com.botree.retailerssfa.support.DirectionsJSONParser;
import com.botree.retailerssfa.support.MyClusterRenderer;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.SFALocation;
import com.botree.retailerssfa.util.SFALocationUpdate;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;


public class ShowRetailerInMapFragment extends SFAFragment implements SFALocationUpdate {
    private static final String TAG = ShowRetailerInMapFragment.class.getSimpleName();
    private static final String SINGLE_SALESMAN = "single Salesman";
    private static final String ALL_SALESMANS = "All Salesmans";
    private SFASharedPref preferences;
    private SFADatabase db;
    private SFALocation sfaLocation;
    private String lat;
    private String lng;
    private List<PendingVO> retailerVOList;
    private DownloadTask downloadTask;
    private ParserTaskNew parserTask;
    private GoogleMap mMap;
    private boolean isMapReady;
    private boolean isLocationReady;
    private boolean isMapUpdated = true;
    private ClusterManager mClusterManager;
    private String routeCode;
    private LatLng currentLatLon;
    private String retailerName = "";
    private String retailerCode = "";
    private List<SalesmanTrackerModel> salemanList = new ArrayList<>();

    public ShowRetailerInMapFragment() {
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
        sfaLocation = new SFALocation(getSFAFragmentActivity(),
                getSFAFragmentActivity().getSavedInstanceBundle(), this);
        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();

        if (getArguments() != null) {
            routeCode = getArguments().getString("routeCode");
            retailerName = getArguments().getString("retailerName");
            retailerCode = getArguments().getString("retailerCode");
            salemanList = (getArguments().getParcelableArrayList("salesmanList"));
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_retailer_in_map, container, false);

        intializeView(view, savedInstanceState);

        return view;
    }

    private void intializeView(View view, final Bundle savedInstanceState) {

        MapView mMapView = view.findViewById(R.id.retailer_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getSFAFragmentActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "intializeView: " + e.getMessage(), e);
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                isMapReady = true;

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_login);

        item.setVisible(false);
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

        isMapUpdated = true;

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

        parserTask = null;
        downloadTask = null;

        if (mMap != null)
            mMap.clear();

        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    private Marker updateMap(Location newLocation, int zoomLevel) {


        currentLatLon = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());

        final Marker myMarker = mMap.addMarker(new MarkerOptions().position(currentLatLon).title("YOU")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)));

        mMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLon, zoomLevel));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLon)      // Sets the center of the map to location user
                .zoom(zoomLevel)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return myMarker;
    }

    private void readRetailerAndSetDirection() {

        if (retailerName != null && retailerName.equalsIgnoreCase("All Retailers")) {

            retailerVOList = db.getAllOutletsBasedRoute(preferences.readString(PREF_DISTRCODE),
                    preferences.readString(PREF_SALESMANCODE), routeCode, lat, lng, "");

        } else {

            retailerVOList = db.getAllOutletsBasedRoute(preferences.readString(PREF_DISTRCODE),
                    preferences.readString(PREF_SALESMANCODE), routeCode, lat, lng, retailerCode);
        }

        Collections.sort(retailerVOList, new Comparator<PendingVO>() {
            @Override
            public int compare(PendingVO a, PendingVO b) {
                return a.getAmount().compareTo(b.getAmount());
            }
        });

        try {

            List<MyItem> myItems = new ArrayList<>();

            for (int i = 0; i < retailerVOList.size(); i++) {

                int count = i + 1;

                String kilometer = String.format(Locale.getDefault(), "%.2f", retailerVOList.get(i).getAmount()) + "km";

                String retailername = "" + count + ". " + retailerVOList.get(i).getStrRetailerName();

                myItems.add(new MyItem(Double.valueOf(retailerVOList.get(i).getLatitude()),
                        Double.valueOf(retailerVOList.get(i).getLongitude()),
                        retailername, kilometer, retailerVOList.get(i).getStrCustomerCode()));
            }
            Log.d(TAG, "readRetailerAndSetDirection: " + mClusterManager.getClusterMarkerCollection().getMarkers());


            mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), mMap, mClusterManager));
            mClusterManager.addItems(myItems);

            callDirectionUrl();
            dynamicMapZoomBasedOnMarker(myItems); // Dynamic Zoom for Distr Salesman Tracker
        } catch (Exception e) {
            Log.e(TAG, "readItems1: " + e.getMessage(), e);
        }


    }

    private void salesmanTracker(List<SalesmanTrackerModel> salemanList) {

        try {

            List<MyItem> myItems = new ArrayList<>();

            for (int i = 0; i < salemanList.size(); i++) {

                int count = i + 1;

                String value = String.format(Locale.ENGLISH, "%.2f", salemanList.get(i).getOrderValue());

                String retailername = "" + count + ". " + salemanList.get(i).getSalesmanName();

                Log.d(TAG, "salesmanTracker1: " + Double.valueOf(salemanList.get(i).getLatitude()) + " | " + Double.valueOf(salemanList.get(i).getLongitude()));
                myItems.add(new MyItem(Double.valueOf(salemanList.get(i).getLatitude()),
                        Double.valueOf(salemanList.get(i).getLongitude()),
                        retailername, value, salemanList.get(i).getSalesmanStatus()));
            }

            mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), mMap, mClusterManager));
            mClusterManager.addItems(myItems);

            dynamicMapZoomBasedOnMarker(myItems); // Dynamic Zoom for Distr Salesman Tracker

            Log.d(TAG, "readItems3: " + mClusterManager.getClusterMarkerCollection().getMarkers());

        } catch (Exception e) {
            Log.e(TAG, "salesmanTracker2: " + e.getMessage(), e);
        }


    }

    /**
     * Dynamic Zoom for Distr Salesman Tracker
     *
     * @param myItems Marker Items
     */
    private void dynamicMapZoomBasedOnMarker(List<MyItem> myItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MyItem marker : myItems) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        mMap.setMaxZoomPreference(19.0f);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200), null);
    }


    private void updateUI(Location newLocation) {

        lat = String.valueOf(newLocation.getLatitude());
        lng = String.valueOf(newLocation.getLongitude());
    }

    @Override
    public void newLocation(final Location newLocation) {

        //This is to provide latlng of salesman to mapview, so that current location latlng will not be provided
        if (retailerName.equalsIgnoreCase(SINGLE_SALESMAN) || retailerName.equalsIgnoreCase(ALL_SALESMANS)) {
            Location singleSalesManLocation = new Location("");
            if (!salemanList.isEmpty() && !salemanList.get(0).getLatitude().isEmpty() && !salemanList.get(0).getLongitude().isEmpty()) {
                singleSalesManLocation.setLatitude(Double.parseDouble(salemanList.get(0).getLatitude()));
                singleSalesManLocation.setLongitude(Double.parseDouble(salemanList.get(0).getLongitude()));
            }

            helperMethod(singleSalesManLocation);

        } else {
            helperMethod(newLocation);
        }

    }

    private void helperMethod(Location location) {
        if (isMapReady && isLocationReady) {
            updateUI(location);

            if (isMapUpdated) {
                isMapUpdated = false;
                startDemo(location);
            }

        }
    }

    private void startDemo(final Location newLocation) {


        setZoomLevel(newLocation);


        //If Loop validation is to prevent showing current location marker for Distributor Tracker
        if (!(retailerName.equalsIgnoreCase(SINGLE_SALESMAN) || retailerName.equalsIgnoreCase(ALL_SALESMANS))) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLocation.getLatitude()
                    , newLocation.getLongitude()), 10));

            final Marker myMarker = updateMap(newLocation, 12);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return markerClickLogic(marker, myMarker, newLocation);

                }
            });
        }


        mClusterManager = new ClusterManager<>(getSFAFragmentActivity(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(final Cluster<MyItem> cluster) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (ClusterItem item : cluster.getItems()) {
                    builder.include(item.getPosition());
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), (float) Math.floor(mMap
                                .getCameraPosition().zoom + 1)), 300,
                        null);

                return true;
            }
        });

        if (checkRetailerName()) {

            // Getting URL to the Google Directions API
            readRetailerAndSetDirection();

        } else {

            salesmanTracker(salemanList);
        }

    }

    private boolean markerClickLogic(Marker marker, Marker myMarker, Location newLocation) {
        try {
            if ((marker != null && myMarker != null) && marker.getTitle().equals(myMarker.getTitle())) {

                updateMap(newLocation, 14);

                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "onMarkerClick: " + e.getMessage(), e);
        }
        return false;
    }

    private boolean checkRetailerName() {
        return (retailerName != null && retailerName.equalsIgnoreCase("All Retailers"))
                || (retailerName != null && retailerName.equalsIgnoreCase("single retailer"));
    }

    private void setZoomLevel(Location newLocation) {
        if (retailerName.equalsIgnoreCase(SINGLE_SALESMAN)) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLocation.getLatitude()
                    , newLocation.getLongitude()), 16));
        } else if (retailerName.equalsIgnoreCase(ALL_SALESMANS)) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLocation.getLatitude()
                    , newLocation.getLongitude()), 14));
        }
    }

    private void callDirectionUrl() {
        String url;
        try {
            url = getDirectionsUrl();
            Log.d(TAG, "startDemo: " + url);
            downloadTask = new DownloadTask(ShowRetailerInMapFragment.this);
            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        } catch (Exception e) {
            Log.e(TAG, "startDemo: " + e.getMessage(), e);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMap != null)
            mMap.clear();
    }

    private String getDirectionsUrl() {

        // Origin of route
        String strOrigin = "origin=" + currentLatLon.latitude + "," + currentLatLon.longitude;

        List<PendingVO> filterLatLongRetailer = new ArrayList<>();
        for (PendingVO pendingVO : retailerVOList) {
            if (Double.parseDouble(pendingVO.getLatitude()) > 0.0 && Double.parseDouble(pendingVO.getLongitude()) > 0.0) {
                filterLatLongRetailer.add(pendingVO);
            }
        }


        String strDest;
        int retCount;
        // Destination of route
        if (filterLatLongRetailer.size() > 8) {
            strDest = "destination=" + filterLatLongRetailer.get(8).getLatitude() + "," + filterLatLongRetailer.get(8).getLongitude();
            retCount = 8;
        } else {
            strDest = "destination=" + filterLatLongRetailer.get(filterLatLongRetailer.size() - 1).getLatitude() + "," + filterLatLongRetailer.get(filterLatLongRetailer.size() - 1).getLongitude();
            retCount = filterLatLongRetailer.size();
        }

        StringBuilder waypoints = new StringBuilder("waypoints=");
        for (int i = 0; i < retCount - 1; i++) {
            waypoints.append(filterLatLongRetailer.get(i).getLatitude()).append(",").append(filterLatLongRetailer.get(i).getLongitude()).append("|");
        }

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = strOrigin + "&" + strDest + "&" + waypoints + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppUtils.getOurInstance().cancleProgressDialog();

    }

    private static class ParserTaskNew extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        private final ShowRetailerInMapFragment activity;
        private final WeakReference<ShowRetailerInMapFragment> activityReference;
        List<LatLng> points = null;
        PolylineOptions lineOptions = null;

        private ParserTaskNew(ShowRetailerInMapFragment context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = new ArrayList<>();

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parseMultipleRoute(jObject);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }


            try {

                for (int i = 0; i < routes.size(); i++) {
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();
                    List<HashMap<String, String>> path = routes.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap point = path.get(j);

                        double lat = Double.parseDouble((String) point.get("lat"));
                        double lng = Double.parseDouble((String) point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    lineOptions.addAll(points);
                    lineOptions.width(20f);
                    lineOptions.clickable(true);
                    lineOptions.zIndex(0f);
                    lineOptions.color(ContextCompat.getColor(activity.getSFAFragmentActivity(), R.color.color_blue_maps_line));
                    lineOptions.geodesic(true);
                    lineOptions.startCap(new RoundCap());
                    lineOptions.endCap(new RoundCap());
                }


// Drawing polyline in the Google Map for the i-th route

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            AppUtils.getOurInstance().cancleProgressDialog();

            try {

                if (lineOptions != null)
                    activity.mMap.addPolyline(lineOptions);
            } catch (Exception e) {
                Log.e(TAG, "onPostExecute: " + e.getMessage(), e);
            }

        }
    }

    private static class DownloadTask extends AsyncTask<String, Void, String> {

        private final WeakReference<ShowRetailerInMapFragment> activityReference;
        private final ShowRetailerInMapFragment activity;

        private DownloadTask(ShowRetailerInMapFragment context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            AppUtils.getOurInstance().showProgressDialog(activity.getSFAFragmentActivity(), "Loading please wait...");

        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                activity.parserTask = new ParserTaskNew(activity);
                activity.parserTask.execute(result);
                activity.parserTask.get(30000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                Log.e(TAG, "onPostExecute: " + e.getMessage(), e);
            }

        }


        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                assert iStream != null;
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }
}
