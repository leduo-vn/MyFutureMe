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
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoalActivity extends AppCompatActivity {
    private Quote mQuote;

    private TextView mTvQuote;
    private RecyclerView mRvGoal;

    private void InitializeView()
    {
        mRvGoal = findViewById(R.id.goal_rv_goal);
        mTvQuote = findViewById(R.id.goal_tv_quote);
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
                    mTvQuote.setText("Code: " + response.code());
                    return;
                }

                mQuote = response.body();

                assert mQuote != null;
                String body= mQuote.getDetail().getBody() ;
                String author= mQuote.getDetail().getAuthor();
                mTvQuote.setText(getString(R.string.text_quote,body,author));
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                mTvQuote.setText(t.getMessage());
            }
        });

    }

    // Consider the startActivityForResult
    public void onAddWorkTaskClicked(View view)
    {
        Intent addWorkTaskActivity = new Intent(this, AddGoalActivity.class);
        startActivity(addWorkTaskActivity);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        InitializeView();
        DisplayQuoteContentFromAPI();

        mTvQuote.setSelected(true);

        ArrayList<Goal> mGoalList = new ArrayList<>();
        ArrayList<Task> mTasklist = new ArrayList<>();

        mTasklist.add(new Task("Task 1"));
        mTasklist.add(new Task("Task 2"));
        mTasklist.add(new Task("Task 3"));

        mGoalList.add(new Goal("Work Task Name","Task description"));
        mGoalList.add(new Goal("Work Task Name 1","Task description 1"));
        mGoalList.add(new Goal("Work Task Name 2","Task description 2"));
        mGoalList.add(new Goal("Work Task Name 3","Task description 3"));

        mGoalList.get(0).setTaskList(mTasklist);
        mGoalList.get(1).setTaskList(mTasklist);
        mGoalList.get(2).setTaskList(mTasklist);
        mGoalList.get(3).setTaskList(mTasklist);


        mRvGoal.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final GoalAdapter adapter = new GoalAdapter(this, mGoalList);
        mRvGoal.setLayoutManager(layoutManager);
        mRvGoal.setAdapter(adapter);

        // Set onClick for Adapter
        adapter.setOnItemClickListener(task -> {
            DisplayToast(task.getName());
            GoToUpdateTaskActivity();
        });


    }

    private void GoToUpdateTaskActivity()
    {
        Intent updateTaskActivity = new Intent(this, UpdateTaskActivity.class);
        startActivity(updateTaskActivity);
    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
