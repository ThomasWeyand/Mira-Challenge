package br.com.mirateste.mirateste.network;

import java.util.List;

import br.com.mirateste.mirateste.model.RandomNumbers;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("random_array_generator")
    Call<List<Integer>> getRandomNumber();


}
