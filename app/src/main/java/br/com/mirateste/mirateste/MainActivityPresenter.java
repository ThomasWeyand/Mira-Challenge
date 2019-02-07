package br.com.mirateste.mirateste;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.mirateste.mirateste.model.RandomNumbers;
import br.com.mirateste.mirateste.network.ApiClient;
import br.com.mirateste.mirateste.network.ApiInterface;
import br.com.mirateste.mirateste.util.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainContract.Presenter {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private MainContract.Parent mMainParent;
    private List<Integer> mRandomList;
    private Call<List<Integer>> randomNumResponse;
    private Context mContext;

    public MainActivityPresenter(MainContract.Parent mainParent, Context context){
        mMainParent = mainParent;
        mContext = context;
    }

    @Override
    public void buildList() {

        if (!NetworkConnection.isConnected(mContext)) {
            mMainParent.showToast("Sem conexão com a internet");
        } else{
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            randomNumResponse = apiService.getRandomNumber();

            apiCall();
        }

    }


    private void apiCall() {

        randomNumResponse.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (!response.isSuccessful()) {
                    randomNumResponse = call.clone();
                    randomNumResponse.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                mRandomList = response.body();
                mMainParent.updateAdapter(mRandomList);
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.i(TAG,"Error: "+t);
            }
        });
    }

    @Override
    public String verifyValue(String number) {

        if(mRandomList==null || mRandomList.isEmpty())
            return mContext.getResources().getString(R.string.empty_list);
        else{
            int numInt = Integer.parseInt(number);
            String verifyNumber;

            for (int i=0;i<mRandomList.size();i++){
                if((i+1) < mRandomList.size()) {
                    for (int j = (i + 1); j < mRandomList.size(); j++) {
                        if(mRandomList.get(i) + mRandomList.get(j) == numInt) {
                            verifyNumber = mContext.getResources().getString(R.string.exist);
                            mMainParent.updateHistoryList(new RandomNumbers(numInt,mRandomList,verifyNumber));
                            return verifyNumber;
                        }
                    }
                }
            }
            verifyNumber = mContext.getResources().getString(R.string.not_exist);
            mMainParent.updateHistoryList(new RandomNumbers(numInt,mRandomList,verifyNumber));
            return verifyNumber;
        }
    }

    @Override
    public void setIntegerList(List<Integer> randomList) {
      mRandomList = randomList;
    }
}
