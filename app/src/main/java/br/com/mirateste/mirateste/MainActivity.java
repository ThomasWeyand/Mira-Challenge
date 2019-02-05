package br.com.mirateste.mirateste;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mirateste.mirateste.adapter.RandomNumbersAdapter;
import br.com.mirateste.mirateste.model.RandomNumbers;
import br.com.mirateste.mirateste.network.ApiClient;
import br.com.mirateste.mirateste.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainContract.Parent {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<Integer> mNumbers;
    private RandomNumbersAdapter mNumbersAdapter;
    private MainContract.Presenter presenter;
    private EditText mEdtNumber;
    protected static final int RESULT_HISTORY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdtNumber = findViewById(R.id.edt_number);

        presenter = new MainActivityPresenter(this,this);

        buildNumberAdapter();
        presenter.buildList();
        setUpButtons();

    }

    private void setUpButtons() {
        AppCompatButton verifyBtn = findViewById(R.id.btn_verify);

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
        mNumbers.clear();
        mNumbers.addAll(numberslist);
        mNumbersAdapter.notifyDataSetChanged();
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
        args.putParcelableArrayList(HistoryActivity.HISTORY_LIST, (ArrayList<? extends Parcelable>) presenter.getVerifiedResults());
        intent.putExtras(args);
        startActivityForResult(intent, RESULT_HISTORY);
    }

    public void redefineClick(View view) {
        presenter.buildList();

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
