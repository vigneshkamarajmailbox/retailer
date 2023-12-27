package com.botree.retailerssfa.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OnRecyclerItemClick;
import com.botree.retailerssfa.adapters.SchemeDistributorListAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.FeedBackMasterModel;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFALocation;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private SFADatabase db;
    private ConstraintLayout rootLayout;
    private Spinner feedbackSpinner;
    private Spinner distr_spinner;
    private ArrayList<CustomerModel> distributorArrayList = new ArrayList<>();
    private String selectedDistr = "";
    private String selectedFeedbackCode = "", selectedFeedbackName = "";
    private int selectedFeedBackPos = 0, selectedDistrPos = 0;
    private Button feedback_submit_btn;
    private EditText feedback_edt_txt;
    private ImageView feedbackImgIv, captureImgIv, deleteImagIv;
    private Group groupCaptureImg;

    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private final String TAG = FeedbackActivity.class.getSimpleName();
    private File photofile;
    private Uri fileUri;

    private String value;
    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFeedbackSpinnerDataFromDB();
        loadDistributorSpinnerDataFromDB();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.feedback_submit_btn:
                if (feedback_edt_txt.getText().toString().length() > 0) {
                    FeedbackModel feedbackModel = new FeedbackModel();
                    feedbackModel.setCmpCode(distributorArrayList.get(selectedDistrPos).getCmpCode());
                    feedbackModel.setFeedbackNo(UUID.randomUUID().toString());
                    feedbackModel.setFeedbackDate("" + System.currentTimeMillis());
                    feedbackModel.setDistrCode(distributorArrayList.get(selectedDistrPos).getDistrCode());
                    feedbackModel.setCustomerCode(distributorArrayList.get(selectedDistrPos).getCustomerCode());
                    feedbackModel.setFeedbackType(selectedFeedbackCode);
                    feedbackModel.setMessage(feedback_edt_txt.getText().toString());
                    feedbackModel.setFeedbackImage(Globals.getOurInstance().getStrimage());
                    db.insertFeedback(feedbackModel);

                    Toast.makeText(this, "Feedback Submitted", Toast.LENGTH_SHORT).show();

                    groupCaptureImg.setVisibility(View.GONE);
                    Globals.getOurInstance().setStrimage("");
                    feedback_edt_txt.setText("");

//                    ArrayList<FeedbackModel> feedbackModelArrayList = db.getAllFeedback();
//                    System.out.println(feedbackModelArrayList.toString());
                } else {
                    Toast.makeText(this, "Feedback cannot be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.feedback_camera_img:
                captureFeedbackImg();
                break;
            case R.id.iv_delete_img:
                Globals.getOurInstance().setStrimage("");
                groupCaptureImg.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public Toolbar getBaseToolbar() {
        return mToolbar;
    }


    private void initViews() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = getIntent().getStringExtra("ScreenName");
        }
        setAutoScreenCount(value);

        mToolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(value, null);

        db = SFADatabase.getInstance(this);
        rootLayout = findViewById(R.id.root_layout);
        feedbackSpinner = findViewById(R.id.feedback_spinner);
        distr_spinner = findViewById(R.id.distr_spinner);
        feedback_submit_btn = findViewById(R.id.feedback_submit_btn);
        feedback_edt_txt = findViewById(R.id.feedback_edt_txt);
        feedbackImgIv = findViewById(R.id.feedback_camera_img);
        captureImgIv = findViewById(R.id.iv_capture_image);
        groupCaptureImg = findViewById(R.id.group_capture_img);
        deleteImagIv = findViewById(R.id.iv_delete_img);

        feedback_submit_btn.setOnClickListener(this);
        feedbackImgIv.setOnClickListener(this);
        deleteImagIv.setOnClickListener(this);
        Globals.getOurInstance().setStrimage("");
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
                this, R.layout.spinner_list_item, feedbackName);
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

    }

    private void loadDistributorSpinnerDataFromDB() {

        distributorArrayList = new ArrayList<>();
        distributorArrayList = db.getUniqueCustomerList();
        ArrayList<String> distributorDropDown = new ArrayList<>();

        for (int i = 0; i < distributorArrayList.size(); i++) {
            distributorDropDown.add(distributorArrayList.get(i).getDistrName() + "(" + distributorArrayList.get(i).getCmpName() + ")");
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_list_item, distributorDropDown);
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
    }

    private void captureFeedbackImg() {
        if (isCameraAvailable(this)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (Build.VERSION.SDK_INT >= 24) {

                photofile = Globals.getOurInstance().getOutputMediaFile(MEDIA_TYPE_IMAGE);

                fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photofile);

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                Log.e(TAG, "onClick: " + fileUri.toString());
            } else {
                fileUri = Globals.getOurInstance().getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            }

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

            } else {
                NotifyUtil.showSnackBar(this, rootLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
            }

        } else {
            NotifyUtil.showSnackBar(this, rootLayout, "Device not supporting camera", Snackbar.LENGTH_LONG);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    Globals.getOurInstance().setStrimage("");
                    Globals.getOurInstance().getProfileImagePath(this, fileUri, photofile, true);
                    groupCaptureImg.setVisibility(View.VISIBLE);
                    captureImgIv.setImageURI(fileUri);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                    NotifyUtil.showSnackBar(this, rootLayout, "User cancelled image capture", Toast.LENGTH_SHORT);
                } else {
                    // failed to capture image
                    NotifyUtil.showSnackBar(this, rootLayout, "Sorry! Failed to capture image", Toast.LENGTH_SHORT);

                }
            } else if (requestCode == SELECT_PICTURE && null != data) {
                Globals.getOurInstance().setStrimage("");
                Uri selectedImageUri = data.getData();
                Globals.getOurInstance().getProfileImagePath(this, selectedImageUri, photofile, false);
                groupCaptureImg.setVisibility(View.VISIBLE);
                feedbackImgIv.setImageURI(fileUri);

            }

        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: " + e.getMessage(), e);
        }

        switch (resultCode) {
            case Activity.RESULT_OK:
                break;
            case Activity.RESULT_CANCELED:
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


}
