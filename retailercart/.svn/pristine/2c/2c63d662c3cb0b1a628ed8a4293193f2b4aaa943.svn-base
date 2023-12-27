package com.botree.retailerssfa.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.CustomPagerAdapter;
import com.botree.retailerssfa.adapters.MessageAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.interfaces.SyncUpdateInterface;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.support.Globals.NAME_DAY_SUMMARY;
import static com.botree.retailerssfa.support.Globals.NAME_DISTRIBUTOR_INFO;
import static com.botree.retailerssfa.support.Globals.NAME_FEEDBACK;
import static com.botree.retailerssfa.support.Globals.NAME_FOCUS_BRAND_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_MUST_SELL_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_DETAILS;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT_MASTER;
import static com.botree.retailerssfa.support.Globals.NAME_REPORTS_MENU;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME_PRODUCTS_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_TRANSACTION;
import static com.botree.retailerssfa.support.Globals.NAME_UPDATE_LOCATION;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_PURCHASE_ORDER;
import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_QUICK_ACTION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_STOCKIST_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_NAME;

public class DashboardNavigationFragment extends SFAFragment implements
        View.OnClickListener, SyncUpdateInterface {

    private static final int DASHBOARD_MENU = 10;
    private static final int QUICK_ACTION_MENU = 11;
    private ImageView showDownFab;
    private NestedScrollView rootNestedScrollView;
    private RecyclerView recyclerDashboardIcons;
    private int fragmentPosition;
    private RecyclerView recyclerQuickActions;
    private List<QuickViewVo> dashboardIcons = new ArrayList<>();
    private List<QuickViewVo> quickViewVos = new ArrayList<>();
    private SFADatabase db;
    private RecyclerView recyclerMessages;
    private SFASharedPref sfaSharedPref;
    private SFASharedPref preferences;
    private LinearLayout dotsLayout;
    private ViewPager mViewPager;
    private Timer timer;
    private Animation blinkAnim;
    private String userType;
    private List<String> bannerList;
    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // page scrolled not required to check
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // page scrolled not required to check
        }
    };

    public DashboardNavigationFragment() {
        //ignore
    }

    private Sync sync;
    private TextView lastSyncTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = SFADatabase.getInstance(getActivity());
        sync = Sync.getInstance();
        sfaSharedPref = SFASharedPref.getOurInstance();
        preferences = SFASharedPref.getOurInstance();
        userType = sfaSharedPref.readString(SFASharedPref.PREF_USER_TYPE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_navigation, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerDashboardIcons.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        recyclerDashboardIcons.setHasFixedSize(true);

        recyclerQuickActions.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        recyclerQuickActions.setHasFixedSize(true);

        recyclerMessages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerMessages.setHasFixedSize(true);

        loadDashboardMenus();
        DashboardAdapter dashboardAdapter = new DashboardAdapter(DASHBOARD_MENU, loadConfigDashboardMenus());
        recyclerDashboardIcons.setAdapter(dashboardAdapter);

        DashboardAdapter quickActionAdapter = new DashboardAdapter(QUICK_ACTION_MENU, getQuickActionMenu());
        recyclerQuickActions.setAdapter(quickActionAdapter);

        loadBCMessages();
    }

    private void loadBCMessages() {
        List<MessageModel> messageModelList;
        messageModelList = db.getAllMessages(preferences.readString(PREF_DISTRCODE),
                preferences.readString(PREF_SALESMANCODE), 3, true);

        MessageAdapter messageAdapter = new MessageAdapter(getActivity(), messageModelList);
        recyclerMessages.setAdapter(messageAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getSFAFragmentActivity()).showBottomNevigation();
        loadPager();

        DashboardAdapter quickActionAdapter = new DashboardAdapter(QUICK_ACTION_MENU, getQuickActionMenu());
        recyclerQuickActions.setAdapter(quickActionAdapter);

        if (rootNestedScrollView.canScrollVertically(1) || rootNestedScrollView.canScrollVertically(-1)) {
            showDownFab.startAnimation(blinkAnim);
            showDownFab.setVisibility(View.VISIBLE);
        } else {
            showDownFab.setVisibility(View.GONE);
        }

    }

    private List<QuickViewVo> getQuickActionMenu() {

        quickViewVos.clear();
        quickViewVos = AppUtils.getOurInstance().getQuickMenuList();

        List<QuickViewVo> quickViewVoList = new ArrayList<>();
        List<QuickViewVo> allActiveStatusList;
        if (!sfaSharedPref.readBoolean(PREF_QUICK_ACTION)) {
            allActiveStatusList = db.getActiveQuickMenus();
            quickViewVoList = new ArrayList<>();
            for (int i = 0; i < quickViewVos.size(); i++) {
                for (QuickViewVo quickViewVo1 : allActiveStatusList) {
                    if (quickViewVos.get(i).getName().equals(quickViewVo1.getName()) && quickViewVo1.isActiveIcon())
                        quickViewVoList.add(quickViewVos.get(i));
                }
            }
            quickViewVoList.add(new QuickViewVo("Add/Remove", R.drawable.icon_add));
        } else {
            int menucount = Integer.parseInt(preferences.readString(SFASharedPref.PREF_AUTO_QUICK_ACCESS_VALUE, "6"));
            allActiveStatusList = db.getAllAutoQuickMenus(menucount);

            for (int i = 0; i < allActiveStatusList.size(); i++) {
                quickViewVoList.addAll(getQuickViewList(allActiveStatusList, i));
            }
        }
        return quickViewVoList;
    }

    private List<QuickViewVo> getQuickViewList(List<QuickViewVo> allActiveStatusList, int i) {
        List<QuickViewVo> quickViewVoList = new ArrayList<>();
        for (QuickViewVo quickViewVo1 : quickViewVos) {
            if (allActiveStatusList.get(i).getName().equals(quickViewVo1.getName())) {
                quickViewVoList.add(quickViewVo1);
            }
        }
        return quickViewVoList;
    }

    private void loadDashboardMenus() {
        dashboardIcons = new ArrayList<>();
//        dashboardIcons.add(new QuickViewVo(NAME_TRANSACTION, R.drawable.order_booking));//icon_dashboard

        dashboardIcons.add(new QuickViewVo(NAME_ORDER_BOOKING, R.drawable.order_booking));
        dashboardIcons.add(new QuickViewVo(NAME_ORDER_DETAILS, R.drawable.cart));
        dashboardIcons.add(new QuickViewVo(NAME_DAY_SUMMARY, R.drawable.icon_day_summary));//ic_insert_chart
        dashboardIcons.add(new QuickViewVo(NAME_PRODUCT, R.drawable.icon_product));
        dashboardIcons.add(new QuickViewVo(NAME_SCHEME, R.drawable.ic_scheme_24dp));
        dashboardIcons.add(new QuickViewVo(NAME_FEEDBACK, R.drawable.send));
        dashboardIcons.add(new QuickViewVo(NAME_DISTRIBUTOR_INFO, R.drawable.ic_distributor));//ic_insert_chart
        dashboardIcons.add(new QuickViewVo(NAME_UPDATE_LOCATION, R.drawable.ic_my_location));//ic_insert_chart
        dashboardIcons.add(new QuickViewVo(NAME_REPORTS_MENU, R.drawable.ic_reports));//ic_insert_chart

    }

    private List<QuickViewVo> loadConfigDashboardMenus() {
        ArrayList<QuickViewVo> configDashboardMenus = new ArrayList<>();
        List<ScreenConfig> screenConfigList = DataManager.getInstance().getScreenConfigArrayList();
        for (ScreenConfig screenConfig : screenConfigList) {
            if (isScreenVisibleTrue(screenConfig, 1, 1)) {
                configDashboardMenus.add(dashboardIcons.get(0));//Other Reports
            } else if (isScreenVisibleTrue(screenConfig, 1, 2)) {
                configDashboardMenus.add(dashboardIcons.get(1));//Distributor Purchase  Receipt
            } else if (isScreenVisibleTrue(screenConfig, 1, 3)) {
                configDashboardMenus.add(dashboardIcons.get(2));//Distributor Purchase Return
            } else if (isScreenVisibleTrue(screenConfig, 1, 4)) {
                configDashboardMenus.add(dashboardIcons.get(3));//Distributor opening stock
            } else if (isScreenVisibleTrue(screenConfig, 1, 5)) {
                configDashboardMenus.add(dashboardIcons.get(4));//Distributor Purchase Order
            } else if (isScreenVisibleTrue(screenConfig, 1, 6)) {
                configDashboardMenus.add(dashboardIcons.get(5));//Distributor Purchase Order
            } else if (isScreenVisibleTrue(screenConfig, 1, 7)) {
                configDashboardMenus.add(dashboardIcons.get(6));//Distributor Purchase Order
            } else if (isScreenVisibleTrue(screenConfig, 1, 8)) {
                configDashboardMenus.add(dashboardIcons.get(7));//Distributor Purchase Order
            } else if (isScreenVisibleTrue(screenConfig, 1, 9)) {
                configDashboardMenus.add(dashboardIcons.get(8));//Distributor Purchase Order
            }
        }

//        configDashboardMenus.add(dashboardIcons.get(0)); // Masters
//        configDashboardMenus.add(dashboardIcons.get(1)); // Transaction
//        configDashboardMenus.add(dashboardIcons.get(2)); // Reports
//        configDashboardMenus.add(dashboardIcons.get(3)); // Reports
//        configDashboardMenus.add(dashboardIcons.get(4)); // Reportsi
//        configDashboardMenus.add(dashboardIcons.get(5)); // Reports
//        configDashboardMenus.add(dashboardIcons.get(6)); // Reports
//        configDashboardMenus.add(dashboardIcons.get(7)); // Reports
        return configDashboardMenus;
    }

    private boolean isScreenVisibleTrue(ScreenConfig screenConfig, int moduleNo, int screenNo) {
        return screenConfig.getModuleNo() == moduleNo && screenConfig.getScreenNo() == screenNo
                && screenConfig.getChecked().equalsIgnoreCase("Y");
    }

    private void initViews(View view) {
        TextView currentDateTxt = view.findViewById(R.id.current_date_txt);
        TextView userNameTxt = view.findViewById(R.id.user_name_txt);
        TextView beatNameTxt = view.findViewById(R.id.today_beat_name_txt);
        TextView coverageCountTxt = view.findViewById(R.id.noof_outlet_count_txt);
        lastSyncTxt = view.findViewById(R.id.last_sync_txt);

        if (getSFAFragmentActivity().getSupportActionBar() != null)
            getSFAFragmentActivity().getSupportActionBar().show();

        rootNestedScrollView = view.findViewById(R.id.dash_root_nestedscroll);
        showDownFab = view.findViewById(R.id.show_down_fab);
        mViewPager = view.findViewById(R.id.adv_pager);
        dotsLayout = view.findViewById(R.id.home_layoutDots);

        recyclerDashboardIcons = view.findViewById(R.id.recyclerDashboardIcons);
        recyclerQuickActions = view.findViewById(R.id.recyclerQuickActions);
        recyclerMessages = view.findViewById(R.id.recyclerMessages);

        recyclerMessages.setNestedScrollingEnabled(false);
        recyclerQuickActions.setNestedScrollingEnabled(false);
        recyclerDashboardIcons.setNestedScrollingEnabled(false);

        currentDateTxt.setText(DateUtil.getCurrentDateMonthYear());

        blinkAnim = AppUtils.getOurInstance().loadBlinkAnim();

        showDownFab.setOnClickListener(this);
        rootNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // We take the last son in the scrollview
                View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (diff == 0) {
                    // do stuff
                    showDownFab.clearAnimation();
                    showDownFab.setVisibility(View.GONE);
                } else {
                    showDownFab.startAnimation(blinkAnim);
                    showDownFab.setVisibility(View.VISIBLE);
                }
            }
        });

        setUserBeatCoverageDetail(userNameTxt, beatNameTxt, coverageCountTxt);

        MainActivity ma = ((MainActivity) getActivity());
        if (null != ma) {
            ma.setOnSyncStatusUpdateListener(this);
            ma.updateSyncDateTime();
        }
    }

    private void setUserBeatCoverageDetail(TextView userNameTxt, TextView beatNameTxt, TextView coverageCountTxt) {
        try {

//            if (getRouteCode() != null && !getRouteCode().isEmpty()) {
//                Map<String, String> map = db.getTodayBeat(getRouteCode());
//                coverageCountTxt.setText(String.valueOf(map.get("totCount")));
//                beatNameTxt.setText(String.valueOf(map.get("toDayRoute")));
//            } else {
//                coverageCountTxt.setText("-");
//                beatNameTxt.setText(getResources().getString(R.string.all_route));
//
//            }
            if (USER_TYPE_ISR.equalsIgnoreCase(userType)) {
                userNameTxt.setText(MessageFormat.format(" {0} - {1}", preferences.readString(PREF_USER_NAME), preferences.readString(PREF_STOCKIST_NAME)));

            } else {
                userNameTxt.setText(preferences.readString(PREF_USER_NAME));

            }
        } catch (Exception e) {
            Log.e(TAG, "initViews: " + e.getMessage(), e);
        }
    }


    private String getRouteCode() {

        return db.getTodayBeatRouteCode(sfaSharedPref.readString(PREF_DISTRCODE),
                sfaSharedPref.readString(PREF_SALESMANCODE), String.valueOf(true));
    }


    private void loadPager() {

//        bannerList = db.getBannerList();
        List<String> companyList = db.getAllMappedCompany();

        bannerList = new ArrayList<>();
        for (String image : companyList) {
            bannerList.add("/" + image + "/jpeg");
            for (int i = 1; i <= 5; i++) {
                bannerList.add("/" + image + i + "/jpeg");
            }
        }

        loadBannerList();

    }

    private void addBottomDots(int currentPage) {
        try {
            TextView[] dots = new TextView[bannerList.size()];
            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getActivity());
                dots[i].setText(fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.color_blue_maps_line));
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.ob_mustsell));
        } catch (Exception e) {

            Log.e(TAG, "addBottomDots: " + e.getMessage(), e);
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.show_down_fab) {
            rootNestedScrollView.post(new Runnable() {
                @Override
                public void run() {
                    rootNestedScrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mViewPager != null)
            mViewPager.removeAllViews();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null)
            timer.cancel();
        if (mViewPager != null)
            mViewPager.removeAllViews();

        final MainActivity ma = ((MainActivity) getActivity());
        if (null != ma)
            ma.setOnSyncStatusUpdateListener(null);
    }

    private void callFragmentBasedOnPosition(String name) {
        ESFAFragTags fragTags = null;

        switch (name) {

            case NAME_TRANSACTION:
                fragTags = ESFAFragTags.TRANSACTION;
                fragmentPosition = 1;
                break;

            case NAME_FEEDBACK:
                Intent intent_feedback = new Intent(getSFAFragmentActivity(), FeedbackActivity.class);
                intent_feedback.putExtra("ScreenName", getResources().getString(R.string.screen_name_feedback));
                startActivity(intent_feedback);

                break;

            case NAME_ORDER_DETAILS:
                Intent intent = new Intent(getSFAFragmentActivity(), PreviousOrderBookingReportActivity.class);
                startActivity(intent);
                break;

            case NAME_DAY_SUMMARY:
                fragTags = ESFAFragTags.DAY_SUMMARY;
                fragmentPosition = 3;
                break;

            case NAME_UPDATE_LOCATION:
                fragTags = ESFAFragTags.UPDATE_LOCATION;
                fragmentPosition = 4;
                break;

            case NAME_DISTRIBUTOR_INFO:
                fragTags = ESFAFragTags.DISTRIBUTOR_INFO;
                fragmentPosition = 5;
                break;

            case NAME_ORDER_BOOKING:
                fragTags = ESFAFragTags.ORDER_BOOKING;
                break;

            case NAME_SCHEME:
                //Intent to RetailerSchemeUtilizationActivity if schemedistr list less than 2
                if (db.getSchemeDistrListCount() > 1) {
                    Intent schemeIntent = new Intent(getSFAFragmentActivity(), SchemeDistributorListActivity.class);
                    schemeIntent.putExtra("ScreenName", getResources().getString(R.string.screen_name_scheme));
                    startActivity(schemeIntent);
                } else {
                    ArrayList<Distributor> distributors = db.getSchemeDistrList();
                    String cmp_code = distributors.get(0).getCompanyCode();
                    String distr_code = distributors.get(0).getDistributorCode();
                    Intent in = new Intent(getSFAFragmentActivity(), RetailerSchemeUtilizationActivity.class);
                    in.putExtra("screen", getResources().getString(R.string.screen_name_scheme));
                    in.putExtra("cmp_code", cmp_code);
                    in.putExtra("distr_code", distr_code);
                    startActivity(in);
                }
                break;

            case NAME_PRODUCT:
                Intent productIntent = new Intent(getSFAFragmentActivity(), ProductMasterActivity.class);
                startActivity(productIntent);
                break;

            default:
                break;
        }
        callFragmentByTag(fragTags, name);
    }

    private void callFragmentByTag(ESFAFragTags fragTags, String fragmentName) {
        if (fragTags != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("fragmentPosition", fragmentPosition);
            bundle.putString("fragmentName", fragmentName);
            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            sfaFragManger.clearBackStack(getSFAFragmentActivity());
            sfaFragManger.updateFragment(fragTags, true, getSFAFragmentActivity(), bundle);
        }
    }

    @Override
    public void onUpdate(String status) {
        if (null != lastSyncTxt)
            lastSyncTxt.setText(status);
    }

    public class DashboardAdapter extends RecyclerView.Adapter<DashViewHolder> {
        private final List<QuickViewVo> quickViewVosList;
        private final int menuType;

        DashboardAdapter(int menuType, List<QuickViewVo> quickViewVos) {
            this.quickViewVosList = quickViewVos;
            this.menuType = menuType;
        }

        @NonNull
        @Override
        public DashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_navigation_item_view, parent, false);
            return new DashboardNavigationFragment.DashViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final DashViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.ivIcon.setImageResource(quickViewVosList.get(position).getIcons());
            if (quickViewVosList.get(position).getColor() != null)
                holder.ivIcon.setColorFilter(quickViewVosList.get(position).getColor());
            holder.tvName.setText(quickViewVosList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onQuickAccessMenuClick(position);
                }
            });

        }

        private void onQuickAccessMenuClick(int position) {
            if (menuType == DASHBOARD_MENU)
                callFragmentBasedOnPosition(quickViewVosList.get(position).getName());
            else {
            }
        }

        private void menuClickSubMethod(int position) {
            if (isScreenTypeTrue(position, NAME_PRODUCT_MASTER)) {
                Intent in = new Intent(getActivity(), ProductSchFocusMustSellMasterActivity.class);
                in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_PRODUCT_MASTER);
                startActivity(in);
                getSFAFragmentActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
            } else if (isScreenTypeTrue(position, NAME_SCHEME_PRODUCTS_REPORT)) {
                Intent in = new Intent(getActivity(), ProductSchFocusMustSellMasterActivity.class);
                in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_SCHEME_PRODUCTS_REPORT);
                startActivity(in);
                getSFAFragmentActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
            } else if (isScreenTypeTrue(position, NAME_FOCUS_BRAND_MASTER_REPORT)) {
                Intent in = new Intent(getActivity(), ProductSchFocusMustSellMasterActivity.class);
                in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_FOCUS_BRAND_MASTER_REPORT);
                startActivity(in);
                getSFAFragmentActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
            } else if (isScreenTypeTrue(position, NAME_MUST_SELL_MASTER_REPORT)) {
                Intent in = new Intent(getActivity(), ProductSchFocusMustSellMasterActivity.class);
                in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_MUST_SELL_MASTER_REPORT);
                startActivity(in);
                getSFAFragmentActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
            }
        }


        private boolean isScreenTypeTrue(@SuppressLint("RecyclerView") int position, String nameTargetVsAchievedScreen) {
            return quickViewVosList.get(position).getName().equalsIgnoreCase(nameTargetVsAchievedScreen);
        }

        @Override
        public int getItemCount() {
            return quickViewVosList.size();
        }

    }

    private class DashViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;

        private DashViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    private void loadBannerList() {
        List<String> tempBanner = new ArrayList<>();

        for (int i = 0; i < bannerList.size(); i++) {
            int finalI = i;
            DataManager.getInstance().getBanner(getResources().getString(R.string.BASE_URL_RELEASE) + ApiClient.REDIRECT, ApiClient.GET_BANNER_IMAGE + bannerList.get(i), new DataManager.APICallBack() {
                @Override
                public void onAPICallback(final String message, boolean isSuccess, AppConstant.RequestType requestType) {
                    if (isSuccess) {
                        tempBanner.add(ApiClient.REDIRECT + ApiClient.GET_BANNER_IMAGE + bannerList.get(finalI));
                    }

                    if (finalI == bannerList.size() - 1) {
                        bannerList.clear();
                        bannerList.addAll(tempBanner);
                        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getSFAFragmentActivity(), bannerList);

                        mViewPager.setAdapter(mCustomPagerAdapter);
                        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (bannerList.size() == 0) {
                                    mViewPager.post(() -> mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1)));
                                } else {
                                    mViewPager.post(() -> mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % bannerList.size()));
                                }
                            }
                        };
                        timer = new Timer();
                        timer.schedule(timerTask, 4000, 4000);

                        addBottomDots(0);
                    }
                }
            });
        }
    }


//    public static class AsyncLoadBanner extends AsyncTask<Void, Void, Boolean> {
//
//        private final DashboardNavigationFragment activity;
//        boolean boolResponse = false;
//        private WeakReference<DashboardNavigationFragment> activityReference;
//
//        public AsyncLoadBanner(DashboardNavigationFragment context) {
//            activityReference = new WeakReference<>(context);
//            activity = activityReference.get();
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                List<>
//                activity.orderBookingVOList = new ArrayList<>();
//                activity.mustSellProduct = new ArrayList<>();
//                activity.focusBrandProduct = new ArrayList<>();
//
//                activity.orderNoList = activity.db.fetchAllOrderNos();
//
//                activity.orderBookingVOList = activity.db.fetchAllProducts(activity.preferences.readString(PREF_DISTRCODE),
//                        activity.preferences.readString(PREF_SALESMANCODE), activity.preferences.readString(PREF_ROUTECODE),
//                        "");
//
//                loadPreviousOrderProducts();
//
//                if (!activity.orderBookingVOList.isEmpty()) {
////                    activity.mustSellProduct = activity.db.getMustsellProduuct(activity.retailerCode, masterTableName);
//
////                    activity.focusBrandProduct = activity.db.getFocusBrandProducts(activity.retailerCode, masterTableName);
////
//                    activity.schemeProductDetail = activity.db.getRetailerSchemeProducts();
////
////                    activity.mustSellBilledProduct = activity.db.getAllMustSellBilledProducts(activity.preferences.readString(PREF_CMP_CODE), activity.preferences.readString(PREF_DISTRCODE), activity.retailerCode);
////
////                    activity.top10Products = activity.db.getTop10SKUList(activity.preferences.readString(PREF_CMP_CODE), activity.distStateCode);
//                    activity.orderBookingVOList = activity.mOrderSupportUtil.mapFilteredProducts(activity.orderBookingVOList, activity.mustSellProduct, activity.mustSellBilledProduct, activity.focusBrandProduct, activity.schemeProductDetail, activity.top10Products);
////
////                    loadMustSellQtyProducts(activity.orderBookingVOList);
//
//                    for (OrderBookingVO orderBookingVO : activity.orderBookingVOList) {
//                        List<SchemeModel> schemeModelList = new ArrayList<>();
//                        for (SchemeModel schemeModel : activity.schemeProductDetail) {
//                            if (orderBookingVO.getProdCode().equalsIgnoreCase(schemeModel.getProductCode())) {
//                                schemeModelList.add(schemeModel);
//                            }
//                        }
//                        orderBookingVO.setSchemeModelList(schemeModelList);
//                    }
//
//                    for (OrderBookingVO orderBookingVO : activity.orderBookingVOList) {
//
//                        String prodType = "";
//                        if (orderBookingVO.getFocusCategory().equalsIgnoreCase(FOCUS_BRAND)
//                                && orderBookingVO.getMustcategory().equalsIgnoreCase(MUST_SELL)
//                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
//                            prodType = "F,M,S";
//                            orderBookingVO.setProdType(prodType);
//
//                        } else if (orderBookingVO.getFocusCategory().equalsIgnoreCase(FOCUS_BRAND)
//                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
//                            prodType = "F,S";
//                            orderBookingVO.setProdType(prodType);
//
//                        } else if (orderBookingVO.getMustcategory().equalsIgnoreCase(MUST_SELL)
//                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
//                            prodType = "M,S";
//                            orderBookingVO.setProdType(prodType);
//
//                        } else if (orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
//                            prodType = "S";
//                            orderBookingVO.setProdType(prodType);
//
//                        } else {
//                            prodType = "N";
//                            orderBookingVO.setProdType(prodType);
//                        }
//
//                        if (!orderBookingVO.getSchemeModelList().isEmpty()) {
//                            List<SchemeModel> schemeslablist = activity.db.getSchemeSlabDetails(orderBookingVO.getSchemeModelList().get(0).getSchemeCode());
//                            orderBookingVO.setSchemeslablist(schemeslablist);
//                        }
//                    }
//                }
//                sequencingArrayList();
//                sequencingQuantityArrayList();
//
//                Collections.sort(activity.orderBookingVOList, activity.new SortByMustSellFocus());
//                loadPreviousOrderQtyStock();
//
//
//            } catch (Exception e) {
//                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
//            }
//            return boolResponse;
//        }
//
//        private void loadPreviousOrderQtyStock() {
//            // capture previous order qty and stocks
//            if (activity.orderBookingVOList.get(0).getOrderInvoiceNo() != null &&
//                    !activity.orderBookingVOList.get(0).getOrderInvoiceNo().isEmpty()) {
//                vsSevices = activity.db.fetchPreviousOrderQtyDetails(
//                        activity.preferences.readString(PREF_DISTRCODE),
//                        activity.preferences.readString(PREF_SALESMANCODE),
//                        "",
//                        activity.orderBookingVOList.get(0).getOrderInvoiceNo());
//
//                Globals.getOurInstance().setPreviousOrderQty(this.vsSevices);
//            }
//        }
//
//        /**
//         * Set the sequencing based on quantity
//         */
//        private void sequencingQuantityArrayList() {
//
//            for (int i = 0; i < activity.orderBookingVOList.size(); i++) {
//                if (activity.orderBookingVOList.get(i).getQuantity() > 0) {
//                    activity.orderBookingVOList.get(i).setMustSellFocusSort(0);
//                }
//            }
//        }
//
//        private void sequencingArrayList() {
//            String configValue = activity.db.getConfigDataBasedOnName(CONFIG_PRODUCT_PRIORITY);
//            if (configValue != null && !configValue.isEmpty()) {
//                final String[] arrayOfString = configValue.trim().split(",");
//                int k = 0;
//                for (String anArrayOfString : arrayOfString) {
//                    k++;
//                    for (int i = 0; i < activity.orderBookingVOList.size(); i++) {
//                        if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getProductStatus())
//                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
//                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);
//
//                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getMustcategory())
//                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
//                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);
//
//                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getFocusCategory())
//                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
//                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);
//
//                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getTop10ProductCategory())
//                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
//                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);
//
//                        } else if (anArrayOfString.trim().equalsIgnoreCase("Others")
//                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
//                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);
//
//                        }
//                    }
//                }
//            }
//        }
//
//        private void loadMustSellQtyProducts(List<OrderBookingVO> orderBookingVOList) {
//            try {
//
//                for (OrderBookingVO bookingVO : orderBookingVOList) {
//
//                    if (bookingVO.getQuantity() == 0 && bookingVO.getMustSellQty() > 0) {
//
//                        double currentQty = bookingVO.getMustSellQty();
//                        bookingVO.setQuantity(currentQty);
//                        bookingVO.setTotQty(activity.mOrderSupportUtil.calcUomConversionQty(bookingVO, bookingVO.getBaseUOM(), currentQty));
//                        // get the conversion factor and calculate with selling price and
//                        // multiplied with current quantity
//                        double orderValue = activity.mOrderSupportUtil.calculateLineOrderValue(bookingVO, bookingVO.getBaseUOM(), currentQty);
//
//                        bookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
//                        bookingVO.setlGrossAmt(BigDecimal.valueOf(orderValue));
//                        bookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(activity.mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, bookingVO.getPrimaryDisc())));
//
//                    }
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "loadMustSellQtyProducts: " + e.getMessage(), e);
//            }
//        }
//
//        private void loadPreviousOrderProducts() {
//            try {
//                if (activity.isPreviousOrder) {
//                    activity.previousOrderList = activity.db.fetchPreviousOrderDetails(activity.preferences.readString(PREF_DISTRCODE),
//                            activity.preferences.readString(PREF_SALESMANCODE), "");
//                    for (OrderBookingVO allProdList : activity.orderBookingVOList) {
//                        for (OrderBookingVO prevOrder : activity.previousOrderList) {
//
//                            if (allProdList.getProdCode().equalsIgnoreCase(prevOrder.getProdCode())) {
//
//                                allProdList.setQuantity(prevOrder.getQuantity());
//                                allProdList.setUomId(prevOrder.getUomId());
//                                double currentQty = prevOrder.getQuantity();
//                                double orderValue = activity.mOrderSupportUtil.calculateLineOrderValue(allProdList, prevOrder.getUomId(), currentQty);
//                                allProdList.setOrderValue(BigDecimal.valueOf(orderValue));
//                                allProdList.setPrimDiscOrderValue(BigDecimal.valueOf(activity.mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, allProdList.getPrimaryDisc())));
//                                allProdList.setTotQty(activity.mOrderSupportUtil.calcUomConversionQty(allProdList, prevOrder.getUomId(), currentQty));
//
//                                List<OrderBookingVO> orderBooking = new ArrayList<>(activity.db.getUom(allProdList.getUomGroupId(), TABLE_UOM_MASTER));
//
//                                activity.uomIdSpinner = activity.mOrderSupportUtil.getUomIdSpinnerList(orderBooking);
//                            }
//
//                        }
//                    }
//                    /* sort based on must sell focus brand */
//                    Collections.sort(activity.orderBookingVOList, activity.new SortByQuantity());
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            activity.showProgressDialog(activity.getActivity(), activity.getResources().getString(R.string.MSG_LOADING));
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            activity.cancleProgressDialog();
//            activity.loadAdapterData();
//            loadBrandsSpinner();
//            Log.e("orderBookingVOList", "" + activity.orderBookingVOList.size());
//
//        }
//
//        private void loadBrandsSpinner() {
//            ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(activity.getSFAFragmentActivity(), R.layout.spinner_list_item, activity.brandsList);
//            brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            activity.mainBrandSpinner.setAdapter(brandAdapter);
//
//            activity.mainBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
//
//                    activity.brandValue = activity.mainBrandSpinner.getSelectedItem().toString();
//                    if (!activity.brandDataList.isEmpty() && pos - 1 >= 0)
//                        activity.brandCode = activity.brandDataList.get(pos - 1).getBrandCode();
//                    activity.categoryValue = ALL_CATEGORY;
//
//                    activity.setApplyFilter();
//                    activity.isFilterDialogShowing = false;
//                    activity.clearTempFilterFields();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                    //default implementation ignored
//                }
//            });
//        }
//    }
}
