package com.botree.retailerssfa.main;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.CommonViewPagerAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.MTDModel;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.support.CustomViewPager;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.SFASharedPref;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.botree.retailerssfa.support.Globals.NAME_ADD_OUTLET;
import static com.botree.retailerssfa.support.Globals.NAME_APPROVALS_DASHBOARD;
import static com.botree.retailerssfa.support.Globals.NAME_DISTRIBUTOR_PURCHASE_ORDER;
import static com.botree.retailerssfa.support.Globals.NAME_DISTRIBUTOR_PURCHASE_RECEIPT;
import static com.botree.retailerssfa.support.Globals.NAME_DISTRIBUTOR_PURCHASE_RETURN;
import static com.botree.retailerssfa.support.Globals.NAME_OPENING_STOCK_DASHBOARD;
import static com.botree.retailerssfa.support.Globals.NAME_OTHER_REPORTS;
import static com.botree.retailerssfa.support.Globals.NAME_STOCK_LEDGER_REPORT;
import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_QUICK_ACTION;

/**
 *
 */

public class HomeFragment extends SFAFragment {


    private static final String FRAGMENT_NAME = "fragmentName";
    private static final String SALES_LIST = "salesList";
    private static final int QUICK_ACTION_MENU = 11;

    private CustomViewPager mViewPager;
    private int pagerPosition = 0;
    private TextView[] dots;
    private LinearLayout mLayoutPageIndicator;
    private SFADatabase db;
    private SFASharedPref sfaSharedPref;

    private List<QuickViewVo> quickViewVos = new ArrayList<>();
    private List<QuickViewVo> dashboardIcons = new ArrayList<>();

    List<QuickViewVo> quickViewVoList = new ArrayList<>();


    TextView emptyText;
    RecyclerView recyclerView;
    DashboardAdapter quickActionAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SFADatabase.getInstance(getActivity());
        sfaSharedPref = SFASharedPref.getOurInstance();
        Log.e("onCreate", "called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addBottomDots();
                dots[0].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.ob_mustsell));
                setViewPager();
            }
        }, 200);

        Log.e("onViewCreated", "called");

    }

    private void initialize(View view) {
        mLayoutPageIndicator = view.findViewById(R.id.layoutPageIndicator);
        mViewPager = view.findViewById(R.id.viewPager);
        emptyText = view.findViewById(R.id.empty_tag_txt);
        recyclerView = view.findViewById(R.id.home_recyclerview);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        loadAdapter();

    }

    private void loadAdapter() {

        loadDashboardMenus();
        getQuickActionMenu();

        quickActionAdapter = new DashboardAdapter(QUICK_ACTION_MENU, quickViewVoList);
        recyclerView.setAdapter(quickActionAdapter);

        if (!quickActionAdapter.getQuickViewVosList().isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);


        } else {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getQuickActionMenu();

        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
            if (!quickActionAdapter.getQuickViewVosList().isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
            }
        }
    }

    private void loadDashboardMenus() {

        List<Integer> listofColors = AppUtils.getOurInstance().getColorsList();

        dashboardIcons = new ArrayList<>();
        dashboardIcons.add(new QuickViewVo(NAME_ADD_OUTLET, R.drawable.icon_add_outlet));
        dashboardIcons.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_ORDER, R.drawable.cart));
        dashboardIcons.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_RECEIPT, R.drawable.ic_receipt));
        dashboardIcons.add(new QuickViewVo(NAME_DISTRIBUTOR_PURCHASE_RETURN, R.drawable.ic_purchase_return));
        dashboardIcons.add(new QuickViewVo(NAME_APPROVALS_DASHBOARD, R.drawable.ic_approvals));
        dashboardIcons.add(new QuickViewVo(NAME_OPENING_STOCK_DASHBOARD, R.drawable.order_booking));
        dashboardIcons.add(new QuickViewVo(NAME_OTHER_REPORTS, R.drawable.ic_productive_24dp));
        dashboardIcons.add(new QuickViewVo(NAME_STOCK_LEDGER_REPORT, R.drawable.icon_time_spent));

        for (int i = 0; i < dashboardIcons.size(); i++) {
            dashboardIcons.get(i).setColor(listofColors.get(i % (listofColors.size() - 1)));
        }
    }


    private List<QuickViewVo> getQuickActionMenu() {

        quickViewVos.clear();
        quickViewVoList.clear();
        quickViewVos.addAll(dashboardIcons);


        List<QuickViewVo> allActiveStatusList;
        if (!sfaSharedPref.readBoolean(PREF_QUICK_ACTION)) {
            allActiveStatusList = db.getActiveQuickMenus();
            for (int i = 0; i < quickViewVos.size(); i++) {
                for (QuickViewVo quickViewVo1 : allActiveStatusList) {
                    if (quickViewVos.get(i).getName().equals(quickViewVo1.getName()) && quickViewVo1.isActiveIcon())
                        quickViewVoList.add(quickViewVos.get(i));
                }
            }
        } else {
            int menucount = Integer.parseInt(sfaSharedPref.readString(SFASharedPref.PREF_AUTO_QUICK_ACCESS_VALUE, "6"));
            allActiveStatusList = db.getAllAutoQuickMenus(menucount);

            for (int i = 0; i < allActiveStatusList.size(); i++) {
                quickViewVoList.addAll(getQuickViewList(allActiveStatusList, i));
            }
        }
        return quickViewVoList;
    }


    private List<QuickViewVo> getQuickViewList(List<QuickViewVo> allActiveStatusList, int i) {
        List<QuickViewVo> quickViewList = new ArrayList<>();
        for (QuickViewVo quickViewVo1 : quickViewVos) {
            if (allActiveStatusList.get(i).getName().equals(quickViewVo1.getName())) {
                quickViewList.add(quickViewVo1);
            }
        }
        return quickViewList;
    }

    private void setViewPager() {


        ArrayList<MTDModel> monthSalesList = new ArrayList<>();

        MTDModel mtdModel = new MTDModel();
        mtdModel.setSalesValues(12.0);
        mtdModel.setCurrentSalesValue(11.0);
        mtdModel.setReportDay(1);
        mtdModel.setWeeks("");

        MTDModel mtdModel0 = new MTDModel();
        mtdModel0.setSalesValues(0.0);
        mtdModel0.setCurrentSalesValue(0.0);
        mtdModel0.setReportDay(2);
        mtdModel0.setWeeks("");

        MTDModel mtdModel1 = new MTDModel();
        mtdModel1.setSalesValues(0.0);
        mtdModel1.setCurrentSalesValue(0.0);
        mtdModel1.setReportDay(3);
        mtdModel1.setWeeks("");

        MTDModel mtdModel2 = new MTDModel();
        mtdModel2.setSalesValues(12.0);
        mtdModel2.setCurrentSalesValue(9.0);
        mtdModel2.setReportDay(4);
        mtdModel2.setWeeks("");

        MTDModel mtdModel3 = new MTDModel();
        mtdModel3.setSalesValues(102.0);
        mtdModel3.setCurrentSalesValue(91.0);
        mtdModel3.setReportDay(5);
        mtdModel3.setWeeks("");

        MTDModel mtdModel4 = new MTDModel();
        mtdModel4.setSalesValues(0.0);
        mtdModel4.setCurrentSalesValue(0.0);
        mtdModel4.setReportDay(6);
        mtdModel4.setWeeks("");


        MTDModel mtdModel5 = new MTDModel();
        mtdModel5.setSalesValues(0.0);
        mtdModel5.setCurrentSalesValue(0.0);
        mtdModel5.setReportDay(7);
        mtdModel5.setWeeks("");

        MTDModel mtdModel6 = new MTDModel();
        mtdModel6.setSalesValues(0.0);
        mtdModel6.setCurrentSalesValue(0.0);
        mtdModel6.setReportDay(8);
        mtdModel6.setWeeks("");

        MTDModel mtdModel7 = new MTDModel();
        mtdModel7.setSalesValues(0.0);
        mtdModel7.setCurrentSalesValue(0.0);
        mtdModel7.setReportDay(9);
        mtdModel7.setWeeks("");

        MTDModel mtdModel8 = new MTDModel();
        mtdModel8.setSalesValues(0.0);
        mtdModel8.setCurrentSalesValue(0.0);
        mtdModel8.setReportDay(10);
        mtdModel8.setWeeks("");

        monthSalesList.add(mtdModel);
        monthSalesList.add(mtdModel0);
        monthSalesList.add(mtdModel1);
        monthSalesList.add(mtdModel2);
        monthSalesList.add(mtdModel3);
        monthSalesList.add(mtdModel4);
        monthSalesList.add(mtdModel5);
        monthSalesList.add(mtdModel6);
        monthSalesList.add(mtdModel7);
        monthSalesList.add(mtdModel8);

        Bundle bundle1 = new Bundle();
        bundle1.putString(FRAGMENT_NAME, "MTDDayWise");
        bundle1.putParcelableArrayList(SALES_LIST, (ArrayList<MTDModel>) monthSalesList);


        ArrayList<MTDModel> weekSalesList = new ArrayList<>();

        MTDModel model = new MTDModel();
        model.setSalesValues(102.0);
        model.setCurrentSalesValue(9.0);
        model.setReportDay(0);
        model.setWeeks("1");

        MTDModel model1 = new MTDModel();
        model1.setSalesValues(1.0);
        model1.setCurrentSalesValue(1.0);
        model1.setReportDay(0);
        model1.setWeeks("2");

        MTDModel model2 = new MTDModel();
        model2.setSalesValues(0.0);
        model2.setCurrentSalesValue(0.0);
        model2.setReportDay(0);
        model2.setWeeks("3");

        MTDModel model3 = new MTDModel();
        model3.setSalesValues(12.0);
        model3.setCurrentSalesValue(19.0);
        model3.setReportDay(0);
        model3.setWeeks("4");

        MTDModel model4 = new MTDModel();
        model4.setSalesValues(102.0);
        model4.setCurrentSalesValue(91.0);
        model4.setReportDay(0);
        model4.setWeeks("5");

        weekSalesList.add(model);
        weekSalesList.add(model1);
        weekSalesList.add(model2);
        weekSalesList.add(model3);
        weekSalesList.add(model4);

        Bundle bundle2 = new Bundle();
        bundle2.putString(FRAGMENT_NAME, "MTDWeekWise");
        bundle2.putParcelableArrayList(SALES_LIST, (ArrayList<MTDModel>) weekSalesList);


        ArrayList<MTDModel> yearSalesList = new ArrayList<>();

        MTDModel year1 = new MTDModel();
        year1.setSalesValues(12.0);
        year1.setCurrentSalesValue(11.0);
        year1.setReportDay(0);
        year1.setMonths(1);
        year1.setWeeks("");

        MTDModel year2 = new MTDModel();
        year2.setSalesValues(0.0);
        year2.setCurrentSalesValue(0.0);
        year2.setReportDay(0);
        year2.setMonths(2);
        year2.setWeeks("");

        MTDModel year3 = new MTDModel();
        year3.setSalesValues(0.0);
        year3.setCurrentSalesValue(0.0);
        year3.setReportDay(0);
        year2.setMonths(3);
        year3.setWeeks("");

        MTDModel year4 = new MTDModel();
        year4.setSalesValues(12.0);
        year4.setCurrentSalesValue(9.0);
        year4.setReportDay(0);
        year4.setMonths(4);
        year4.setWeeks("");

        MTDModel year5 = new MTDModel();
        year5.setSalesValues(102.0);
        year5.setCurrentSalesValue(91.0);
        year5.setReportDay(0);
        year5.setMonths(5);
        year5.setWeeks("");

        MTDModel year6 = new MTDModel();
        year6.setSalesValues(0.0);
        year6.setCurrentSalesValue(0.0);
        year6.setReportDay(0);
        year6.setMonths(6);
        year6.setWeeks("");


        MTDModel year7 = new MTDModel();
        year7.setSalesValues(0.0);
        year7.setCurrentSalesValue(0.0);
        year7.setReportDay(0);
        year7.setMonths(7);
        year7.setWeeks("");

        MTDModel year8 = new MTDModel();
        year8.setSalesValues(0.0);
        year8.setCurrentSalesValue(0.0);
        year8.setReportDay(0);
        year8.setMonths(8);
        year8.setWeeks("");

        MTDModel year9 = new MTDModel();
        year9.setSalesValues(0.0);
        year9.setCurrentSalesValue(0.0);
        year9.setReportDay(0);
        year9.setMonths(1);
        year9.setWeeks("");

        MTDModel year10 = new MTDModel();
        year10.setSalesValues(0.0);
        year10.setCurrentSalesValue(0.0);
        year10.setReportDay(0);
        year10.setMonths(1);
        year10.setWeeks("");

        yearSalesList.add(year2);
        yearSalesList.add(year2);
        yearSalesList.add(year3);
        yearSalesList.add(year4);
        yearSalesList.add(year5);
        yearSalesList.add(year6);
        yearSalesList.add(year7);
        yearSalesList.add(year8);
        yearSalesList.add(year9);
        yearSalesList.add(year10);

        Bundle bundle3 = new Bundle();
        bundle3.putString(FRAGMENT_NAME, "MTDYearWise");
        bundle3.putParcelableArrayList(SALES_LIST, (ArrayList<MTDModel>) yearSalesList);


        CommonViewPagerAdapter pagerAdapter = new CommonViewPagerAdapter(getSFAFragmentActivity().getSupportFragmentManager());

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(pagerPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerPosition = position;
                updateIndicatorOnScroll(position);
            }

            @Override
            public void onPageSelected(int position) {
                // do your stuff when page selected
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do your stuff when scroll state changed
            }
        });
    }

    private void updateIndicatorOnScroll(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.ob_mustsell));
            } else {
                dots[i].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.color_blue_maps_line));
            }
        }
    }

    private void addBottomDots() {
        try {
            dots = new TextView[3];
            mLayoutPageIndicator.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(getContext());
                dots[i].setText(fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(ContextCompat.getColor(getSFAFragmentActivity(), R.color.color_blue_maps_line));
                mLayoutPageIndicator.addView(dots[i]);
            }

        } catch (Exception e) {
            //Do nothing
        }
    }


    private class DashboardAdapter extends RecyclerView.Adapter<DashViewHolder> {
        private final List<QuickViewVo> quickViewVosList;

        public List<QuickViewVo> getQuickViewVosList() {
            return quickViewVosList;
        }

        DashboardAdapter(int menuType, List<QuickViewVo> quickViewVos) {
            this.quickViewVosList = quickViewVos;
        }

        @NonNull
        @Override
        public DashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_view, parent, false);
            return new DashViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final DashViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            if (quickViewVosList.get(position).getColor() != null)
                holder.tvName.setText(quickViewVosList.get(position).getName());

            holder.tvName.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            holder.cardValTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

            SecureRandom r = new SecureRandom();
            int low = 100;
            int high = 10000;
            int result = r.nextInt(high - low) + low;

            holder.cardValTxt.setText(String.valueOf(result));


            if (quickViewVoList.size() > 3) {
                if ((holder.getAdapterPosition() % 2 == 0)) {
                    ViewGroup.LayoutParams params = holder.mainLayout.getLayoutParams();
                    params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                    params.width = MATCH_PARENT;
                    holder.mainLayout.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = holder.mainLayout.getLayoutParams();
                    params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
                    params.width = MATCH_PARENT;
                    holder.mainLayout.setLayoutParams(params);
                }
            } else {
                ViewGroup.LayoutParams params = holder.mainLayout.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
                params.width = MATCH_PARENT;
                holder.mainLayout.setLayoutParams(params);
            }

            AppUtils.getOurInstance().getFixedHeightDrawable(holder.getAdapterPosition(), holder.mainLayout);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ignored
                }
            });
        }


        @Override
        public int getItemCount() {
            return quickViewVosList.size();
        }

    }

    private class DashViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView cardValTxt;
        private CardView mainLayout;

        private DashViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            cardValTxt = itemView.findViewById(R.id.card_value_txt);
            mainLayout = itemView.findViewById(R.id.main_layout_bg);


        }
    }

}
