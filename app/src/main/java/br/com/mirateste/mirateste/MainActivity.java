package br.com.mirateste.mirateste;

import android.content.Intent;
import android.content.SharedPreferences;
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
    protected static final int RESULT_HISTORY = 1;
    protected static final String HISTORY_STATE = "history_state";
    private List<RandomNumbers> mHistoryList;


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
                    return true;
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroy");
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
        if(mEdtNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Digite um valor para verificar", Toast.LENGTH_SHORT).show();
        } else if(Integer.parseInt(mEdtNumber.getText().toString()) < -197 ||
                Integer.parseInt(mEdtNumber.getText().toString()) > 197) {
            Toast.makeText(this, "Digite um valor entre -197 e 197", Toast.LENGTH_SHORT).show();
            mEdtNumber.getText().clear();
        }
        else {
            Toast.makeText(this, presenter.verifyValue(mEdtNumber.getText().toString()), Toast.LENGTH_SHORT).show();
            mEdtNumber.getText().clear();
        }
    }

    public void historyClick(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        Bundle args = new Bundle();
        args.putParcelableArrayList(HistoryActivity.HISTORY_LIST, (ArrayList<? extends Parcelable>) mHistoryList);
        intent.putExtras(args);
        startActivityForResult(intent, RESULT_HISTORY);
    }

    public void redefineClick(View view) {
        presenter.buildList();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(HISTORY_STATE,(ArrayList<? extends Parcelable>) mHistoryList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(HISTORY_STATE, (ArrayList<? extends Parcelable>) mHistoryList);
        onSaveInstanceState(bundle);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_HISTORY){
            if(data.getExtras()!=null){
                List<RandomNumbers> historyList = data.getExtras().getParcelableArrayList(HistoryActivity.HISTORY_LIST);
                presenter.setHistoryListFromBack(historyList);
            }
        }
    }
}
