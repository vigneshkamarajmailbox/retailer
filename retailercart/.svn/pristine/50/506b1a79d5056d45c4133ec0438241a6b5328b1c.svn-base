package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.MessageAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotificationUtils;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;


public class MessageFragment extends SFAFragment {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = MessageFragment.class.getSimpleName();
    private int currentPosition = 0;
    private TextView msg = null;
    private List<MessageModel> messageModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView emptyTxt;
    private MessageAdapter messageAdapter;
    private SFADatabase db;
    private SFASharedPref preferences;
    private Paint p = new Paint();
    private View mCoordinatorLayout;
    private Dialog dialog;
    NotifyUtil.NotifyListener deleteDialogListener = new NotifyUtil.NotifyListener() {
        @Override
        public void onOkClicked() {

            if (messageModelList != null) {

                db.deleteMsgById(preferences.readString(PREF_DISTRCODE),
                        messageModelList.get(currentPosition).getMsgCode());

                db.updateMessageStatus(preferences.readString(PREF_DISTRCODE),
                        messageModelList.get(currentPosition).getMsgCode());

                //To show notification count
                messageModelList.clear();
                loadDataFromDB();

                MessageFragmentListener messageFragmentListener = (MessageFragmentListener) getActivity();
                assert messageFragmentListener != null;
                messageFragmentListener.onMessageHandled();

                String count = String.valueOf(messageAdapter.getItemCount());
                if (count.length() == 1) {
                    msg.setText(String.valueOf("0" + count));
                } else {
                    msg.setText(count);
                }

            } else {
                msg.setText(String.valueOf("00"));
            }
        }

        @Override
        public void onCancelClicked() {


            messageAdapter.updateListItems(currentPosition);
        }
    };

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.message_recylerview);
        emptyTxt = view.findViewById(R.id.msg_empty_txt);
        mCoordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        initSwipe();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    loadDataFromDB();

                } catch (Exception e) {
                    if (Debug.isDebuggerConnected()) {
                        Log.e(TAG, "run: " + e.getMessage(), e);
                    }
                }

            }
        }, 100);

        NotificationUtils.clearNotifications(getSFAFragmentActivity());
    }


    private void adpterListenr() {

        if (messageAdapter != null && messageAdapter.getItemCount() > 0)
            messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    showMessage(position);
                }
            });

        if (messageAdapter != null)
            messageAdapter.setOnItemLongClickListener(new MessageAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(View view, int position) {

                    deleteOrderById(position);
                    return false;
                }
            });

    }

    private void deleteOrderById(int position) {


        currentPosition = position;

        String msgBody = messageModelList.get(position).getMsgBody();

        String text = "<font color=#3B4463>Do you want to delete this message ?</font> <font color=#FF8075>" + msgBody + "</font>";

        NotifyUtil.showDialog(getSFAFragmentActivity(), "Delete",
                text, deleteDialogListener, "Yes", "No");
    }

    @SuppressWarnings("deprecation")
    private void showMessage(final int position) {

        if (dialog != null && dialog.isShowing()) return;
        currentPosition = position;
        recyclerView.getAdapter().notifyItemChanged(position);
        dialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.single_msg_dialog);
        final Button cancel = dialog.findViewById(R.id.msg_ok_btn);

        TextView msgTitle = dialog.findViewById(R.id.msg_subject_txt);
        TextView msgTxt = dialog.findViewById(R.id.msg_txt);
        TextView msgDateTxt = dialog.findViewById(R.id.msg_date_txt);

        msgTitle.setText(messageModelList.get(position).getMsgTitle());
        msgTxt.setText(fromHtml(messageModelList.get(position).getMessageDetail()));

        msgDateTxt.setText(DateUtil.covertTimeStampIntoData(messageModelList.get(position).getMsgDate(), "yyyy-MM-dd hh:mm a"));

        db.updateMessageStatus(preferences.readString(PREF_DISTRCODE), messageModelList.get(position).getMsgCode());

        recyclerView.getAdapter().notifyDataSetChanged();

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                messageModelList = db.getAllMessages(preferences.readString(PREF_DISTRCODE),
                        preferences.readString(PREF_SALESMANCODE), 0, true);

                messageAdapter.updateUI(position, messageModelList.get(position));

                dialog.dismiss();
            }
        });

        //Send BroadCast for Notification count Referesh
        Intent pushNotification = new Intent(Globals.PUSH_NOTIFICATION_COUNT);
        LocalBroadcastManager.getInstance(getSFAFragmentActivity()).sendBroadcast(pushNotification);
        dialog.show();
    }


    private void loadDataFromDB() {

        messageModelList = db.getAllMessages(preferences.readString(PREF_DISTRCODE),
                preferences.readString(PREF_SALESMANCODE), 0, true);

        messageAdapter = new MessageAdapter(getActivity(), messageModelList);

        if (messageAdapter.getItemCount() > 0) {
            recyclerView.setAdapter(messageAdapter);
            emptyTxt.setVisibility(View.GONE);


            String count = String.valueOf(messageAdapter.getItemCount());
            if (count.length() == 1) {
                msg.setText(String.valueOf("0" + count));
            } else {
                msg.setText(count);
            }

        } else {
            emptyTxt.setVisibility(View.VISIBLE);
            msg.setText(String.valueOf("00"));
        }

        adpterListenr();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_messages, menu);

        MenuItem item = menu.findItem(R.id.menu_hotlist);

        item.setActionView(R.layout.action_bar_notifitcation_icon);
        View view = item.getActionView();
        msg = view.findViewById(R.id.hotlist_hot);
        msg.setText(String.valueOf("00"));

    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                deleteOrderById(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#ffffff"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.cancle);
                        RectF iconDest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, iconDest, p);
                    } else {
                        p.setColor(Color.parseColor("#ffffff"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.cancle);
                        RectF iconDest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, iconDest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            Snackbar.make(mCoordinatorLayout, "Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
        }
    }

    public interface MessageFragmentListener {
        void onMessageHandled();
    }

}
