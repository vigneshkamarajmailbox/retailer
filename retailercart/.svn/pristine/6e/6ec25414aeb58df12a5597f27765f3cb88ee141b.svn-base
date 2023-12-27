package com.botree.retailerssfa.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Download;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.service.DownloadService;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.controller.constants.AppConstant.FILE_DOWNLOADED;
import static com.botree.retailerssfa.controller.constants.AppConstant.FILE_DOWNLOADING;
import static com.botree.retailerssfa.support.Globals.MESSAGE_PROGRESS;
import static com.botree.retailerssfa.support.Globals.MESSAGE_UNPROGRESS;
import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_AUTH_TOKEN;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private Activity con;
    private List<MessageModel> messageModel;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener monItemLongClicked;
    private SFASharedPref preferences;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                int downloadPosition = intent.getIntExtra("downloadPosition", -1);
                if (downloadPosition < messageModel.size()) {
                    try {
                        messageModel.get(downloadPosition).setDownloadPercent((int) download.getCurrentFileSize());
                        messageModel.get(downloadPosition).setDownloadStatus(AppConstant.FILE_DOWNLOADED);
                        notifyItemChanged(downloadPosition);
                        SFADatabase.getInstance(con).updateMessageDownloadStatus(preferences.readString(PREF_DISTRCODE),
                                messageModel.get(downloadPosition).getMsgCode(), String.valueOf(FILE_DOWNLOADED));
                    } catch (Exception e) {
                        Log.e(TAG, "onReceive: " + e.getMessage(), e);
                    }
                }
                notifyDataSetChanged();
            } else if (intent.getAction().equals(MESSAGE_UNPROGRESS)) {
                int downloadPosition = intent.getIntExtra("downloadPosition", -1);
                if (downloadPosition < messageModel.size()) {
                    messageModel.get(downloadPosition).setDownloadStatus(AppConstant.FILE_DOWNLOAD);
                    notifyItemChanged(downloadPosition);
                    SFADatabase.getInstance(con).updateMessageDownloadStatus(preferences.readString(PREF_DISTRCODE),
                            messageModel.get(downloadPosition).getMsgCode(), String.valueOf(AppConstant.FILE_DOWNLOAD));
                }
                notifyDataSetChanged();
            }
        }
    };

    public MessageAdapter(Activity context, List<MessageModel> messageModels) {
        this.messageModel = messageModels;
        con = context;
        preferences = SFASharedPref.getOurInstance();
        registerReceiver();
    }

    public void updateListItems(int position) {

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, messageModel.size());
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public String getValueAt(int position) {

        return messageModel.get(position).getMsgTitle();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (messageModel.get(position).getMsgStatus().equalsIgnoreCase("0")) {
            holder.msgTitleTxt.setTypeface(holder.msgTitleTxt.getTypeface(), Typeface.NORMAL);
            holder.msgBodyTxt.setTextColor(ContextCompat.getColor(con, R.color.color6));
        }
        holder.msgTitleTxt.setText(messageModel.get(position).getMsgTitle());

        String userInitial = messageModel.get(position).getMsgTitle();
        String init = userInitial.substring(0, 1);

        holder.initialeTxt.setText(init.toUpperCase(Locale.getDefault()));
        holder.msgBodyTxt.setText(fromHtml(messageModel.get(position).getMessageDetail()));
        if (messageModel.get(position).getFileName() != null && !messageModel.get(position).getFileName().isEmpty()) {
            holder.attachment.setVisibility(View.VISIBLE);
            holder.fileNameTxt.setVisibility(View.VISIBLE);
            holder.fileNameTxt.setText(messageModel.get(position).getFileName());
        } else {
            holder.attachment.setVisibility(View.GONE);
            holder.fileNameTxt.setVisibility(View.GONE);
        }
        holder.msgDateTxt.setText(DateUtil.covertTimeStampIntoData(messageModel.get(position).getMsgDate(), "yyyy-MM-dd hh:mm a"));

        File file = new File(FileAccessUtil.getInstance().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                messageModel.get(position).getFileName());
        if (messageModel.get(position).getDownloadStatus() == AppConstant.FILE_DOWNLOAD) {
            holder.attachment.setImageResource(R.drawable.ic_download);
        } else if (messageModel.get(position).getDownloadStatus() == FILE_DOWNLOADING) {
            holder.attachment.setImageResource(R.drawable.loader);
        } else {
            holder.attachment.setImageResource(getAttachmentImage(file));
        }

        holder.initialeTxt.setBackground(AppUtils.getOurInstance().getInitialCircleDrawable(position));

        holder.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttachmentOnClick(position, holder);
            }
        });
    }

    private void setAttachmentOnClick(@SuppressLint("RecyclerView") int position, @NonNull MessageViewHolder holder) {
        if (messageModel.get(position).getFileName() != null) {
            File file = new File(FileAccessUtil.getInstance().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    messageModel.get(position).getFileName());
            if (file.exists()) {
                openExistingFile(holder, file);
            } else {
                downloadAttachment(position, holder);
            }
        }
    }

    private void downloadAttachment(@SuppressLint("RecyclerView") int position, @NonNull MessageViewHolder holder) {
        if (checkPermission()) {
            if (!messageModel.get(position).getFileName().isEmpty()) {
                String fileName = messageModel.get(position).getFileName();
                String[] tokens = fileName.split("\\.(?=[^.]+$)");
                if (tokens.length >= 2) {
                    SFADatabase.getInstance(con).updateMessageDownloadStatus(preferences.readString(PREF_DISTRCODE),
                            messageModel.get(position).getMsgCode(), String.valueOf(FILE_DOWNLOADING));
                    messageModel.get(position).setDownloadStatus(FILE_DOWNLOADING);
                    holder.attachment.setImageResource(R.drawable.loader);
                    startDownload(String.valueOf(tokens[0] + "/" + tokens[1]), position);
                } else {
                    Toast.makeText(con, "Invalid File", Snackbar.LENGTH_LONG).show();
                }
            }
        } else {
            requestPermission();
        }
    }

    private void openExistingFile(@NonNull MessageViewHolder holder, File file) {
        try {
            con.startActivity(AppUtils.getOurInstance().openFile(file, con));
        } catch (Exception e) {

            Uri selectedUri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(selectedUri, "resource/folder");

            if (intent.resolveActivityInfo(con.getPackageManager(), 0) != null) {
                con.startActivity(intent);
            } else {

                NotifyUtil.showSnackBar(con, holder.mView, con.getResources().getString(R.string.file_format_error), Toast.LENGTH_SHORT);
            }
        }
    }


    private int getAttachmentImage(File file) {
        if (file.exists()) {
            String fileName = file.toString().toLowerCase(Locale.getDefault());
            if (fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png")) {
                return R.drawable.ic_file_image;
            } else if (fileName.contains(".wav") || fileName.contains(".mp3")) {
                return R.drawable.ic_file_music;
            } else if (fileName.contains(".doc") || fileName.contains(".docx")) {
                return R.drawable.ic_file_word;
            } else if (fileName.contains(".3gp") || fileName.contains(".mpg") || fileName.contains(".mpeg") ||
                    fileName.contains(".mpe") || fileName.contains(".mp4") || fileName.contains(".avi")) {
                return R.drawable.ic_file_video;
            } else if (fileName.contains(".pdf")) {
                return R.drawable.ic_file_pdf;
            } else if (fileName.contains(".xls") || fileName.contains(".xlsx")) {
                return R.drawable.ic_file_excel;
            } else {
                return R.drawable.ic_file_doc;
            }
        } else {
            return R.drawable.ic_download;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(con,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(con,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private void startDownload(String file, int position) {
        Intent intent = new Intent(con, DownloadService.class);
        intent.putExtra("FILE", file);
        intent.putExtra("Position", position);
        intent.putExtra("accessToken", preferences.readString(PREF_AUTH_TOKEN));
        con.startService(intent);
    }

    @Override
    public int getItemCount() {

        return messageModel.size();
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(con);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        intentFilter.addAction(MESSAGE_UNPROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    public void updateUI(int pos,MessageModel message) {
        messageModel.remove(pos);
        messageModel.add(pos,message);
        notifyDataSetChanged();
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        final View mView;
        final TextView msgTitleTxt;
        final TextView msgBodyTxt;
        final TextView msgDateTxt;
        final ImageView attachment;
        final TextView initialeTxt;
        final TextView fileNameTxt;

        MessageViewHolder(View view) {
            super(view);
            mView = view;
            initialeTxt = view.findViewById(R.id.msg_initi_txt);
            msgTitleTxt = view.findViewById(R.id.msg_title_txt);
            msgBodyTxt = view.findViewById(R.id.msg_body_txt);
            msgDateTxt = view.findViewById(R.id.msg_date);
            attachment = view.findViewById(R.id.attachement);
            fileNameTxt = view.findViewById(R.id.msg_file_name_txt);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + msgTitleTxt.getText();
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());

            }
        }

        @Override
        public boolean onLongClick(View view) {

            if (monItemLongClicked != null) {
                monItemLongClicked.onItemLongClicked(view, getAdapterPosition());
            }
            return false;
        }
    }
}