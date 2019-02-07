package br.com.mirateste.mirateste;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mirateste.mirateste.adapter.RandomNumbersAdapter;
import br.com.mirateste.mirateste.model.RandomNumbers;

public class MainActivity extends AppCompatActivity implements MainContract.Parent {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<Integer> mNumbers;
    private RandomNumbersAdapter mNumbersAdapter;
    private MainContract.Presenter presenter;
    private EditText mEdtNumber;
    protected static final String HISTORY_STATE = "history_state";
    private List<RandomNumbers> mHistoryList;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdtNumber = findViewById(R.id.edt_number);
        final AppCompatButton verifyBtn = findViewById(R.id.btn_verify);

        mEdtNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    verifyBtn.performClick();
                    return false;
                }
                return false;
            }
        });

        mHistoryList = new ArrayList<>();

        buildNumberAdapter();
        presenter = new MainActivityPresenter(this,this);

        if(savedInstanceState == null || !savedInstanceState.containsKey(HISTORY_STATE)){
            presenter.buildList();
        }else{
            mHistoryList.addAll(savedInstanceState.<RandomNumbers>getParcelableArrayList(HISTORY_STATE));
            updateAdapter(mHistoryList.get(mHistoryList.size() - 1).getGenerateNumbers());
            presenter.setIntegerList(mHistoryList.get(mHistoryList.size() - 1).getGenerateNumbers());
        }
    }

    private void buildNumberAdapter() {

    mRecyclerView = findViewById(R.id.numbers_list);
    mNumbers = new ArrayList<>();
    mNumbersAdapter = new RandomNumbersAdapter(this, mNumbers);
    mRecyclerView.setAdapter(mNumbersAdapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }


    @Override
    public void updateAdapter(List<Integer> numberslist) {
        if(!mNumbers.isEmpty())
        mNumbers.clear();
        mNumbers.addAll(numberslist);
        mNumbersAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateHistoryList(RandomNumbers randomNumbers) {
        mHistoryList.add(randomNumbers);
    }

    public void verifyClick(View view) {
        String toastText;
        if(mEdtNumber.getText().toString().equals("")) {
            toastText = getResources().getString(R.string.some_value);
        } else if(Integer.parseInt(mEdtNumber.getText().toString()) < -197 ||
                Integer.parseInt(mEdtNumber.getText().toString()) > 197) {
            toastText = getResources().getString(R.string.wrong_interval);
            mEdtNumber.getText().clear();
        }
        else {
            toastText = presenter.verifyValue(mEdtNumber.getText().toString());
            mEdtNumber.getText().clear();
        }
        showToast(toastText);
    }

    @Override
    public void showToast(String toastText){
        if(mToast!=null)
            mToast.cancel();
        mToast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void historyClick(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        Bundle args = new Bundle();
        args.putParcelableArrayList(HistoryActivity.HISTORY_LIST, (ArrayList<? extends Parcelable>) mHistoryList);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void redefineClick(View view) {
        presenter.buildList();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(HISTORY_STATE,(ArrayList<? extends Parcelable>) mHistoryList);
        super.onSaveInstanceState(outState);
    }

}
