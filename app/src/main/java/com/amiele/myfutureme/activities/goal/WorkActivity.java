package com.amiele.myfutureme.activities.goal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.motivation.JsonPlaceHolderApi;
import com.amiele.myfutureme.activities.motivation.Quote;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkActivity extends AppCompatActivity {

    TextView mTv_quote;
    Quote quote;

    private void InitializedVariables()
    {
        mTv_quote = findViewById(R.id.txt_quote);
    }

    private void DisplayQuoteContentFromAPI()
    {
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
                    mTv_quote.setText("Code: " + response.code());
                    return;
                }

                quote = response.body();

                String body=quote.getDetail().getBody() ;
                String author= quote.getDetail().getAuthor();
                mTv_quote.setText(body+" (" + author +")");
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                mTv_quote.setText(t.getMessage());
            }
        });

    }

    // Consider the startActivityForResult
    public void onAddWorkTaskClicked(View view)
    {
        Intent addWorkTaskActivity = new Intent(this, AddWorkTaskActivity.class);
        startActivity(addWorkTaskActivity);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        InitializedVariables();
        DisplayQuoteContentFromAPI();
//        mTv_quote.startAnimation((Animation) AnimationUtils.loadAnimation(this,R.anim.quote_animation));
        mTv_quote.setSelected(true);

        ArrayList<WorkTask> workTaskList = new ArrayList<>();
        ArrayList<Task> tasklist = new ArrayList<>();
        tasklist.add(new Task("Task 1"));
        tasklist.add(new Task("Task 2"));
        tasklist.add(new Task("Task 3"));

        workTaskList.add(new WorkTask("Work Task Name","Task description"));
        workTaskList.add(new WorkTask("Work Task Name 1","Task description 1"));
        workTaskList.add(new WorkTask("Work Task Name 2","Task description 2"));
        workTaskList.add(new WorkTask("Work Task Name 3","Task description 3"));
        workTaskList.get(0).setTaskList(tasklist);
        workTaskList.get(1).setTaskList(tasklist);
        workTaskList.get(2).setTaskList(tasklist);
        workTaskList.get(3).setTaskList(tasklist);


        RecyclerView recyclerView = findViewById(R.id.work_task_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final WorkTaskAdapter adapter = new WorkTaskAdapter(this,workTaskList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Set onClick for Adapter
        // When item clicked, open the fragment of chosen category
        adapter.setOnItemClickListener(new WorkTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
               // adapter.notifyItemChanged;
              //  Log.i("info", String.valueOf(task.getDescription()));
                //Toast.makeText(, task.getDescription(),Toast.LENGTH_SHORT).show();
                DisplayToast(task.getName());
            }
        });


    }

    private  void DisplayToast(String text)
    {

        Intent updateTaskActivity = new Intent(this, UpdateTaskActivity.class);
        startActivity(updateTaskActivity);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
