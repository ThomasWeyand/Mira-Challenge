package br.com.mirateste.mirateste;

import java.util.List;

import br.com.mirateste.mirateste.model.RandomNumbers;

public class MainContract {

    public interface Parent{
        void updateAdapter(List<Integer> numberslist);
        void updateHistoryList(RandomNumbers randomNumbers);
        void showToast(String toastText);
    }

    public interface Presenter{
        void buildList();
        String verifyValue(String number);
        void setIntegerList(List<Integer> randomList);
    }

}
