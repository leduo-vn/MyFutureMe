package com.amiele.myfutureme.activities.main.goal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.amiele.myfutureme.activities.main.motivation.JsonPlaceHolderApi;
import com.amiele.myfutureme.activities.main.motivation.Quote;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoalActivity extends AppCompatActivity {
    private GoalViewModel mGoalViewModel;
    final String ACTION_ADD = "ADD";
    final String ACTION_EDIT = "EDIT";

    private Quote mQuote;
    GoalAdapter adapter;
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

    public void onAddWorkTaskClicked(View view)
    {
        Intent addGoalActivity = new Intent(this, AddGoalActivity.class);
        addGoalActivity.putExtra("user_id",Integer.toString(mGoalViewModel.getUserId()));
        addGoalActivity.putExtra("action",ACTION_ADD);

        startActivityForResult(addGoalActivity,ACTIVITY_REQUEST_CODE);
    }

    public static final int ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            DisplayToast("add goal success!");
        } else
        if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            {
           DisplayToast("edit task success");
        }
        else DisplayToast("error");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        InitializeView();
        DisplayQuoteContentFromAPI();

        mTvQuote.setSelected(true);

        ArrayList<Goal> mGoalList = new ArrayList<>();

        mGoalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        mRvGoal.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new GoalAdapter(this, mGoalList);
        mRvGoal.setLayoutManager(layoutManager);
        mRvGoal.setAdapter(adapter);

        // Set onClick for Adapter
        adapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                DisplayToast(task.getName());
                GoToUpdateTaskActivity(task);
            }

            @Override
            public void onItemClick(Goal goal) {
                DisplayToast(Integer.toString(goal.getId()));
                GoToUpdateGoalActivity(goal);
            }
        });

        mGoalViewModel.getUserResult().observe(this, aBoolean -> {
            if (aBoolean) GetGoals();
        });
    }

    private void GetGoals()
    {
        mGoalViewModel.getAllGoals().observe(this, goals -> {
            adapter.setGoalList(goals);
            adapter.notifyDataSetChanged();
        } );
    }

    private void GoToUpdateGoalActivity(Goal goal)
    {
        Intent  updateGoalActivity= new Intent(this, AddGoalActivity.class);
        updateGoalActivity.putExtra("goal_id",Integer.toString(goal.getId()));
        updateGoalActivity.putExtra("action",ACTION_EDIT);

        startActivityForResult(updateGoalActivity,ACTIVITY_REQUEST_CODE);
    }

    private void GoToUpdateTaskActivity(Task task)
    {
        Intent  updateTaskActivity= new Intent(this, UpdateTaskActivity.class);
        updateTaskActivity.putExtra("task_id",Integer.toString(task.getId()));
        startActivityForResult(updateTaskActivity,EDIT_TASK_ACTIVITY_REQUEST_CODE);
    }

    private  void DisplayToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
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
                mGoalViewModel.updateSignedInUser(false);
                finish();
                Intent loginActivity = new Intent(this, LoginActivity.class);
                startActivity(loginActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
