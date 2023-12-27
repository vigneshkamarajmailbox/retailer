package com.botree.retailerssfa.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.support.Globals.VOICE_SEARCH;

public class SearchActivity extends SFAActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private List<QuickViewVo> quickViewVos = new ArrayList<>();
    private QuickActionAdapter quickActionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quick_action);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initToolbar1();
        intiViews();

    }


    private void initToolbar1() {
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        TextView title = toolbar.findViewById(R.id.custom_toolbar_title);

        title.setText(R.string.search_screen);
        setSupportActionBar(toolbar);

        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setContentInsetStartWithNavigation(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_screen_search, menu);
        MenuItem voiceSeach = menu.findItem(R.id.product_voice_search);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_screen_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String text = newText.toUpperCase(Locale.getDefault());

                if (text.trim().length() >= 0) {
                    quickActionAdapter.filter(text);
                }

                return true;
            }
        });

        voiceSeach.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startVoiceService();
                return true;
            }
        });

        return true;
    }

    private void startVoiceService() {

        if (Globals.isSpeechAvail()) {
            Intent intent = new Intent(this, VoiceToStringActivity.class);
            startActivityForResult(intent, VOICE_SEARCH);
        } else {
            Toast.makeText(getApplicationContext(), "Voice recognizer is not available in your device",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void intiViews() {

        RecyclerView recyclerQuickActivity = findViewById(R.id.recyclerQuickActivity);

        recyclerQuickActivity.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerQuickActivity.setHasFixedSize(true);

        quickViewVos.clear();
        quickViewVos = AppUtils.getOurInstance().getQuickMenuList();
        quickActionAdapter = new QuickActionAdapter(quickViewVos);
        recyclerQuickActivity.setAdapter(quickActionAdapter);

    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Log.e(TAG, "onActivityResult 1 : " + data.getStringExtra("voiceTxt"));
                String resultData = data.getStringExtra("voiceTxt");
                if (resultData.length() > 0 && quickActionAdapter != null) {
                    quickActionAdapter.filter(resultData);
                }
            }

        } else {
            Log.d(TAG, "onActivityResult: " + data);
        }
    }

    private class QuickActionAdapter extends RecyclerView.Adapter<SeachViewHolder> {
        private final List<QuickViewVo> quickViewVosList;
        private final ArrayList<QuickViewVo> searchItem;

        QuickActionAdapter(List<QuickViewVo> quickViewVos) {
            this.quickViewVosList = quickViewVos;
            this.searchItem = new ArrayList<>();
            this.searchItem.addAll(quickViewVos);
        }

        @NonNull
        @Override
        public SeachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_action_item_view, parent, false);
            return new SeachViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SeachViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.ivIcon.setImageResource(quickViewVosList.get(position).getIcons());
            if (quickViewVosList.get(position).getColor() != null)
                holder.ivIcon.setColorFilter(quickViewVosList.get(position).getColor());
            holder.tvName.setText(quickViewVosList.get(position).getName());
            holder.checkbox.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("MESSAGE", quickViewVosList.get(position).getName());
                    setResult(RESULT_OK, intent);
                    finish();//finishing activity
                }
            });

        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            quickViewVosList.clear();
            if (charText.length() == 0) {
                quickViewVosList.addAll(searchItem);
            } else {

                if (charText.contains("all") || charText.contains("All")) {

                    quickViewVosList.addAll(searchItem);
                } else {
                    for (QuickViewVo p : searchItem) {

                        if (p.getName().toLowerCase(Locale.getDefault()).contains(charText)) {

                            quickViewVosList.add(p);
                        }
                    }
                }

            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return quickViewVosList.size();
        }

    }

    private class SeachViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final CheckBox checkbox;
        private TextView tvName;

        SeachViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
            checkbox = itemView.findViewById(R.id.checkbox);

        }
    }
}
