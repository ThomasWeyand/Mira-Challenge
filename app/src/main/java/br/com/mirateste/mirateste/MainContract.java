package br.com.mirateste.mirateste;

import java.util.List;

import br.com.mirateste.mirateste.model.RandomNumbers;

public class MainContract {

    public interface Parent{
        void updateAdapter(List<Integer> numberslist);
    }

    public interface Presenter{
        void buildList();
        String verifyValue(String number);
        List<RandomNumbers> getVerifiedResults();
        void setHistoryListFromBack(List<RandomNumbers> historyList);
    }

}
