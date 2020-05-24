package com.amiele.myfutureme.activities.motivation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotivationActivity extends AppCompatActivity {
    Quote quotes;


    private void DisplayContent()
    {
        final TextView textView = findViewById(R.id.textViewQuotes);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://favqs.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Quote> call = jsonPlaceHolderApi.getQuotes();

        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {

                if (!response.isSuccessful()){
                    textView.setText("Code: " + response.code());
                    return;
                }

                quotes = response.body();
                String content = "";
                content+="text: " + quotes.getDetail().getBody()+ "\n";
                content += "author: " + quotes.getDetail().getAuthor() + "\n\n";
//                for (Quote quote: quotes)
//                {
//                    String content = "";
//                    content+="text: " + quote.getContent()+ "\n";
//                    content += "author: " + quote.getAuthor() + "\n\n";
//
//                    textView.append(content);
//                }
                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);
        DisplayContent();



        // Start comment

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://type.fit/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<Quote>> call = jsonPlaceHolderApi.getQuotes();
//
//        call.enqueue(new Callback<List<Quote>>() {
//            @Override
//            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
//
//                if (!response.isSuccessful()){
//                    textView.setText("Code: " + response.code());
//                    return;
//                }
//
//                quotes = response.body();
////                for (Quote quote: quotes)
////                {
////                    String content = "";
////                    content+="text: " + quote.getContent()+ "\n";
////                    content += "author: " + quote.getAuthor() + "\n\n";
////
////                    textView.append(content);
////                }
//                textView.setText("What page number you want to open from 0 to "+(quotes.size()-1)+" ");
//            }
//
//            @Override
//            public void onFailure(Call<List<Quote>> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });
//
            // End commnet

//        DownloadQuotes task = new DownloadQuotes();
//        task.execute("https://type.fit/api/quotes");
    }

    public void onFindQuoteButtonClicked(View view)
    {
//        EditText editText = findViewById(R.id.editText);
//        TextView quoteView = findViewById(R.id.quote_content);
//        String number = editText.getText().toString();
//        if (number!=null && number.length()>0)
//        {
//            int index = Integer.parseInt(number);
//            Quote quote = quotes.get(index);
//            String content = "";
//            content+="text: " + quote.getContent()+ "\n";
//            content += "author: " + quote.getAuthor() + "\n\n";
//
//            quoteView.setText(content);
//        }
    }
}
//public class DownloadQuotes extends AsyncTask<String, Void, String>{
//
//    @Override
//    protected String doInBackground(String... urls) {
//        String result="";
//        URL url;
//        HttpURLConnection urlConnection = null;
//        try{
//            url = new URL(urls[0]);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream in = urlConnection.getInputStream();
//            InputStreamReader reader = new InputStreamReader(in);
//            int data = reader.read();
//            while (data != -1)
//            {
//                char current = (char) data;
//                result += current;
//                data = reader.read();
//                //    Log.i("info",result);
//            }
//            return result;
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        //   Log.i("JSON",s);
//
//        try{
//            JSONObject jsonObject = new JSONObject();
////                String quote
//            JSONArray arr = new JSONArray(s);
//            for (int i = 0; i< arr.length();i++)
//            {
//                JSONObject jsonPart = arr.getJSONObject(i);
//
//                Log.i("quote",jsonPart.getString("author"));
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//}
