package br.com.mirateste.mirateste.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.mirateste.mirateste.R;

public class RandomNumbersAdapter extends RecyclerView.Adapter<RandomNumbersAdapter.RandomViewHolder> {

    private Context mContext;
    private List<Integer> mNumberList;

    public RandomNumbersAdapter(Context context, List<Integer> numbersList){
        mContext = context;
        mNumberList = numbersList;
    }


    @NonNull
    @Override
    public RandomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.number_adapter_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomViewHolder holder, int position) {

        if(mNumberList.size()>0)
        holder.numberTxt.setText(Integer.toString(mNumberList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNumberList.size();
    }

    public class RandomViewHolder extends RecyclerView.ViewHolder{

        TextView numberTxt;

        public RandomViewHolder(View itemView) {
            super(itemView);
            numberTxt = itemView.findViewById(R.id.txt_number);
        }
    }

}
