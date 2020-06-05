package com.amiele.myfutureme.activities.main.goal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.activities.authentication.login.LoginActivity;
import com.amiele.myfutureme.activities.main.goal.task.UpdateTaskActivity;
import com.amiele.myfutureme.activities.main.goal.motivation.JsonPlaceHolderApi;
import com.amiele.myfutureme.activities.main.goal.motivation.Quote;
import com.amiele.myfutureme.activities.main.summary.SummaryActivity;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoalActivity extends AppCompatActivity {
    public static final String GOAL_ACTION_ADD = "ADD";
    public static final String GOAL_ACTION_EDIT = "EDIT";
    public static final int GOAL_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;

    private GoalViewModel mGoalViewModel;

    private Quote mQuote;
    private GoalAdapter mAdapter;
    private TextView mTvQuote;
    private RecyclerView mRvGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        mRvGoal = findViewById(R.id.goal_rv_goal);
        mTvQuote = findViewById(R.id.goal_tv_quote);
        ArrayList<Goal> mGoalList = new ArrayList<>();

        DisplayQuoteContentFromAPI();
        mTvQuote.setSelected(true);

        mGoalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new GoalAdapter(this, mGoalList);
        mRvGoal.setLayoutManager(layoutManager);
        mRvGoal.setAdapter(mAdapter);
        mRvGoal.setHasFixedSize(true);

        // Set onClick for Adapter
        mAdapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                GoToUpdateTaskActivity(task);
            }

            @Override
            public void onItemClick(Goal goal) {
                GoToUpdateGoalActivity(goal);
            }
        });


        mGoalViewModel.getUserResult().observe(this, aBoolean -> {
            if (aBoolean) GetGoals();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.goal_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mGoalViewModel.updateSignedInUserToSignedOut();
                FinishActivity();
                return true;
            case R.id.action_summary:
                Intent summaryActivity = new Intent(this, SummaryActivity.class);
                summaryActivity.putExtra("user_id",Integer.toString(mGoalViewModel.getUserId()));
                startActivity(summaryActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            DisplayToast("Goal list is updated");
        } else
        if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            DisplayToast("Task is updated");
        }
        else DisplayToast("Error is occurred! Your changes may not be stored");
    }

    public void onAddWorkTaskClicked(View view)
    {
        Intent addGoalActivity = new Intent(this, AddGoalActivity.class);

        addGoalActivity.putExtra("user_id",Integer.toString(mGoalViewModel.getUserId()));
        addGoalActivity.putExtra("action", GOAL_ACTION_ADD);

        startActivityForResult(addGoalActivity, GOAL_ACTIVITY_REQUEST_CODE);
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
                    mTvQuote.setText(String.format(Locale.US,"Code: %d", response.code()));
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

    private void GetGoals()
    {
        mGoalViewModel.getAllGoals().observe(this, goals -> {
            mAdapter.setGoalList(goals);
            mAdapter.notifyDataSetChanged();
        } );
    }

    private void GoToUpdateGoalActivity(Goal goal)
    {
        Intent  updateGoalActivity= new Intent(this, AddGoalActivity.class);
        updateGoalActivity.putExtra("goal_id",Integer.toString(goal.getId()));
        updateGoalActivity.putExtra("action", GOAL_ACTION_EDIT);

        startActivityForResult(updateGoalActivity, GOAL_ACTIVITY_REQUEST_CODE);
    }

    private void GoToUpdateTaskActivity(Task task)
    {
        Intent  updateTaskActivity= new Intent(this, UpdateTaskActivity.class);

        updateTaskActivity.putExtra("task_id",Integer.toString(task.getId()));

        startActivityForResult(updateTaskActivity,EDIT_TASK_ACTIVITY_REQUEST_CODE);
    }

    private void FinishActivity()
    {
        finish();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


}
