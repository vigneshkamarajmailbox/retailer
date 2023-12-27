package com.botree.retailerssfa.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.GridDashboardAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.FeedBackMasterModel;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.models.MyItem;
import com.botree.retailerssfa.models.PendingVO;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.support.MyClusterRenderer;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotifyUtil;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.itextpdf.text.pdf.parser.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_FEEDBACK_REASON;
import static com.botree.retailerssfa.main.UpdateLocationFragment.MEDIA_TYPE_IMAGE;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME;

public class TransactionFragment extends SFAFragment implements SFALocationUpdate, View.OnClickListener {
    private RecyclerView mRecyclerTransactionDashboard;
    private SFASharedPref preferences;
    private LinearLayout rootLayout;
    private LinearLayout feedbackbutton;
    private SFALocation sfaLocation;
    private boolean isfeedShow = true;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private EditText feedEditView;
    private View tranView;
    private File photofile;
    private Uri fileUri;
    private Spinner feedbackSpinner;
    private BottomSheetBehavior behavior;
    private List<QuickViewVo> seqQuickViewVos = new ArrayList<>();
    private final String TAG = TransactionFragment.class.getSimpleName();
    private GoogleMap mMap;
    private boolean isMapReady;
    private boolean isLocationReady;
    private String lat = "0";
    private String lng = "0";
    private ClusterManager mClusterManager;
    private boolean isMapUpdated = true;
    private List<PendingVO> retailerVOList = new ArrayList<>();
    private RetailerVO retailerLatLong;
    private ImageView feedboadExpandImg;

    //New FeedBack Master
    private SFADatabase db;
    private Spinner distr_spinner;
    private ArrayList<CustomerModel> distributorArrayList = new ArrayList<>();
    private String selectedDistr = "";
    private String selectedFeedbackCode = "", selectedFeedbackName = "";
    private int selectedFeedBackPos = 0, selectedDistrPos = 0;
    private Button feedback_submit_btn;
    private EditText feedback_edt_txt;
    private ImageView feedbackImgIv;

    public TransactionFragment() {
        // Requires empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = SFASharedPref.getOurInstance();
        sfaLocation = new SFALocation(getSFAFragmentActivity(),
                getSFAFragmentActivity().getSavedInstanceBundle(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAutoScreenCount(Globals.NAME_TRANSACTION);

        initViews(view);
        loadlist();
        initializeMap(view, savedInstanceState);
        loadDashboardMenus();
        GridDashboardAdapter gridDashboardAdapter = new GridDashboardAdapter(loadConfigDashboardMenus(), Boolean.FALSE);
        mRecyclerTransactionDashboard.setAdapter(gridDashboardAdapter);

        gridDashboardAdapter.setOnRecyclerItemClick(new GridDashboardAdapter.OnRecyclerItemClick() {
            @Override
            public void onItemClick(int position, String menuName) {
                callFragmentBasedOnPosition(menuName);
            }
        });

        ((MainActivity) getSFAFragmentActivity()).showBottomNevigation();
    }

    void loadlist() {
        PendingVO pendingData1 = new PendingVO();
        pendingData1.setStrRouteCode("ROU1093");
        pendingData1.setStrCustomerCode("CUS12731");
        pendingData1.setStrRetailerName("RETAILER TEST");

        double latitude = 12.960145;
        double longitude = 80.198226;

        pendingData1.setLatitude(String.valueOf(latitude));
        pendingData1.setLongitude(String.valueOf(longitude));

        Location loc1 = new Location("");
        loc1.setLatitude(Double.valueOf(lat));
        loc1.setLongitude(Double.valueOf(lng));

        Location loc2 = new Location("");
        loc2.setLatitude(latitude);
        loc2.setLongitude(longitude);

        float distanceInMeters = loc1.distanceTo(loc2) / 1000;

        pendingData1.setAmount((double) distanceInMeters);

        retailerVOList.add(pendingData1);

        retailerLatLong = new RetailerVO();
        retailerLatLong.setLatitude(retailerVOList.get(0).getLatitude());
        retailerLatLong.setLongitude(retailerVOList.get(0).getLongitude());
    }

    private void initializeMap(View view, Bundle savedInstanceState) {

        MapView mMapView = view.findViewById(R.id.retailer_dashboard_map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getSFAFragmentActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "initializeMap: " + e.getMessage(), e);
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                isMapReady = true;

            }
        });

    }

    private List<QuickViewVo> loadConfigDashboardMenus() {
        List<QuickViewVo> transactionMenuList = getQuickViewVos();
        List<QuickViewVo> nonSeqQuickViewVos = getQuickViewVos(transactionMenuList);

        Collections.sort(seqQuickViewVos, new Comparator<QuickViewVo>() {
            @Override
            public int compare(QuickViewVo o1, QuickViewVo o2) {
                return o1.getSequence() - o2.getSequence();
            }
        });

        List<QuickViewVo> resultMenus = new ArrayList<>();
        resultMenus.addAll(seqQuickViewVos);
        resultMenus.addAll(nonSeqQuickViewVos);

        return resultMenus;
    }

    @NonNull
    private List<QuickViewVo> getQuickViewVos(List<QuickViewVo> outletDashboardMenus) {
        seqQuickViewVos = new ArrayList<>();
        List<QuickViewVo> nonSeqQuickViewVos = new ArrayList<>();
        for (QuickViewVo quickViewVo : outletDashboardMenus) {
            if (quickViewVo.getSequence() > 0) {
                seqQuickViewVos.add(quickViewVo);
            } else
                nonSeqQuickViewVos.add(quickViewVo);
            if (quickViewVo.getSequence() == 0) {
                quickViewVo.setSeqVisible(Boolean.TRUE);
            }
        }
        return nonSeqQuickViewVos;
    }

    private List<QuickViewVo> getQuickViewVos() {
        List<QuickViewVo> dashboardMenuList = new ArrayList<>();

        List<ScreenConfig> screenConfigList = DataManager.getInstance().getScreenConfigArrayList();
        for (ScreenConfig screenConfig : screenConfigList) {
//            if (isScreenVisibleTrue(screenConfig, 2, 1)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_OPENING_STOCK, R.drawable.ic_opening_stock, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 2)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_ORDER, R.drawable.ic_purchase_order, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 3)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_RECEIPT, R.drawable.ic_purchase_receipt, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 4)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_LOGISTIC_MATERIAL, R.drawable.ic_logistic_material, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 5)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_TRANSACTION_BILLING, R.drawable.billing, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 6)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_SALES_RETURN, R.drawable.ic_sales_return, true, screenConfig.getSequence()));
//            } else if (isScreenVisibleTrue(screenConfig, 2, 7)) {
//                dashboardMenuList.add(new QuickViewVo(NAME_STOCK_ADJUSTMENT, R.drawable.stock_adj, true, screenConfig.getSequence()));
//            }
        }
        dashboardMenuList.add(new QuickViewVo(NAME_ORDER_BOOKING, R.drawable.order_booking, true, 0));
        dashboardMenuList.add(new QuickViewVo(NAME_SCHEME, R.drawable.ic_scheme_24dp, true, 0));
        dashboardMenuList.add(new QuickViewVo(NAME_PRODUCT, R.drawable.icon_product, true, 0));
        return dashboardMenuList;
    }

    private void loadDashboardMenus() {
        ArrayList<QuickViewVo> masterMenus = new ArrayList<>();
        //masterMenus.add(new QuickViewVo(NAME_TRANSACTION_COLLECTION, R.drawable.collection, true));
//        //masterMenus.add(new QuickViewVo(NAME_ORDER_BOOKING, R.drawable.icon_channel, true));
//        masterMenus.add(new QuickViewVo(NAME_OPENING_STOCK, R.drawable.ic_opening_stock, true));
//        masterMenus.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_ORDER, R.drawable.ic_purchase_order)); //cart
//        masterMenus.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_RECEIPT, R.drawable.ic_purchase_receipt)); //ic_receipt
//        masterMenus.add(new QuickViewVo(NAME_LOGISTIC_MATERIAL, R.drawable.ic_logistic_material, true)); //logistic
//        masterMenus.add(new QuickViewVo(NAME_TRANSACTION_BILLING, R.drawable.billing, true));
//        //masterMenus.add(new QuickViewVo(SCREEN_NAME_SALES_PANEL, R.drawable.icon_channel, true));
//        masterMenus.add(new QuickViewVo(NAME_SALES_RETURN, R.drawable.ic_sales_return, true));
//        //masterMenus.add(new QuickViewVo(NAME_MANUAL_CLAIMS, R.drawable.icon_channel, true));
//        masterMenus.add(new QuickViewVo(NAME_STOCK_ADJUSTMENT, R.drawable.stock_adj, true));

        masterMenus.add(new QuickViewVo(NAME_ORDER_BOOKING, R.drawable.order_booking, true, 0));
        masterMenus.add(new QuickViewVo(NAME_SCHEME, R.drawable.ic_scheme_24dp, true, 0));
        masterMenus.add(new QuickViewVo(NAME_PRODUCT, R.drawable.icon_product, true, 0));

//        for (int i = 0; i < masterMenus.size(); i++) {
//            masterMenus.get(i).setColor(listofColors[i % (listofColors.length - 1)]);
//        }
    }


    private void callFragmentBasedOnPosition(String name) {

        ESFAFragTags fragTags = null;



        switch (name) {



            case NAME_ORDER_BOOKING:
                fragTags = ESFAFragTags.ORDER_BOOKING;
                break;
            case NAME_SCHEME:
                //Intent to RetailerSchemeUtilizationActivity if schemedistr list less than 2
                if (db.getSchemeDistrListCount() > 1) {
                    Intent schemeIntent = new Intent(getSFAFragmentActivity(), SchemeDistributorListActivity.class);
                    schemeIntent.putExtra("ScreenName", Globals.NAME_SCHEME);
                    startActivity(schemeIntent);
                } else {
                    ArrayList<Distributor> distributors = db.getSchemeDistrList();
                    String cmp_code = distributors.get(0).getCompanyCode();
                    String distr_code = distributors.get(0).getDistributorCode();
                    Intent in = new Intent(getSFAFragmentActivity(), RetailerSchemeUtilizationActivity.class);
                    in.putExtra("screen", Globals.NAME_SCHEME);
                    in.putExtra("cmp_code", cmp_code);
                    in.putExtra("distr_code", distr_code);
                    startActivity(in);
                }
                break;

            case NAME_PRODUCT:
                Intent intent = new Intent(getSFAFragmentActivity(), ProductMasterActivity.class);
                startActivity(intent);
                break;

            default:

                break;

        }

        callFragmentByTag(fragTags, name);

    }



    private void callFragmentByTag(ESFAFragTags fragTags, String fragmentName) {
        if (fragTags != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("fragmentPosition", 1);
            bundle.putString("fragmentName", fragmentName);
            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            sfaFragManger.updateFragment(fragTags, true, getSFAFragmentActivity(), bundle);
        }
    }

    private boolean isScreenVisibleTrue(ScreenConfig screenConfig, int moduleNo, int screenNo) {
        return screenConfig.getModuleNo() == moduleNo && screenConfig.getScreenNo() == screenNo
                && screenConfig.getChecked().equalsIgnoreCase("Y");
    }

    private void initViews(View view) {
        db = SFADatabase.getInstance(getSFAFragmentActivity());
        tranView = view.findViewById(R.id.tras_view);
        tranView.setOnClickListener(null);
        rootLayout = view.findViewById(R.id.root_layout);
        feedbackbutton = view.findViewById(R.id.feedback_layout);
        mRecyclerTransactionDashboard = view.findViewById(R.id.mRecyclerTransactionDashboard);
        feedbackSpinner = view.findViewById(R.id.feedback_spinner);
        distr_spinner = view.findViewById(R.id.distr_spinner);
        feedboadExpandImg = view.findViewById(R.id.feedboad_expand_img);
        ImageView feedbackImageCaptureBtn = view.findViewById(R.id.feedback_camera_img);
        feedbackImageCaptureBtn.setOnClickListener(this);
        loadBottomSheet(view);
        mRecyclerTransactionDashboard.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        mRecyclerTransactionDashboard.setHasFixedSize(true);
    }

    private void loadBottomSheet(View view) {
        List<ScreenConfig> screenConfigList = DataManager.getInstance().getScreenConfigArrayList();
        for (ScreenConfig screenConfig : screenConfigList) {
            if (screenConfig.getModuleNo() == 3 && screenConfig.getScreenNo() == 11 && screenConfig.getChecked().equalsIgnoreCase("true")) {
                view.findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
            }
        }


        behavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet));
        feedback_submit_btn = view.findViewById(R.id.feedback_submit_btn);
        feedback_edt_txt = view.findViewById(R.id.feedback_edt_txt);
        feedbackImgIv = view.findViewById(R.id.feedback_camera_img);
        // Capturing the callbacks for bottom sheet
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // on state chenged not in use
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                    if (tranView.getVisibility() != View.VISIBLE) {
                        tranView.setVisibility(View.VISIBLE);
                    }
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    if (tranView.getVisibility() == View.VISIBLE) {
                        tranView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // on slide not in use
            }
        });

        feedback_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback_edt_txt.getText().toString().length()>0)
                {
                    FeedbackModel feedbackModel = new FeedbackModel();
                    feedbackModel.setCmpCode(distributorArrayList.get(selectedDistrPos).getCmpCode());
                    feedbackModel.setFeedbackNo(UUID.randomUUID().toString());
                    feedbackModel.setFeedbackDate(""+System.currentTimeMillis());
                    feedbackModel.setDistrCode(distributorArrayList.get(selectedDistrPos).getDistrCode());
                    feedbackModel.setCustomerCode(distributorArrayList.get(selectedDistrPos).getCustomerCode());
                    feedbackModel.setFeedbackType(selectedFeedbackCode);
                    feedbackModel.setMessage(feedback_edt_txt.getText().toString());
                    feedbackModel.setFeedbackImage(Globals.getOurInstance().getStrimage());
                    db.insertFeedback(feedbackModel);
                    Toast.makeText(getActivity(), "Feedback Submitted", Toast.LENGTH_SHORT).show();

                    if (tranView.getVisibility() == View.VISIBLE) {
                        tranView.setVisibility(View.GONE);
                    }
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    feedbackImgIv.setVisibility(View.GONE);
                    Globals.getOurInstance().setStrimage("");
                    feedback_edt_txt.setText("");

                    ArrayList<FeedbackModel> feedbackModelArrayList = db.getAllFeedback();
                    System.out.println(feedbackModelArrayList.toString());
                }else
                {
                    Toast.makeText(getActivity(), "Feedback cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        rotateFabForward();

        feedbackPanel();
    }

    /* Rotating floading button forward */
    public void rotateFabForward() {
        ViewCompat.animate(feedboadExpandImg)
                .rotation(180.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }
    /* Rotating floading button backward */

    public void rotateFabBackward() {
        ViewCompat.animate(feedboadExpandImg)
                .rotation(0.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }

    private void feedbackPanel() {
        feedbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {

                    if (tranView.getVisibility() != View.VISIBLE) {
                        tranView.setVisibility(View.VISIBLE);
                    }

                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {

                    if (tranView.getVisibility() == View.VISIBLE) {
                        tranView.setVisibility(View.GONE);
                    }

                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                if (isfeedShow)
                    rotateFabBackward();
                else
                    rotateFabForward();

                isfeedShow = !isfeedShow;

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSFAFragmentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

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
    public void newLocation(Location newLocation) {

        if (isMapReady && isLocationReady) {
            updateUI(newLocation);

            if (isMapUpdated) {
                isMapUpdated = false;
                try {
                    startDemo(newLocation);
                } catch (Exception e) {
                    Log.e(TAG, "newLocation : " + e.getMessage(), e);
                }
            }
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

    private void startDemo(final Location newLocation) {

        int zoomCount = getZoomCount();


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLocation.getLatitude()
                , newLocation.getLongitude()), zoomCount));

        final Marker myMarker = updateMap(newLocation, zoomCount);

        final int finalZoomCount = zoomCount;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                try {
                    if ((marker != null && myMarker != null) && marker.getTitle().equals(myMarker.getTitle())) {

                        updateMap(newLocation, finalZoomCount);

                        return true;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onMarkerClick: " + e.getMessage(), e);
                }

                return false;
            }
        });

        mClusterManager = new ClusterManager<>(getSFAFragmentActivity(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);

        try {
            readItems();

            mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
                @Override
                public boolean onClusterClick(final Cluster<MyItem> cluster) {
                    LatLngBounds.Builder builder = LatLngBounds.builder();
                    for (ClusterItem item : cluster.getItems()) {
                        builder.include(item.getPosition());
                    }
                    final LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                    return true;
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

    }

    private int getZoomCount() {
        int zoomCount = 2;

        if (!retailerVOList.isEmpty()) {
            if (retailerVOList.get(0).getAmount() > 20.0) {
                zoomCount = 2;
            } else if (retailerVOList.get(0).getAmount() > 15.0 && retailerVOList.get(0).getAmount() < 20.0) {
                zoomCount = 6;
            } else if (retailerVOList.get(0).getAmount() > 10.0 && retailerVOList.get(0).getAmount() < 15.0) {
                zoomCount = 8;
            } else if (retailerVOList.get(0).getAmount() > 5.0 && retailerVOList.get(0).getAmount() < 10.0) {
                zoomCount = 16;
            } else if (retailerVOList.get(0).getAmount() > 1.0 && retailerVOList.get(0).getAmount() < 5.0) {
                zoomCount = 18;
            } else {
                zoomCount = 14;
            }
        }
        return 14;
    }

    private void updateUI(Location newLocation) {
        try {
            Log.e(TAG, "updateUI: lat long " + newLocation.getLatitude() + " | " + newLocation.getLongitude());
            lat = String.valueOf(newLocation.getLatitude());
            lng = String.valueOf(newLocation.getLongitude());

            Globals.getOurInstance().setStrLatitude(lat);
            Globals.getOurInstance().setStrLongitude(lng);

            Globals.getOurInstance().setRetrlatitude(Double.valueOf(retailerLatLong.getLatitude()));
            Globals.getOurInstance().setRettlongitude(Double.valueOf(retailerLatLong.getLongitude()));

            Location startPoint = new Location("locationA");
            startPoint.setLatitude(newLocation.getLatitude());
            startPoint.setLongitude(newLocation.getLongitude());

            Location endPoint = new Location("locationB");
            endPoint.setLatitude(Double.valueOf(retailerLatLong.getLatitude()));
            endPoint.setLongitude(Double.valueOf(retailerLatLong.getLongitude()));
//            distance = startPoint.distanceTo(endPoint);
//
//            Globals.getOurInstance().setStrRetrDistance(distance);
//
//            if (newLocation.getLatitude() > 0.0 && newLocation.getLongitude() > 0.0) {
//                lat = String.valueOf(newLocation.getLatitude());
//                lng = String.valueOf(newLocation.getLongitude());
//                mSfaSharedPref.writeString(PREF_LAST_LAT, lat);
//                mSfaSharedPref.writeString(PREF_LAST_LONG, lng);
//            }

        } catch (Exception e) {
            Log.e(TAG, "updateUI: " + e.getMessage(), e);
        }
    }

    private Marker updateMap(Location newLocation, int zoomLevel) {

        LatLng currentLatLon = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());

        final Marker myMarker = mMap.addMarker(new MarkerOptions().position(currentLatLon).title("YOU")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)));

        mMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(getSFAFragmentActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLon, 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLon)      // Sets the center of the map to location user
                .zoom(zoomLevel)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return myMarker;
    }

    private void readItems() {
        try {
            List<MyItem> myItems = new ArrayList<>();

            for (int i = 0; i < retailerVOList.size(); i++) {
                int count = i + 1;
                String kilometer = String.format(Locale.ENGLISH, "%.1f", retailerVOList.get(i).getAmount()) + " km";

                String retailername = "" + count + ". " + retailerVOList.get(i).getStrRetailerName();

                myItems.add(new MyItem(Double.valueOf(retailerVOList.get(i).getLatitude()),
                        Double.valueOf(retailerVOList.get(i).getLongitude()),
                        retailername, kilometer, retailerVOList.get(i).getStrCustomerCode()));
            }

            setClusterManager(myItems);

        } catch (Exception e) {
            Log.e(TAG, "readItems: " + e.getMessage(), e);
        }
    }

    private void setClusterManager(List<MyItem> myItems) {
        try {
            mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), mMap, mClusterManager));
            mClusterManager.addItems(myItems);

        } catch (Exception e) {
            Log.e(TAG, "readItems: " + e.getMessage(), e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sfaLocation != null)
            sfaLocation.onResume();
        loadFeedbackSpinnerDataFromDB();
        loadDistributorSpinnerDataFromDB();
    }

    private void loadFeedbackSpinnerDataFromDB() {
        ArrayList<String> feedbackName = new ArrayList<>();
        ArrayList<String> feedbackType = new ArrayList<>();
        ArrayList<FeedBackMasterModel> feedBackMasterModelArrayList;

        feedBackMasterModelArrayList = db.getFeedbackList();

        for (int i = 0; i < feedBackMasterModelArrayList.size(); i++) {

            feedbackName.add(feedBackMasterModelArrayList.get(i).getFeedbackName());
            feedbackType.add(feedBackMasterModelArrayList.get(i).getFeedbackType());
        }
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getSFAFragmentActivity(), R.layout.spinner_list_item, feedbackName);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        feedbackSpinner.setAdapter(spinnerArrayAdapter2);

        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFeedBackPos = position;
                selectedFeedbackCode = feedBackMasterModelArrayList.get(selectedFeedBackPos).getFeedbackType();
                selectedFeedbackName = feedBackMasterModelArrayList.get(selectedFeedBackPos).getFeedbackName();
                System.out.println("feedbackSpinner position:" + selectedFeedBackPos + ",selectedFeedbackName:" + selectedFeedbackName + ",selectedFeedbacCode:" + selectedFeedbackCode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        SpinnerSelectedPosition spinnerSelectedPosition = new SpinnerSelectedPosition(getSFAFragmentActivity(), reasonName, feedbackSpinner);
//        spinnerSelectedPosition.setSpinnerPos(new SpinnerSelectedPosition.OnSpinnerCallBack() {
//            @Override
//            public void onSpinnerCallbackPosition(int position) {
//                feedbackPos = position;
//            }
//        });
    }

    private void loadDistributorSpinnerDataFromDB() {

        distributorArrayList = new ArrayList<>();
//        if (db.isProdAvailable()) {
//            distributorArrayList = db.getDistributorList();
//        } else {
//            no_distributors_tv.setVisibility(View.VISIBLE);
//            mRecyclerDistributorInfo.setVisibility(View.GONE);
//        }

        distributorArrayList = db.getUniqueCustomerList();


        ArrayList<String> distributorDropDown = new ArrayList<>();

        for (int i = 0; i < distributorArrayList.size(); i++) {

            distributorDropDown.add(distributorArrayList.get(i).getDistrCode() +"("+ distributorArrayList.get(i).getCmpCode()+")");
        }


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getSFAFragmentActivity(), R.layout.spinner_list_item, distributorDropDown);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        distr_spinner.setAdapter(spinnerArrayAdapter);

        distr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrPos = position;
                selectedDistr = distributorDropDown.get(selectedDistrPos);

                System.out.println("distr_spinner position:" + selectedDistrPos + ",selectedDistr:" + selectedDistr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("distr_spinner noting selected");
            }
        });


//        SpinnerSelectedPosition spinnerSelectedPosition = new SpinnerSelectedPosition(getSFAFragmentActivity(), reasonName, feedbackSpinner);
//        spinnerSelectedPosition.setSpinnerPos(new SpinnerSelectedPosition.OnSpinnerCallBack() {
//            @Override
//            public void onSpinnerCallbackPosition(int position) {
//                feedbackPos = position;
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_camera_img:
                captureFeedbackImg();
                break;

            default:
                break;
        }
    }

    /**
     * check whether camera hardware is available
     *
     * @param context params
     * @return true is available otherwise false
     */
    public boolean isCameraAvailable(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void captureFeedbackImg() {
        Globals.getOurInstance().setStrimage("");
        if (isCameraAvailable(getActivity())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= 24) {

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
                NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
            }

        } else {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    Globals.getOurInstance().getProfileImagePath(getActivity(), fileUri, photofile, true);
                    feedbackImgIv.setVisibility(View.VISIBLE);
                    feedbackImgIv.setImageURI(fileUri);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, "User cancelled image capture", Toast.LENGTH_SHORT);
                } else {
                    // failed to capture image
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, "Sorry! Failed to capture image", Toast.LENGTH_SHORT);

                }
            } else if (requestCode == SELECT_PICTURE && null != data) {

                Uri selectedImageUri = data.getData();

                Globals.getOurInstance().getProfileImagePath(getActivity(), selectedImageUri, photofile, false);
                feedbackImgIv.setVisibility(View.VISIBLE);
                feedbackImgIv.setImageURI(fileUri);

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

    @Override
    public void onStart() {
        if (sfaLocation != null)
            sfaLocation.onStart();

        super.onStart();
    }

    @Override
    public void onPause() {

        if (sfaLocation != null) {
            sfaLocation.onPause();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        if (sfaLocation != null)
            sfaLocation.onStop();

        if (mMap != null)
            mMap.clear();

        super.onStop();
    }
}
