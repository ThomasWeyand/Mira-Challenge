package br.com.mirateste.mirateste;

import java.util.List;

public class MainContract {

    public interface Parent{
        void updateAdapter(List<Integer> numberslist);
    }

    public interface Presenter{
        void buildList();
        String verifyValue(String number);
    }

}
