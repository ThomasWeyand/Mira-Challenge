package br.com.mirateste.mirateste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.mirateste.mirateste.adapter.RandomNumbersAdapter;

public class ListResultDialogActivity extends AppCompatActivity {

    public static final String RESULT_LIST = "result_list";
    private List<Integer> mResultsList;
    private RandomNumbersAdapter mRandomNumAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dialog);

        setUpAdapter();
        getExtrasList();
    }

    private void setUpAdapter() {
        RecyclerView resultsRecycler = findViewById(R.id.list_recycler);
        mResultsList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        resultsRecycler.setLayoutManager(gridLayoutManager);
        mRandomNumAdapter = new RandomNumbersAdapter(this, mResultsList);
        resultsRecycler.setAdapter(mRandomNumAdapter);

    }

    private void getExtrasList() {
        if(getIntent().getExtras()!=null){
            mResultsList.addAll(getIntent().getIntegerArrayListExtra(RESULT_LIST));
            mRandomNumAdapter.notifyDataSetChanged();
        }

    }

    public void closeDialogBtn(View view) {
        onBackPressed();
    }
}
