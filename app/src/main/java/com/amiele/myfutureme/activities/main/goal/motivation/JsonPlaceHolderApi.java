package com.amiele.myfutureme.activities.main.goal.motivation;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

//    @GET("api/quotes")
//    Call<List<Quote>> getQuotes();

    @GET("api/qotd")
    Call<Quote> getQuotes();
}
