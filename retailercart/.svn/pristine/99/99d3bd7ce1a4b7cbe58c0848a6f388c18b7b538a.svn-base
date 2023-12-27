/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.ProductFilterAdapter;
import com.botree.retailerssfa.adapters.ReportProductFilterAdapter;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.main.MainActivity;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.topsnackbar.TSnackbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_1;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_10;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_11;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_12;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_13;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_14;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_15;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_2;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_3;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_4;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_5;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_6;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_7;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_8;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_CODE_9;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_1;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_10;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_11;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_12;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_13;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_14;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_15;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_2;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_3;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_4;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_5;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_6;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_7;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_8;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LEVEL_NAME_9;
import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;

/**
 * Class which is used to show the Custom Snackbar and Dialog
 */

public class NotifyUtil {

    private static boolean previousDayClosed = false;
    private static boolean isDataAvailable = false;
    private static Dialog dateDialog;
    private static Dialog coverageDialog;
    private static Dialog sharedDialog;
    private BottomSheetDialog filterDialog;
    private static final String QUERY_APPEND = " = ? and ";

    private static String levelCode1 = "hierLevelCode1";
    private static String levelCode2 = "hierLevelCode2";
    private static String levelCode3 = "hierLevelCode3";
    private static String levelCode4 = "hierLevelCode4";

    private static String levelName1 = "hierLevelName1";
    private static String levelName2 = "hierLevelName2";
    private static String levelName3 = "hierLevelName3";
    private static String levelName4 = "hierLevelName4";

    private LinearLayout level2Layout;
    private LinearLayout level3Layout;
    private LinearLayout level4Layout;

    private ProductFilterAdapter adapter3;
    private ProductFilterAdapter adapter4;
    private List<ProFilterModel> level1;
    private String tableName;

    private int currentPos;
    private String strBrandCode;
    private String strBrandName;

    private int productSearchLevel;
    private static NotifyUtil notifyUtil;

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void setStrBrandCode(String strBrandCode) {
        this.strBrandCode = strBrandCode;
    }

    public void setStrBrandName(String strBrandName) {
        this.strBrandName = strBrandName;
    }

    public  static NotifyUtil getOurInstance(){
        if(notifyUtil == null){
            notifyUtil = new NotifyUtil();
        }
        return notifyUtil;
    }

    /**
     * Show the snackbar
     *
     * @param context under this context the snackbar will shown
     * @param view    where the snackbar will shown
     * @param msg     message which is displayed in snackbar
     */
    public static void showSnackBar(Context context, View view, String msg, int length) {

        if (context != null) {
            TSnackbar snackbar = TSnackbar.make(view, msg, length);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setIconLeft(R.drawable.notice, 24);
            snackbar.setIconPadding(8);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.navig_list_pressed_clr));
            TextView textView = snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    public static void showAutoSyncDialog(final Context context, String title, String msg) {

        if (dateDialog != null && dateDialog.isShowing()) return;
        dateDialog = new Dialog(context, R.style.ThemeDialogCustom);
        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateDialog.setCanceledOnTouchOutside(false);
        dateDialog.setCancelable(false);
        dateDialog.setContentView(R.layout.warning_dialog);
        TextView txtMainMsg = dateDialog.findViewById(R.id.alert_msg_txt);
        TextView txtMainTitle = dateDialog.findViewById(R.id.alert_msg_title);
        final Button ok = dateDialog.findViewById(R.id.alert_msg_ok_btn);

        txtMainTitle.setText(title);
        txtMainMsg.setText(msg);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("dateChange", "closeApp");
                context.startActivity(intent);
            }
        });
        dateDialog.show();
    }


    public static void showDateTimeDialog(Context context, String title, String msg,
                                          final DateTimeNotifyListener notifyListener) {

        if (dateDialog != null && dateDialog.isShowing()) return;
        dateDialog = new Dialog(context, R.style.ThemeDialogCustom);
        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateDialog.setCanceledOnTouchOutside(false);
        dateDialog.setCancelable(false);
        dateDialog.setContentView(R.layout.msg_dialog);
        TextView txtMsg = dateDialog.findViewById(R.id.alert_msg);
        TextView txtTitle = dateDialog.findViewById(R.id.alert_title);
        final Button ok = dateDialog.findViewById(R.id.alert_ok_btn);
        final Button cancel = dateDialog.findViewById(R.id.alert_cancel_btn);

        txtTitle.setText(title);
        txtMsg.setText(msg);

        if (title.startsWith("PO Confirmation")) {
            ok.setText("YES");
            cancel.setText("NO");
        }

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onOkClicked();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onCancelClicked();
                }

            }
        });

        dateDialog.show();
    }

    public static void showDialog(Context context, String title, String msg) {

        if (dateDialog != null && dateDialog.isShowing()) return;
        dateDialog = new Dialog(context, R.style.ThemeDialogCustom);
        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateDialog.setCanceledOnTouchOutside(false);
        dateDialog.setCancelable(false);
        dateDialog.setContentView(R.layout.msg_dialog);
        TextView txtMsg = dateDialog.findViewById(R.id.alert_msg);
        TextView txtTitle = dateDialog.findViewById(R.id.alert_title);
        final Button ok = dateDialog.findViewById(R.id.alert_ok_btn);
        final Button cancel = dateDialog.findViewById(R.id.alert_cancel_btn);

        txtTitle.setText(title);
        txtMsg.setText(msg);
        cancel.setVisibility(View.GONE);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });

        dateDialog.show();
    }

    public static void showListDialog(Context context, String title, String[] msg) {

        if (dateDialog != null && dateDialog.isShowing()) return;
        dateDialog = new Dialog(context, R.style.ThemeDialogCustom);
        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateDialog.setCanceledOnTouchOutside(false);
        dateDialog.setCancelable(false);
        dateDialog.setContentView(R.layout.msg_dialog_list);
        ListView txtMsg = dateDialog.findViewById(R.id.alert_msg);
        TextView txtTitle = dateDialog.findViewById(R.id.alert_title);
        final Button ok = dateDialog.findViewById(R.id.alert_ok_btn);

        txtTitle.setText(title);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, msg);
        txtMsg.setAdapter(adapter);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });

        dateDialog.show();
    }

    /**
     * Show the dialog with passed message
     *
     * @param context          under this context the dialog will shown
     * @param title            which need to display on dialog as title
     * @param msg              which need to display on dialog as message
     * @param notifyListener   here you get the trigger after ok or cancel button is clicked.
     * @param previousDayClose flag to check if previous day is closed or not
     */
    public static void showDialog(Context context, String title, String msg, final NotifyListener notifyListener,
                                  boolean previousDayClose, boolean isDataAvail) {
        previousDayClosed = previousDayClose;
        isDataAvailable = isDataAvail;
        showDialog(context, title, msg, notifyListener, null, null);
    }

    /**
     * Show the dialog with passed message
     *
     * @param context        under this context the dialog will shown
     * @param title          which need to display on dialog as title
     * @param msg            which need to display on dialog as message
     * @param notifyListener here you get the trigger after ok or cancel button is clicked.
     *                       If you need to do any addition process after ok or cancel button pressed you can do here.
     * @param positiveStr    custom positive button text.
     * @param negativeStr    custom negative button text.
     */
    public static void showDialog(Context context, String title, String msg,
                                  final NotifyListener notifyListener,
                                  String positiveStr, String negativeStr) {

//        SFADatabase db = SFADatabase.getInstance(context);

//        boolean isVanSalesAvil = db.isVanSalesDataAvailable();

        if (coverageDialog != null && coverageDialog.isShowing()) return;
        coverageDialog = new Dialog(context, R.style.ThemeDialogCustom);
        coverageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        coverageDialog.setCanceledOnTouchOutside(false);
        coverageDialog.setCancelable(false);
        coverageDialog.setContentView(R.layout.msg_dialog);
        TextView txtMsg = coverageDialog.findViewById(R.id.alert_msg);
        TextView txtTitle = coverageDialog.findViewById(R.id.alert_title);
        TextView stockMsgTxt = coverageDialog.findViewById(R.id.stock_upload_msg_txt);
        CheckBox chStockLdng = coverageDialog.findViewById(R.id.cbStockUnloding);
        final Button ok = coverageDialog.findViewById(R.id.alert_ok_btn);
        final Button cancel = coverageDialog.findViewById(R.id.alert_cancel_btn);

        changeTextBasedOnTitle(title, stockMsgTxt, chStockLdng, cancel, false);
        setTextToButton(positiveStr, ok);
        setTextToButton(negativeStr, cancel);

        txtTitle.setText(title);

        if (msg.startsWith("<font")) {
            txtMsg.setText(fromHtml(msg));
        } else {
            txtMsg.setText(msg);
        }

        if (title.equalsIgnoreCase("Logout")) {
            ok.setText(R.string.yes);
            cancel.setText(R.string.no);
        }

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                coverageDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onOkClicked();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                coverageDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onCancelClicked();
                }
            }
        });
        if (!coverageDialog.isShowing())
            coverageDialog.show();
    }


    public static void showSharedUserExistWithDistributor(final Context context, String title, String msg) {

        if (sharedDialog != null && sharedDialog.isShowing()) return;
        sharedDialog = new Dialog(context, R.style.ThemeDialogCustom);
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sharedDialog.setCanceledOnTouchOutside(false);
        sharedDialog.setCancelable(false);
        sharedDialog.setContentView(R.layout.warning_dialog);
        TextView txtMainMsg = sharedDialog.findViewById(R.id.alert_msg_txt);
        TextView txtMainTitle = sharedDialog.findViewById(R.id.alert_msg_title);
        final Button ok = sharedDialog.findViewById(R.id.alert_msg_ok_btn);

        txtMainTitle.setText(title);
        txtMainMsg.setText(msg);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sharedDialog.dismiss();
            }
        });

        if (!sharedDialog.isShowing())
            sharedDialog.show();
    }

    private static void changeTextBasedOnTitle(String title, TextView stockMsgTxt, CheckBox chStockLdng,
                                               Button cancel, boolean isVanSalesAvil) {
        if (title.equalsIgnoreCase("No Coverage") || title.equalsIgnoreCase("Alert")) {
            cancel.setVisibility(View.GONE);
        } else if (title.equalsIgnoreCase("Sync")) {

            if (!isDataAvailable) {
                chStockLdng.setVisibility(View.GONE);
                stockMsgTxt.setVisibility(View.GONE);
            } else {
                if (!previousDayClosed) {

                    checkVanSalesStuts(stockMsgTxt, chStockLdng, isVanSalesAvil);
                    previousDayClosed = false;
                } else {
                    chStockLdng.setVisibility(View.GONE);
                    stockMsgTxt.setVisibility(View.GONE);
                }
            }

            chStockLdng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SFASharedPref.getOurInstance().writeBoolean(SFASharedPref.PREF_STOCK_UNLOADING, isChecked);
                }
            });
        } else {
            cancel.setVisibility(View.VISIBLE);
        }
    }

    private static void checkVanSalesStuts(TextView stockMsgTxt, CheckBox chStockLdng,
                                           boolean isVanSalesAvil) {
        if (isVanSalesAvil) {
            chStockLdng.setVisibility(View.VISIBLE);
            stockMsgTxt.setVisibility(View.VISIBLE);
        } else {
            chStockLdng.setVisibility(View.GONE);
            stockMsgTxt.setVisibility(View.GONE);
        }
    }

    private static void setTextToButton(String positiveStr, Button ok) {
        if (!TextUtils.isEmpty(positiveStr)) {
            ok.setText(positiveStr);
        }
    }

    public void showFilterDialog(Context context,
                                        final NotifyFilterListener notifyListener, String title,
                                        List<String> list) {

        if (isFilterDialogShow()) return;
        filterDialog = new BottomSheetDialog(context);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.brand_filter_dialog);
        filterDialog.setCancelable(false);
        TextView txtTitle = filterDialog.findViewById(R.id.brand_title);
        RecyclerView recyclerView = filterDialog.findViewById(R.id.filter_recyclerview);
        final EditText searchEdt = filterDialog.findViewById(R.id.brand_search_edt);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        final ImageView cancel = filterDialog.findViewById(R.id.filter_cancel_btn);
        txtTitle.setText(title);
        level1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ProFilterModel model = new ProFilterModel();
            model.setHierLelevelName1(list.get(i));
            level1.add(model);
        }

        final ProductFilterAdapter adapter = new ProductFilterAdapter(context, level1,
                Globals.getOurInstance().getBrandPos(), false, null);

        if (adapter.getItemCount() > 0) {
            recyclerView.setAdapter(adapter);
            if (Globals.getOurInstance().getBrandPos() > 0) {
                manager.scrollToPosition(Globals.getOurInstance().getBrandPos());
            }

            adapter.setOnBrandItemClickListener(new ProductFilterAdapter.OnListBrandItemClick() {
                @Override
                public void onItemClick(int pos, String brandName) {
                    filterFinalResultItem(pos, brandName, notifyListener);
                }
            });
        }

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Ignore
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().toUpperCase(Locale.getDefault());
                if (text.trim().length() >= 0) {
                    adapter.brandSearchFilter(text);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onFilterCancelClicked();
                }

            }
        });
        if (!filterDialog.isShowing())
            filterDialog.show();
    }


    public void showBrandFilterDialog(final Context context,
                                             final NotifyBrandFilterListener notifyListener, String title,
                                             final String strtableName, final String lob) {

        final SFADatabase db = SFADatabase.getInstance(context);
        tableName = strtableName;
        SFASharedPref pref = SFASharedPref.getOurInstance();
        if (filterDialog != null && filterDialog.isShowing()) return;
        filterDialog = new BottomSheetDialog(context);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.product_filter_dialog);
        filterDialog.setCancelable(false);

        TextView txtTitle = filterDialog.findViewById(R.id.brand_title);
        TextView allBrandTxt = filterDialog.findViewById(R.id.brand_all_txt);
        TextView applyBtnTxt = filterDialog.findViewById(R.id.brand_apply_txt);
        level2Layout = filterDialog.findViewById(R.id.level2_layout);
        level3Layout = filterDialog.findViewById(R.id.level3_layout);
        level4Layout = filterDialog.findViewById(R.id.level4_layout);

        final ImageView cancel = filterDialog.findViewById(R.id.filter_cancel_btn);

        txtTitle.setText(title);
        final RecyclerView recyclerView1 = filterDialog.findViewById(R.id.filter_recyclerview1);
        final RecyclerView recyclerView2 = filterDialog.findViewById(R.id.filter_recyclerview2);
        final RecyclerView recyclerView3 = filterDialog.findViewById(R.id.filter_recyclerview3);
        final RecyclerView recyclerView4 = filterDialog.findViewById(R.id.filter_recyclerview4);

        StaggeredGridLayoutManager manager1;
        StaggeredGridLayoutManager manager2;
        StaggeredGridLayoutManager manager3;
        StaggeredGridLayoutManager manager4;

        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
            manager1 = new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL);
            manager2 = new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL);
            manager3 = new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL);
            manager4 = new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL);
        } else {
            // code for landscape mode
            manager1 = new StaggeredGridLayoutManager(4,
                    StaggeredGridLayoutManager.VERTICAL);
            manager2 = new StaggeredGridLayoutManager(4,
                    StaggeredGridLayoutManager.VERTICAL);
            manager3 = new StaggeredGridLayoutManager(4,
                    StaggeredGridLayoutManager.VERTICAL);
            manager4 = new StaggeredGridLayoutManager(4,
                    StaggeredGridLayoutManager.VERTICAL);
        }

        if (recyclerView1 != null) {
            recyclerView1.setLayoutManager(manager1);
            recyclerView1.setNestedScrollingEnabled(false);
        }
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(manager2);
            recyclerView2.setNestedScrollingEnabled(false);
        }
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(manager3);
            recyclerView3.setNestedScrollingEnabled(false);
        }
        if (recyclerView4 != null) {
            recyclerView4.setLayoutManager(manager4);
            recyclerView4.setNestedScrollingEnabled(false);
        }

        productSearchLevel = getProductSearchLevelCode(context, pref);
        hideNextLevelLayout();
        ProductFilterAdapter adapter1 = getProductFilterAdapterFirstLevelData(context, db, lob);

        if (adapter1.getItemCount() > 0) {
            assert recyclerView1 != null;
            recyclerView1.setAdapter(adapter1);
            if (Globals.getOurInstance().getProdLevel1Pos() > -1 || Globals.getOurInstance().getProdLevel2Pso() > -1 ||
                    Globals.getOurInstance().getCategorPos() > -1 || Globals.getOurInstance().getBrandPos() > -1) {

                loadExistsFilterList(context, recyclerView2, recyclerView3, recyclerView4,
                        notifyListener, productSearchLevel, lob);
            }

            adapter1.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
                @Override
                public void onItemClick(int pos, String brandCode, String brandName) {
                    Globals.getOurInstance().setProdLevel1Pos(pos);
                    if (productSearchLevel == 1) {

                        filterFinalItem(pos, brandCode, brandName, notifyListener);

                    } else {
                        Globals.getOurInstance().setProdLevel2Pso(-1);
                        Globals.getOurInstance().setCategorPos(-1);
                        Globals.getOurInstance().setBrandPos(-1);
                        clearNextLevelAdapters();
                        loadSecondLevelData(brandCode, lob, context, recyclerView2,
                                recyclerView3, recyclerView4, notifyListener);
                    }
                    currentPos = pos;
                    strBrandCode = brandCode;
                    strBrandName = brandName;
                }
            });
        }

        allBrandTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.getOurInstance().setProdLevel1Pos(-1);
                Globals.getOurInstance().setProdLevel2Pso(-1);
                Globals.getOurInstance().setCategorPos(-1);
                Globals.getOurInstance().setBrandPos(-1);
                filterDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onfilterOkClicked(0, "", "All Brand");
                    strBrandCode = "";
                    strBrandName = "";
                }
            }
        });


        applyBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFinalItem(currentPos, strBrandCode, strBrandName, notifyListener);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onFilterCancelClicked();
                }

            }
        });
        if (!filterDialog.isShowing())
            filterDialog.show();
    }

    private int filterLevel;
    private List<String> selection = new ArrayList<>();
    private List<MTDReportModel> models = new ArrayList<>();

    public void showReportBrandFilterDialog(final Context context, final NotifyReportProdFilterListener notifyListener,
                                                   final SFADatabase sfaDatabase, final String[] levels, final String codeColumn,
                                                   final String nameColumn, final String groupBy, final String where) {

        if (filterDialog != null && filterDialog.isShowing()) return;
        filterDialog = new BottomSheetDialog(context);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.report_product_filter_dialog);
        filterDialog.setCancelable(false);

        TextView allBrandTxt = filterDialog.findViewById(R.id.brand_all_txt);
        TextView applyBtnTxt = filterDialog.findViewById(R.id.brand_apply_txt);
        final TextView selections = filterDialog.findViewById(R.id.selections);
        ImageView goBackButton = filterDialog.findViewById(R.id.go_back);

        final ImageView cancel = filterDialog.findViewById(R.id.filter_cancel_btn);

        final RecyclerView recyclerView1 = filterDialog.findViewById(R.id.filter_recyclerview);

        StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);

        recyclerView1.setLayoutManager(manager1);

        recyclerView1.setNestedScrollingEnabled(false);

        models = sfaDatabase.getUniqueProductLevels(levels, codeColumn, nameColumn, groupBy, where);
        setSelectionText(sfaDatabase, selections);

        final ReportProductFilterAdapter adapter1 = new ReportProductFilterAdapter(context, models, 0, filterLevel, false);
        recyclerView1.setAdapter(adapter1);

        adapter1.setOnItemClickListener(new ReportProductFilterAdapter.OnListItemClick() {
            @Override
            public void onItemClick(int pos, String brandCode, String brandName) {
                selection.add(brandCode);
                models = sfaDatabase.getUniqueProductLevels(getReportFilterWhereArgs(), getReportFilterCodeColumn(), getReportFilterNameColumn(), getReportFilterCodeColumn(), getReportFilterWhereQuery());
                if (!models.isEmpty()) {
                    filterLevel = filterLevel + 1;
                    adapter1.updateRecyclerView(models, pos, filterLevel, true);
                } else {
                    filterDialog.dismiss();
                    if (notifyListener != null) {
                        notifyListener.onfilterOkClicked(0, "", "", filterLevel);
                    }
                }
                setSelectionText(sfaDatabase, selections);
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selection.isEmpty()) {
                    selection.remove(selection.size() - 1);
                    models = sfaDatabase.getUniqueProductLevels(getReportFilterWhereArgs(), getReportFilterCodeColumn(), getReportFilterNameColumn(), getReportFilterCodeColumn(), getReportFilterWhereQuery());
                    if (!models.isEmpty()) {
                        filterLevel = filterLevel - 1;
                        adapter1.updateRecyclerView(models, 0, filterLevel, true);
                    }
                    setSelectionText(sfaDatabase, selections);
                }
            }
        });

        allBrandTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                clearReportBrandFilter();
                if (notifyListener != null) {
                    notifyListener.onfilterOkClicked(0, "", "all", filterLevel);
                }
            }
        });

        applyBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onfilterOkClicked(0, "", "", filterLevel);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                if (notifyListener != null) {
                    notifyListener.onFilterCancelClicked();
                }
            }
        });

        if (!filterDialog.isShowing())
            filterDialog.show();
    }

    private void setSelectionText(SFADatabase database, TextView textView) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < selection.size(); i++) {
            String name = database.getBrandName(selection.get(i), i);
            stringBuilder.append(name).append(" -> ");
        }
        try {
            int start = stringBuilder.toString().lastIndexOf(" -> ");
            textView.setText(stringBuilder.toString().substring(0, start));
        } catch (Exception e) {
//Do Nothing
        }
    }

    public void clearReportBrandFilter() {
        filterLevel = 0;
        selection.clear();
        models.clear();
    }

    public String[] getReportFilterWhereArgs() {
        if (selection.isEmpty()) {
            return null;
        } else {
            return selection.toArray(new String[selection.size()]);
        }
    }

    public String getReportFilterWhereQuery() {
        if (selection.isEmpty()) {
            return null;
        } else if (selection.size() == 1) {
            return COLUMN_LEVEL_CODE_1 + " = ?";
        } else if (selection.size() == 2) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + " = ?";
        } else if (selection.size() == 3) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + " = ?";
        } else if (selection.size() == 4) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + " = ?";
        } else if (selection.size() == 5) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + " = ?";
        } else if (selection.size() == 6) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + " = ?";
        } else if (selection.size() == 7) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + " = ?";
        } else if (selection.size() == 8) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + " = ?";
        } else if (selection.size() == 9) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + " = ?";
        } else if (selection.size() == 10) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + " = ?";
        } else if (selection.size() == 11) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + QUERY_APPEND + COLUMN_LEVEL_CODE_11 + " = ?";
        } else if (selection.size() == 12) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + QUERY_APPEND + COLUMN_LEVEL_CODE_11 + QUERY_APPEND + COLUMN_LEVEL_CODE_12 + " = ?";
        } else if (selection.size() == 13) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + QUERY_APPEND + COLUMN_LEVEL_CODE_11 + QUERY_APPEND + COLUMN_LEVEL_CODE_12 + QUERY_APPEND + COLUMN_LEVEL_CODE_13 + " = ?";
        } else if (selection.size() == 14) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + QUERY_APPEND + COLUMN_LEVEL_CODE_11 + QUERY_APPEND + COLUMN_LEVEL_CODE_12 + QUERY_APPEND + COLUMN_LEVEL_CODE_13 + QUERY_APPEND + COLUMN_LEVEL_CODE_14 + " = ?";
        } else if (selection.size() == 15) {
            return COLUMN_LEVEL_CODE_1 + QUERY_APPEND + COLUMN_LEVEL_CODE_2 + QUERY_APPEND + COLUMN_LEVEL_CODE_3 + QUERY_APPEND + COLUMN_LEVEL_CODE_4 + QUERY_APPEND + COLUMN_LEVEL_CODE_5 + QUERY_APPEND + COLUMN_LEVEL_CODE_6 + QUERY_APPEND + COLUMN_LEVEL_CODE_7 + QUERY_APPEND + COLUMN_LEVEL_CODE_8 + QUERY_APPEND + COLUMN_LEVEL_CODE_9 + QUERY_APPEND + COLUMN_LEVEL_CODE_10 + QUERY_APPEND + COLUMN_LEVEL_CODE_11 + QUERY_APPEND + COLUMN_LEVEL_CODE_12 + QUERY_APPEND + COLUMN_LEVEL_CODE_13 + QUERY_APPEND + COLUMN_LEVEL_CODE_14 + QUERY_APPEND + COLUMN_LEVEL_CODE_15 + " = ?";
        }
        return COLUMN_LEVEL_CODE_1 + " = ?";
    }

    public String getReportFilterCodeColumn() {
        if (selection.isEmpty()) {
            return COLUMN_LEVEL_CODE_1;
        } else if (selection.size() == 1) {
            return COLUMN_LEVEL_CODE_2;
        } else if (selection.size() == 2) {
            return COLUMN_LEVEL_CODE_3;
        } else if (selection.size() == 3) {
            return COLUMN_LEVEL_CODE_4;
        } else if (selection.size() == 4) {
            return COLUMN_LEVEL_CODE_5;
        } else if (selection.size() == 5) {
            return COLUMN_LEVEL_CODE_6;
        } else if (selection.size() == 6) {
            return COLUMN_LEVEL_CODE_7;
        } else if (selection.size() == 7) {
            return COLUMN_LEVEL_CODE_8;
        } else if (selection.size() == 8) {
            return COLUMN_LEVEL_CODE_9;
        } else if (selection.size() == 9) {
            return COLUMN_LEVEL_CODE_10;
        } else if (selection.size() == 10) {
            return COLUMN_LEVEL_CODE_11;
        } else if (selection.size() == 11) {
            return COLUMN_LEVEL_CODE_12;
        } else if (selection.size() == 12) {
            return COLUMN_LEVEL_CODE_13;
        } else if (selection.size() == 13) {
            return COLUMN_LEVEL_CODE_13;
        } else if (selection.size() == 14) {
            return COLUMN_LEVEL_CODE_14;
        } else if (selection.size() == 15) {
            return COLUMN_LEVEL_CODE_15;
        }
        return COLUMN_LEVEL_CODE_1;
    }

    public String getReportFilterNameColumn() {
        if (selection.isEmpty()) {
            return COLUMN_LEVEL_NAME_1;
        } else if (selection.size() == 1) {
            return COLUMN_LEVEL_NAME_2;
        } else if (selection.size() == 2) {
            return COLUMN_LEVEL_NAME_3;
        } else if (selection.size() == 3) {
            return COLUMN_LEVEL_NAME_4;
        } else if (selection.size() == 4) {
            return COLUMN_LEVEL_NAME_5;
        } else if (selection.size() == 5) {
            return COLUMN_LEVEL_NAME_6;
        } else if (selection.size() == 6) {
            return COLUMN_LEVEL_NAME_7;
        } else if (selection.size() == 7) {
            return COLUMN_LEVEL_NAME_8;
        } else if (selection.size() == 8) {
            return COLUMN_LEVEL_NAME_9;
        } else if (selection.size() == 9) {
            return COLUMN_LEVEL_NAME_10;
        } else if (selection.size() == 10) {
            return COLUMN_LEVEL_NAME_11;
        } else if (selection.size() == 11) {
            return COLUMN_LEVEL_NAME_12;
        } else if (selection.size() == 12) {
            return COLUMN_LEVEL_NAME_13;
        } else if (selection.size() == 13) {
            return COLUMN_LEVEL_NAME_13;
        } else if (selection.size() == 14) {
            return COLUMN_LEVEL_NAME_14;
        } else if (selection.size() == 15) {
            return COLUMN_LEVEL_NAME_15;
        }
        return COLUMN_LEVEL_NAME_1;
    }

    static List<MTDReportModel> getNextLevelUniqueModels(final List<MTDReportModel> list, final List<String> codes, final int level) {
        List<MTDReportModel> nextLevelmodels = new ArrayList<>();
        for (String code : codes) {
            for (MTDReportModel model : list) {
                if (Globals.getCodeBasedOnLevel(level, model).equalsIgnoreCase(code)) {
                    nextLevelmodels.add(model);
                    break;
                }
            }
        }
        return nextLevelmodels;
    }

    private OrderSupportUtil.ZeroResult tempZeroNonZeroResult;
    private OrderSupportUtil.ZeroResult zeroNonZeroResult;

    public void showOtherFilterDialog(Context context, final NotifyOtherFilterListener notifyListener, String title,
                                             final boolean showQtyFilter, OrderSupportUtil.ZeroResult tempZeroNonZero,
                                             OrderSupportUtil.ZeroResult zeroNonZero, SFADatabase db,
                                             SFASharedPref preferences, boolean includeAllLob) {

        if (filterDialog != null && filterDialog.isShowing()) return;

        tempZeroNonZeroResult = tempZeroNonZero;
        zeroNonZeroResult = zeroNonZero;

        filterDialog = new BottomSheetDialog(context);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.category_filter_dialog);
        filterDialog.setCancelable(false);

        final TextView titleTextView = filterDialog.findViewById(R.id.filter_title);
        final LinearLayout stockFilterLayout = filterDialog.findViewById(R.id.stock_filter_layout);
        final RadioGroup zeroNonZeroRadioGroup = filterDialog.findViewById(R.id.zero_non_zero_radio_group);
        final RecyclerView filterRecyclerView = filterDialog.findViewById(R.id.cat_filter_recyclerview);
        ImageView cancelBtn = filterDialog.findViewById(R.id.cat_filter_cancel_btn);

        RadioGroup.OnCheckedChangeListener zeroRadioGroupListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                assignZeroGroupValue(checkedId, true);
            }
        };

        titleTextView.setText(title);
        if (showQtyFilter) {
            stockFilterLayout.setVisibility(View.VISIBLE);

            setZeroNonZeroProductRG(zeroNonZeroRadioGroup, tempZeroNonZeroResult, zeroNonZeroResult);
            zeroNonZeroRadioGroup.setOnCheckedChangeListener(zeroRadioGroupListener);
        } else {
            stockFilterLayout.setVisibility(View.GONE);
        }

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        filterRecyclerView.setLayoutManager(manager);

        List<ProFilterModel> level1List = new ArrayList<>();
        if (includeAllLob) {
            ProFilterModel bookingVO = new ProFilterModel();
            bookingVO.setHierLelevelCode1("All");
            bookingVO.setHierLelevelName1("All");
            level1List.add(bookingVO);
        }
        level1List.addAll(db.getLOBFilterData(preferences.readString(PREF_CMP_CODE)));

        final ProductFilterAdapter adapter = new ProductFilterAdapter(context, level1List,
                Globals.getOurInstance().getOtherFilterPso(), false, null);
        filterRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() > 0) {
            adapter.setOnBrandItemClickListener(new ProductFilterAdapter.OnListBrandItemClick() {
                @Override
                public void onItemClick(int pos, String brandVal) {
                    if (notifyListener != null) {
                        notifyListener.onfilterOkClicked(pos, brandVal, zeroNonZeroResult);
                    }
                    filterDialog.dismiss();
                }
            });
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (notifyListener != null) {
                    notifyListener.onFilterCancelClicked();
                }
                filterDialog.dismiss();
            }
        });

        if (filterDialog != null && !filterDialog.isShowing())
            filterDialog.show();
    }

    /**
     * Get the Radio Group result and store it into to zeroNonZeroResult or tempZeroNonZeroResult
     *
     * @param checkedRadioButtonId get the response of zero product group with this radio button id
     * @param isFromSubmit         If from submit button then store the result to zeroNonZeroResult
     *                             else tempZeroNonZeroResult
     */
    private void assignZeroGroupValue(int checkedRadioButtonId, boolean isFromSubmit) {
        OrderSupportUtil.ZeroResult result;
        switch (checkedRadioButtonId) {
            case R.id.zero_btn:
                result = OrderSupportUtil.ZeroResult.ZERO_PRODUCTS;
                break;
            case R.id.non_zero_btn:
                result = OrderSupportUtil.ZeroResult.NON_ZERO_PRODUCTS;
                break;
            case R.id.all_btn:
            default:
                result = OrderSupportUtil.ZeroResult.ALL_PRODUCTS;
                break;
        }
        if (isFromSubmit)
            zeroNonZeroResult = result;
        else
            tempZeroNonZeroResult = result;
    }

    /**
     * Check the orientation handling temp variable is null.
     * If not null then get temp variable value and set to radio group
     * else go with zeroNonZeroResult
     *
     * @param zeroRG set this to Zero Product radio group
     */
    private static void setZeroNonZeroProductRG(RadioGroup zeroRG, OrderSupportUtil.ZeroResult tempZeroNonZeroResult, OrderSupportUtil.ZeroResult zeroNonZeroResult) {
        OrderSupportUtil.ZeroResult result = tempZeroNonZeroResult != null ? tempZeroNonZeroResult : zeroNonZeroResult;

        switch (result) {
            case ZERO_PRODUCTS:
                zeroRG.check(R.id.zero_btn);
                break;
            case NON_ZERO_PRODUCTS:
                zeroRG.check(R.id.non_zero_btn);
                break;
            case ALL_PRODUCTS:
            default:
                zeroRG.check(R.id.all_btn);
                break;
        }
    }

    private void hideNextLevelLayout() {
        if (productSearchLevel == 1) {
            level2Layout.setVisibility(View.GONE);
            level3Layout.setVisibility(View.GONE);
            level4Layout.setVisibility(View.GONE);
        } else if (productSearchLevel == 2) {
            level3Layout.setVisibility(View.GONE);
            level4Layout.setVisibility(View.GONE);
        } else if (productSearchLevel == 3) {
            level4Layout.setVisibility(View.GONE);
        }
    }

    private void clearNextLevelAdapters() {
        if ((adapter3 != null && adapter3.getItemCount() > 0)) {
            adapter3.clearData();

        }
        if (adapter4 != null && adapter4.getItemCount() > 0) {
            adapter4.clearData();
        }
    }

    private void loadExistsFilterList(final Context context, RecyclerView recyclerView2,
                                             final RecyclerView recyclerView3,
                                             final RecyclerView recyclerView4,
                                             final NotifyBrandFilterListener notifyListener,
                                             int currentLevelPos, final String lobCode) {

        final SFADatabase db = SFADatabase.getInstance(context);
        ProductFilterAdapter adapter2 = null;

        try {

            if (currentLevelPos == 4) {

                final List<ProFilterModel> level2 = db.getNexttLevelData(tableName,
                        level1.get(Globals.getOurInstance().getProdLevel1Pos()).getHierLelevelCode1(),
                        levelCode1, levelCode2, levelName2, lobCode);

                adapter2 = new ProductFilterAdapter(context, level2,
                        Globals.getOurInstance().getProdLevel2Pso(), true, null);
                recyclerView2.setAdapter(adapter2);

                final List<ProFilterModel> level3 = db.getNexttLevelData(tableName,
                        level2.get(Globals.getOurInstance().getProdLevel2Pso()).getHierLelevelCode1(),
                        levelCode2, levelCode3, levelName3, lobCode);

                adapter3 = new ProductFilterAdapter(context, level3, Globals.getOurInstance().getCategorPos(),
                        true, null);
                recyclerView3.setAdapter(adapter3);


                final List<ProFilterModel> level4 = db.getNexttLevelData(tableName,
                        level3.get(Globals.getOurInstance().getCategorPos()).getHierLelevelCode1(),
                        levelCode3, levelCode4, levelName4, lobCode);

                adapter4 = new ProductFilterAdapter(context, level4,
                        Globals.getOurInstance().getBrandPos(), true, null);
                recyclerView4.setAdapter(adapter4);

            } else if (currentLevelPos == 3) {

                final List<ProFilterModel> level2 = db.getNexttLevelData(tableName,
                        level1.get(Globals.getOurInstance().getProdLevel1Pos()).getHierLelevelCode1(),
                        levelCode1, levelCode2, levelName2, lobCode);

                adapter2 = new ProductFilterAdapter(context, level2,
                        Globals.getOurInstance().getProdLevel2Pso(), true, null);
                recyclerView2.setAdapter(adapter2);


                final List<ProFilterModel> level3 = db.getNexttLevelData(tableName,
                        level2.get(Globals.getOurInstance().getProdLevel2Pso()).getHierLelevelCode1(),
                        levelCode2, levelCode3, levelName3, lobCode);

                adapter3 = new ProductFilterAdapter(context, level3, Globals.getOurInstance().getCategorPos(),
                        true, null);
                recyclerView3.setAdapter(adapter3);

            } else if (currentLevelPos == 2) {

                final List<ProFilterModel> level2 = db.getNexttLevelData(tableName,
                        level1.get(Globals.getOurInstance().getProdLevel1Pos()).getHierLelevelCode1(),
                        levelCode1, levelCode2, levelName2, lobCode);

                adapter2 = new ProductFilterAdapter(context, level2,
                        Globals.getOurInstance().getProdLevel2Pso(), true, null);
                recyclerView2.setAdapter(adapter2);

            }
        } catch (Exception e) {
            Log.e("setAdapter", "loadExistsFilterList: " + e.getMessage(), e);
        }
        if (adapter2 != null && adapter2.getItemCount() > 0) {
            adapter2.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
                @Override
                public void onItemClick(int pos, String brandCode, String brandName1) {
                    Globals.getOurInstance().setProdLevel2Pso(pos);
                    Globals.getOurInstance().setCategorPos(-1);
                    Globals.getOurInstance().setBrandPos(-1);

                    loadNextAdapterData(pos, brandCode, brandName1, lobCode, notifyListener, context, recyclerView3, recyclerView4);

                    currentPos = pos;
                    strBrandCode = brandCode;
                    strBrandName = brandName1;
                }
            });
        }

        if (adapter3 != null && adapter3.getItemCount() > 0) {
            adapter3.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
                @Override
                public void onItemClick(int pos, String brandCode, String brandName1) {
                    Globals.getOurInstance().setCategorPos(pos);
                    Globals.getOurInstance().setBrandPos(-1);

                    if (productSearchLevel == 3) {
                        filterFinalItem(pos, brandCode, brandName1, notifyListener);
                    } else {
                        loadFourthLevelData(brandCode, lobCode, db, tableName, context, recyclerView4, notifyListener);
                    }

                    currentPos = pos;
                    strBrandCode = brandCode;
                    strBrandName = brandName1;
                }
            });
        }

        if (adapter4 != null && adapter4.getItemCount() > 0) {
            adapter4.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
                @Override
                public void onItemClick(int pos, String brandCode, String brandName) {
                    Globals.getOurInstance().setBrandPos(pos);
                    filterFinalItem(pos, brandCode, brandName, notifyListener);
                }
            });
        }
    }

    private void loadNextAdapterData(int pos, String brandCode, String brandName1, String lobCode,
                                            NotifyBrandFilterListener notifyListener, Context context,
                                            RecyclerView recyclerView3, RecyclerView recyclerView4) {
        if (productSearchLevel == 2) {
            filterFinalItem(pos, brandCode, brandName1, notifyListener);
        } else {
            if (adapter4 != null && adapter4.getItemCount() > 0) {
                adapter4.clearData();
            }

            loadThirdLevelData(brandCode, lobCode, context, recyclerView3, recyclerView4,
                    notifyListener);
        }
    }

    private static int getProductSearchLevelCode(Context context, SFASharedPref pref) {

//        int productSearchLevel = 4;
        int productSearchLevel = 2;
        try {
            productSearchLevel = Integer.parseInt(context.getResources().getString(R.string.filter_level));
            String levels = pref.readString(SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL);
            if (levels != null && !levels.isEmpty()) {
                productSearchLevel = Integer.parseInt(pref.readString(SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL));
            }
        } catch (Exception e) {
            Log.e("filter level", "getProductSearchLevelCode: " + e.getMessage(), e);
        }
        return productSearchLevel;
    }

    private void filterFinalItem(int pos, String brandCode, String brandName,
                                        NotifyBrandFilterListener notifyListener) {
        Log.d("final_filter", "clicked: " + brandCode + " " + brandName);
        filterDialog.dismiss();
        if (notifyListener != null) {
            Globals.getOurInstance().setBrandPos(pos);
            notifyListener.onfilterOkClicked(pos, brandCode, brandName);
        }
    }

    private void filterFinalResultItem(int pos, String brandName,
                                              NotifyFilterListener notifyListener) {
        Globals.getOurInstance().setBrandPos(pos);
        filterDialog.dismiss();
        if (notifyListener != null) {
            notifyListener.onfilterOkClicked(pos, brandName);
        }
    }

    @NonNull
    private ProductFilterAdapter getProductFilterAdapterFirstLevelData(Context context, SFADatabase db, String lob) {

        ProductFilterAdapter adapter1;

        level1 = db.getFirstLevel(tableName, levelCode1,
                levelName1, lob);
        adapter1 = new ProductFilterAdapter(context, level1,
                Globals.getOurInstance().getProdLevel1Pos(), true, null);
        return adapter1;
    }

    private boolean isFilterDialogShow() {
        return filterDialog != null && filterDialog.isShowing();
    }

    private void loadFourthLevelData(String brandVal, String lobCode, SFADatabase db, String tableName,
                                            Context context, RecyclerView recyclerView4,
                                            final NotifyBrandFilterListener notifyListener) {

        final List<ProFilterModel> level2 = db.getNexttLevelData(tableName, brandVal, levelCode3, levelCode4, levelName4, lobCode);

        adapter4 = new ProductFilterAdapter(context, level2,
                Globals.getOurInstance().getBrandPos(), true, null);
        recyclerView4.setAdapter(adapter4);

        adapter4.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
            @Override
            public void onItemClick(int pos, String brandCode, String brandName) {

                Globals.getOurInstance().setBrandPos(pos);
                currentPos = pos;
                if (!brandCode.equalsIgnoreCase(strBrandCode)) {
                    strBrandCode = strBrandCode + "/" + brandCode;
                    strBrandName = strBrandName + "/" + brandName;
                } else {
                    strBrandCode = brandCode;
                    strBrandName = brandName;
                }
                filterFinalItem(pos, strBrandCode, strBrandName, notifyListener);
            }
        });
    }

    private void loadThirdLevelData(String brandVal, final String lobCode,
                                           final Context context, RecyclerView recyclerView3,
                                           final RecyclerView recyclerView4,
                                           final NotifyBrandFilterListener notifyListener) {

        final SFADatabase db = SFADatabase.getInstance(context);
        final List<ProFilterModel> level2 = db.getNexttLevelData(tableName, brandVal, levelCode2,
                levelCode3, levelName3, lobCode);

        if (adapter4 != null && adapter4.getItemCount() > 0) {
            adapter4.clearData();
        }

        adapter3 = new ProductFilterAdapter(context, level2,
                Globals.getOurInstance().getCategorPos(), true, null);
        recyclerView3.setAdapter(adapter3);

        adapter3.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
            @Override
            public void onItemClick(int pos, String brandCode, String brandName1) {
                Globals.getOurInstance().setCategorPos(pos);
                if (productSearchLevel == 3) {

                    filterFinalItem(pos, brandCode, brandName1, notifyListener);
                } else {
                    Globals.getOurInstance().setBrandPos(-1);
                    loadFourthLevelData(brandCode, lobCode, db, tableName, context, recyclerView4, notifyListener);
                }
                currentPos = pos;
                if (!brandCode.equalsIgnoreCase(strBrandCode)) {
                    strBrandCode = strBrandCode + "/" + brandCode;
                    strBrandName = strBrandName + "/" + brandName1;
                } else {
                    strBrandCode = brandCode;
                    strBrandName = brandName1;
                }
            }
        });
    }

    private void loadSecondLevelData(String brandVal, final String lobCode,
                                            final Context context, RecyclerView recyclerView2,
                                            final RecyclerView recyclerView3,
                                            final RecyclerView recyclerView4,
                                            final NotifyBrandFilterListener notifyListener) {

        SFADatabase db = SFADatabase.getInstance(context);

        final List<ProFilterModel> level2 = db.getNexttLevelData(tableName, brandVal,
                levelCode1, levelCode2, levelName2, lobCode);

        ProductFilterAdapter adapter2 = new ProductFilterAdapter(context, level2,
                Globals.getOurInstance().getProdLevel2Pso(), true, null);
        recyclerView2.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new ProductFilterAdapter.OnListItemClick() {
            @Override
            public void onItemClick(int pos, String brandCode, String brandName1) {
                Globals.getOurInstance().setProdLevel2Pso(pos);
                if (productSearchLevel == 2) {

                    filterFinalItem(pos, brandCode, brandName1, notifyListener);
                } else {
                    Globals.getOurInstance().setCategorPos(-1);
                    Globals.getOurInstance().setBrandPos(-1);
                    if (adapter4 != null && adapter4.getItemCount() > 0) {
                        adapter4.clearData();
                    }

                    loadThirdLevelData(brandCode, lobCode, context, recyclerView3, recyclerView4,
                            notifyListener);
                }
                currentPos = pos;
                if (!brandCode.equalsIgnoreCase(strBrandCode)) {
                    strBrandCode = strBrandCode + "/" + brandCode;
                    strBrandName = strBrandName + "/" + brandName1;
                } else {
                    strBrandCode = brandCode;
                    strBrandName = brandName1;
                }
            }
        });
    }


    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface DateTimeNotifyListener {
        void onOkClicked();

        void onCancelClicked();
    }

    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface NotifyListener {
        void onOkClicked();

        void onCancelClicked();
    }

    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface NotifyFilterListener {

        void onfilterOkClicked(int pos, String brandVal);

        void onFilterCancelClicked();
    }

    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface NotifyOtherFilterListener {

        void onfilterOkClicked(int pos, String brandVal,
                               OrderSupportUtil.ZeroResult zeroNonZero);

        void onFilterCancelClicked();
    }

    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface NotifyBrandFilterListener {

        void onfilterOkClicked(int pos, String brandCode, String brandVal);

        void onFilterCancelClicked();
    }

    /**
     * Notify after ok or cancel button is clicked in dialog
     */
    public interface NotifyReportProdFilterListener {

        void onfilterOkClicked(int pos, String brandCode, String brandVal, int level);

        void onFilterCancelClicked();
    }

}
