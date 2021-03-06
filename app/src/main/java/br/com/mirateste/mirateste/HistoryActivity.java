package br.com.mirateste.mirateste;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.mirateste.mirateste.adapter.HistoryAdapter;
import br.com.mirateste.mirateste.model.RandomNumbers;

public class HistoryActivity extends AppCompatActivity {

    private List<RandomNumbers> mRandomNumbers;
    private HistoryAdapter mHistoryAdapter;
    protected static final String HISTORY_LIST = "history_list";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        buildToolbar();
        setUpAdapter();
        getHistoryArgs();

    }

    private void buildToolbar() {

        Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setTitle("Histórico");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.getNavigationIcon().setColorFilter(getResources()
                .getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    private void setUpAdapter() {

        RecyclerView historyRecycler = findViewById(R.id.history_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        historyRecycler.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this,R.drawable.divider_shape);
        dividerItemDecoration.setDrawable(dividerDrawable);
        historyRecycler.addItemDecoration(dividerItemDecoration);
        mRandomNumbers = new ArrayList<>();
        mHistoryAdapter = new HistoryAdapter(this,mRandomNumbers);
        historyRecycler.setAdapter(mHistoryAdapter);

    }

    private void getHistoryArgs() {

        if(getIntent().getExtras()!=null){
            List<RandomNumbers> randomResultsList = this.getIntent().getExtras().getParcelableArrayList(HISTORY_LIST);
            if(randomResultsList!=null) {
                for(int i= (randomResultsList.size()-1);i>=0;i--) {
                    mRandomNumbers.add(randomResultsList.get(i));
                }
                mHistoryAdapter.notifyDataSetChanged();
            }
        }
    }

}
