package br.com.mirateste.mirateste.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.mirateste.mirateste.R;
import br.com.mirateste.mirateste.model.RandomNumbers;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context mContext;
    private List<RandomNumbers> mRandomNumbersList;

    public HistoryAdapter(Context context, List<RandomNumbers> randomNumbersList){
        this.mContext = context;
        this.mRandomNumbersList = randomNumbersList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.HistoryViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.history_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {

        if(mRandomNumbersList.size() > 0) {
            holder.numVerifiedTxt.setText(String.valueOf(mRandomNumbersList.get(position).getRandomNum()));
            holder.verificationTxt.setText(mRandomNumbersList.get(position).getVerifyTxt());

        }

    }

    @Override
    public int getItemCount() {
        return mRandomNumbersList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView numVerifiedTxt;
        AppCompatTextView verificationTxt;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            numVerifiedTxt = itemView.findViewById(R.id.number_verified_txt);
            verificationTxt = itemView.findViewById(R.id.verification_txt);

        }
    }
}
