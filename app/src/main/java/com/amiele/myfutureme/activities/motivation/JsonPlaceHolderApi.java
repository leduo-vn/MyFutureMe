package com.amiele.myfutureme.activities.motivation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

//    @GET("api/quotes")
//    Call<List<Quote>> getQuotes();

    @GET("api/qotd")
    Call<Quote> getQuotes();
}
